package com.qltv.QLTV.Controller;

import com.nimbusds.jose.JOSEException;
import com.qltv.QLTV.DTO.Request.AuthenticationRequest;
import com.qltv.QLTV.DTO.Request.IntrospectRequest;
import com.qltv.QLTV.DTO.Request.LogoutRequest;
import com.qltv.QLTV.DTO.Request.RefreshRequest;
import com.qltv.QLTV.DTO.Response.ApiResponse;
import com.qltv.QLTV.DTO.Response.AuthenticationResponse;
import com.qltv.QLTV.DTO.Response.IntrospectResponse;
import com.qltv.QLTV.Service.AuthenticationService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;

@RestController
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthenticationController {
    AuthenticationService authenticationService;

    @PostMapping("/log-in")
    public ApiResponse<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request){
        return ApiResponse.<AuthenticationResponse>builder()
                .code(1000)
                .data(authenticationService.authenticate(request))
                .build();
    }

    @PostMapping("/log-out")
    public ApiResponse<String> logout(@RequestBody LogoutRequest request) throws ParseException, JOSEException {
        authenticationService.logout(request);
        return ApiResponse.<String>builder()
                .code(1000)
                .data("Log out successfully")
                .build();
    }

    @PostMapping("/introspect")
    public ApiResponse<IntrospectResponse> authenticate(@RequestBody IntrospectRequest request) throws ParseException, JOSEException {
        return ApiResponse.<IntrospectResponse>builder()
                .code(1000)
                .data(authenticationService.introspect(request))
                .build();
    }

    @PostMapping("/refresh-token")
    public ApiResponse<AuthenticationResponse> logout(@RequestBody RefreshRequest request) throws ParseException, JOSEException {
        return ApiResponse.<AuthenticationResponse>builder()
                .code(1000)
                .data(authenticationService.refreshToken(request))
                .build();
    }
}
