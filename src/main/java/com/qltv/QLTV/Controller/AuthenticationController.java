package com.qltv.QLTV.Controller;

import com.qltv.QLTV.DTO.Request.AuthenticationRequest;
import com.qltv.QLTV.DTO.Response.ApiResponse;
import com.qltv.QLTV.DTO.Response.AuthenticationResponse;
import com.qltv.QLTV.Service.AuthenticationService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
