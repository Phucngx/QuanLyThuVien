package com.qltv.QLTV.DTO.Request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticationRequest {
    @NotBlank(message = "NOT_NULL")
    String userName;

    @NotBlank(message = "NOT_NULL")
    String password;
}
