package com.qltv.QLTV.DTO.Request.User;

import com.qltv.QLTV.Validator.DobConstraint;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.Set;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateUserRequest {
    @NotBlank(message = "NOT_NULL")
    @Size(message = "USERNAME_INVALID", min = 6, max = 20)
    String userName;

    @NotBlank(message = "NOT_NULL")
    @Size(message = "PASSWORD_INVALID", min = 4, max = 20)
    String password;
    String fullName;

    @Size(message = "PHONE_INVALID", min = 10, max = 11)
    String phone;

    @NotBlank(message = "NOT_NULL")
    @Email(message = "EMAIL_INVALID")
    String email;

    @DobConstraint(message = "INVALID_DOB", min = 10)
    LocalDate dob;
    String address;
}
