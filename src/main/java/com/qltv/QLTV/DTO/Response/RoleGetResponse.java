package com.qltv.QLTV.DTO.Response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoleGetResponse {
    String roleId;
    String roleName;
    Set<PermissionResponse> permissions;
}
