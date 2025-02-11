package com.qltv.QLTV.Service;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import com.qltv.QLTV.DTO.Request.AuthenticationRequest;
import com.qltv.QLTV.DTO.Request.IntrospectRequest;
import com.qltv.QLTV.DTO.Request.LogoutRequest;
import com.qltv.QLTV.DTO.Request.RefreshRequest;
import com.qltv.QLTV.DTO.Response.AuthenticationResponse;
import com.qltv.QLTV.DTO.Response.IntrospectResponse;
import com.qltv.QLTV.Entity.InvalidatedToken;
import com.qltv.QLTV.Entity.Users;
import com.qltv.QLTV.Exception.ApplicationException;
import com.qltv.QLTV.Exception.ErrorCode;
import com.qltv.QLTV.Repository.InvalidatedRepository;
import com.qltv.QLTV.Repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.Date;
import java.util.StringJoiner;
import java.util.UUID;

@Slf4j
@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class AuthenticationService {
    UserRepository userRepository;
    InvalidatedRepository invalidatedRepository;

    @NonFinal
//    @Value("${jwt.valid-duration}")
    protected long VALID_DURATION = 1;

    @NonFinal
//    @Value("${jwt.refreshable-duration}")
    protected long REFRESHABLE_DURATION = 7;

    @NonFinal
    protected final String SIGNER_KEY = "fjJn9RdiXSFp79TA9UhegB6WNs4PsnHO9vxJJMn2oPCniZXzHk8rLKo0UJbl41PU";

    public AuthenticationResponse authenticate(AuthenticationRequest request){
        Users user  = userRepository.findByUserName(request.getUserName()).orElseThrow(() -> new ApplicationException(ErrorCode.USERNAME_NOT_EXISTS));
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        boolean isAuthenticated = passwordEncoder.matches(request.getPassword(), user.getPassword());
        if(!isAuthenticated) throw new ApplicationException(ErrorCode.SIGN_IN_FAIL);
        String accessToken = generateToken(user);
        return AuthenticationResponse.builder()
                .accessToken(accessToken)
                .build();
    }

    public IntrospectResponse introspect(IntrospectRequest request) throws JOSEException, ParseException {
        String token = request.getToken();
        boolean isValid = true;
        try{
            verifyToken(token, false);
        } catch (ApplicationException ex){
            isValid = false;
        }
        return IntrospectResponse.builder()
                .valid(isValid)
                .build();
    }

    public void logout(LogoutRequest request) throws ParseException, JOSEException {
        try{
            SignedJWT signToken = verifyToken(request.getToken(), true);
            String jit = signToken.getJWTClaimsSet().getJWTID();
            Date expTime = signToken.getJWTClaimsSet().getExpirationTime();
            InvalidatedToken invalidatedToken = InvalidatedToken.builder()
                    .id(jit)
                    .exp(expTime)
                    .build();
            invalidatedRepository.save(invalidatedToken);
        } catch (ApplicationException ex){
            log.info("Access token already exp");
        }
    }

    private SignedJWT verifyToken(String token, boolean isRefresh) throws JOSEException, ParseException {
        JWSVerifier verifier = new MACVerifier(SIGNER_KEY.getBytes());
        SignedJWT signedJWT = SignedJWT.parse(token);

        Date expTime = (isRefresh)
                ? new Date(signedJWT.getJWTClaimsSet().getIssueTime().toInstant().plus(REFRESHABLE_DURATION, ChronoUnit.DAYS).toEpochMilli())
                : signedJWT.getJWTClaimsSet().getExpirationTime();

        boolean verified = signedJWT.verify(verifier);
        if(!verified && expTime.after(new Date())) throw new ApplicationException(ErrorCode.UNAUTHENTICATED);
        if(invalidatedRepository.existsById(signedJWT.getJWTClaimsSet().getJWTID())){
            throw new ApplicationException(ErrorCode.UNAUTHENTICATED);
        }

        return signedJWT;
    }

    public AuthenticationResponse refreshToken(RefreshRequest request) throws ParseException, JOSEException {
        SignedJWT signedJWT = verifyToken(request.getToken(), true);
        String jit = signedJWT.getJWTClaimsSet().getJWTID();
        Date expTime = signedJWT.getJWTClaimsSet().getExpirationTime();
        InvalidatedToken invalidatedToken = InvalidatedToken.builder()
                .id(jit)
                .exp(expTime)
                .build();
        invalidatedRepository.save(invalidatedToken);

        String userName = signedJWT.getJWTClaimsSet().getSubject();

        Users user = userRepository.findByUserName(userName).orElseThrow(() -> new ApplicationException(ErrorCode.UNAUTHENTICATED));
        String token = generateToken(user);
        return AuthenticationResponse.builder()
                .accessToken(token)
                .build();

    }

    private String generateToken(Users user){
        JWSHeader jwsHeader = new JWSHeader(JWSAlgorithm.HS512);
        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(user.getUserName())
                .issuer("ngp.com")
                .issueTime(new Date())
                .expirationTime(new Date(Instant.now().plus(VALID_DURATION, ChronoUnit.DAYS).toEpochMilli()))
                .jwtID(UUID.randomUUID().toString())
                .jwtID(String.valueOf(UUID.randomUUID()))
                .claim("scope", buildScope(user))
                .build();
        Payload payload = new Payload(jwtClaimsSet.toJSONObject());
        JWSObject jwsObject = new JWSObject(jwsHeader, payload);

        try{
            jwsObject.sign(new MACSigner(SIGNER_KEY.getBytes()));
            return jwsObject.serialize();
        } catch (JOSEException ex){
            throw new RuntimeException(ex);
        }

    }

    private String buildScope(Users user){
        StringJoiner stringJoiner = new StringJoiner(" ");
        if(!CollectionUtils.isEmpty(user.getRoles())){
            user.getRoles().forEach(roles -> {
                stringJoiner.add("ROLE_" + roles.getRoleName());
                        if(!CollectionUtils.isEmpty(roles.getPermissions())){
                            roles.getPermissions().forEach(permissions -> {
                                stringJoiner.add(permissions.getPermissionName());
                            });
                        }
            });
        }
        return stringJoiner.toString();
    }
}
