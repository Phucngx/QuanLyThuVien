package com.qltv.QLTV.Mapper;

import com.qltv.QLTV.DTO.Request.PermissionRequest;
import com.qltv.QLTV.DTO.Response.PermissionGetResponse;
import com.qltv.QLTV.DTO.Response.PermissionResponse;
import com.qltv.QLTV.Entity.Permissions;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface PermissionMapper {
    Permissions toPermission(PermissionRequest request);
    PermissionResponse toPermissionReponse(Permissions permission);
    PermissionGetResponse toPermissionGetReponse(Permissions permission);
    void updatePermission(@MappingTarget Permissions permissions, PermissionRequest request);
}
