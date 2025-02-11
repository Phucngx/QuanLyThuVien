package com.qltv.QLTV.Mapper;

import com.qltv.QLTV.DTO.Request.RoleRequest;
import com.qltv.QLTV.DTO.Request.User.CreateUserRequest;
import com.qltv.QLTV.DTO.Request.User.UpdateUserRequest;
import com.qltv.QLTV.DTO.Response.RoleGetResponse;
import com.qltv.QLTV.DTO.Response.RoleResponse;
import com.qltv.QLTV.DTO.Response.UserExportReponse;
import com.qltv.QLTV.DTO.Response.UserReponse;
import com.qltv.QLTV.Entity.Roles;
import com.qltv.QLTV.Entity.Users;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(target = "roles", ignore = true)
    Users toUser(CreateUserRequest request);
    UserReponse toUserResponse(Users users);
    UserExportReponse toUserExportResponse(Users users);
//    RoleGetResponse toRoleGetResponse(Roles roles);

    @Mapping(target = "roles", ignore = true)
    void updateUser(@MappingTarget Users users, UpdateUserRequest request);
}
