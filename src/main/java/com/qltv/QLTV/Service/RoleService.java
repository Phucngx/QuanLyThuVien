package com.qltv.QLTV.Service;

import com.qltv.QLTV.DTO.Request.RoleRequest;
import com.qltv.QLTV.DTO.Response.RoleGetResponse;
import com.qltv.QLTV.DTO.Response.RoleResponse;
import com.qltv.QLTV.Entity.Roles;
import com.qltv.QLTV.Exception.ApplicationException;
import com.qltv.QLTV.Exception.ErrorCode;
import com.qltv.QLTV.Mapper.RoleMapper;
import com.qltv.QLTV.Repository.PermissionRepository;
import com.qltv.QLTV.Repository.RoleRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class RoleService {
    RoleRepository roleRepository;
    RoleMapper roleMapper;
    PermissionRepository permissionRepository;

    public RoleResponse createRole(RoleRequest request){
        if(roleRepository.existsByRoleName(request.getRoleName())){
            throw new ApplicationException(ErrorCode.NAME_EXISTS);
        }
        Roles roles = roleMapper.toRole(request);
        var permissions = permissionRepository.findAllByPermissionNameIn(request.getPermissions());
        roles.setPermissions(new HashSet<>(permissions));
        return roleMapper.toRoleResponse(roleRepository.save(roles));
    }

    public RoleResponse updateRole(String id, RoleRequest request){
        Roles roles = roleRepository.findById(id).orElseThrow(() -> new ApplicationException(ErrorCode.NOT_FOUND));
        roleMapper.updateRole(roles, request);
        var permissions = permissionRepository.findAllByPermissionNameIn(request.getPermissions());
        roles.setPermissions(new HashSet<>(permissions));
        return roleMapper.toRoleResponse(roleRepository.save(roles));
    }

    public void deleteRole(String id){
        roleRepository.deleteById(id);
    }

    public List<RoleGetResponse> getAllRole(){
        return roleRepository.findAll()
                .stream()
                .map(roleMapper::toRoleGetResponse)
                .toList();
    }
}
