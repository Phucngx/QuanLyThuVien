package com.qltv.QLTV.DTO.Response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.Set;
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserReponse {
    String userId;
    String userName;
    String password;
    String fullName;
    String phone;
    String email;
    LocalDate dob;
    String address;
    Set<RoleResponse> roles;
}
