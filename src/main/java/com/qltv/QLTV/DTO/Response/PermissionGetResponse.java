package com.qltv.QLTV.DTO.Response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PermissionGetResponse {
    String permissionId;
    String permissionName;
}
