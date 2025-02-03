package com.qltv.QLTV.Mapper;

import com.qltv.QLTV.DTO.Request.PermissionRequest;
import com.qltv.QLTV.DTO.Request.RoleRequest;
import com.qltv.QLTV.DTO.Response.PermissionResponse;
import com.qltv.QLTV.DTO.Response.RoleGetResponse;
import com.qltv.QLTV.DTO.Response.RoleResponse;
import com.qltv.QLTV.Entity.Permissions;
import com.qltv.QLTV.Entity.Roles;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    @Mapping(target = "permissions", ignore = true)
    Roles toRole(RoleRequest request);
    RoleResponse toRoleResponse(Roles roles);
    RoleGetResponse toRoleGetResponse(Roles roles);

    @Mapping(target = "permissions", ignore = true)
    void updateRole(@MappingTarget Roles roles, RoleRequest request);
}
