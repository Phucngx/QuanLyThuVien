package com.qltv.QLTV.Service;

import com.qltv.QLTV.DTO.Request.PermissionRequest;
import com.qltv.QLTV.DTO.Response.PermissionGetResponse;
import com.qltv.QLTV.DTO.Response.PermissionResponse;
import com.qltv.QLTV.Entity.Permissions;
import com.qltv.QLTV.Exception.ApplicationException;
import com.qltv.QLTV.Exception.ErrorCode;
import com.qltv.QLTV.Mapper.PermissionMapper;
import com.qltv.QLTV.Repository.PermissionRepository;
import jakarta.servlet.http.PushBuilder;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class PermissionService {
    PermissionRepository permissionRepository;
    PermissionMapper permissionMapper;

    public PermissionResponse createPermission(PermissionRequest request){
        if(permissionRepository.existsByPermissionName(request.getPermissionName())){
            throw new ApplicationException(ErrorCode.NAME_EXISTS);
        }
        Permissions permissions = permissionMapper.toPermission(request);
        return permissionMapper.toPermissionReponse(permissionRepository.save(permissions));
    }

    public PermissionResponse updatePermission(String id, PermissionRequest request){
        Permissions permission = permissionRepository.findById(id).orElseThrow(() -> new ApplicationException(ErrorCode.NOT_FOUND));
        permissionMapper.updatePermission(permission, request);
        return permissionMapper.toPermissionReponse(permissionRepository.save(permission));
    }

    public void deletePermission(String id){
        permissionRepository.deleteById(id);
    }

    public List<PermissionGetResponse> getAllPermission(){
        return permissionRepository.findAll()
                .stream()
                .map(permissionMapper::toPermissionGetReponse)
                .toList();
    }
}
