package com.qltv.QLTV.Service;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jwt.JWTClaimsSet;
import com.qltv.QLTV.DTO.Request.AuthenticationRequest;
import com.qltv.QLTV.DTO.Response.AuthenticationResponse;
import com.qltv.QLTV.Entity.Users;
import com.qltv.QLTV.Exception.ApplicationException;
import com.qltv.QLTV.Exception.ErrorCode;
import com.qltv.QLTV.Repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.Date;
import java.util.StringJoiner;
import java.util.UUID;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class AuthenticationService {
    UserRepository userRepository;

    @NonFinal
    protected final String signerKey = "fjJn9RdiXSFp79TA9UhegB6WNs4PsnHO9vxJJMn2oPCniZXzHk8rLKo0UJbl41PU";

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

    public String generateToken(Users user){
        JWSHeader jwsHeader = new JWSHeader(JWSAlgorithm.HS512);
        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(user.getUserName())
                .issuer("ngp.com")
                .issueTime(new Date())
                .expirationTime(new Date(Instant.now().plus(1, ChronoUnit.HOURS).toEpochMilli()))
                .jwtID(UUID.randomUUID().toString())
                .claim("scope", buildScope(user))
                .build();
        Payload payload = new Payload(jwtClaimsSet.toJSONObject());
        JWSObject jwsObject = new JWSObject(jwsHeader, payload);

        try{
            jwsObject.sign(new MACSigner(signerKey.getBytes()));
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
