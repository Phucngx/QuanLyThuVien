package com.qltv.QLTV.DTO.Request;

import com.qltv.QLTV.DTO.Response.PermissionResponse;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoleRequest {
    @NotBlank(message = "NOT_NULL")
    String roleName;

    Set<String> permissions;
}
