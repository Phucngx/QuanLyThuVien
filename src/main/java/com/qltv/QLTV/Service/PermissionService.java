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

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class PermissionService {
    PermissionRepository permissionRepository;
    PermissionMapper permissionMapper;

    @PreAuthorize("hasRole('ADMIN')")
    public PermissionResponse createPermission(PermissionRequest request){
        if(permissionRepository.existsByPermissionName(request.getPermissionName())){
            throw new ApplicationException(ErrorCode.NAME_EXISTS);
        }
        Permissions permissions = permissionMapper.toPermission(request);
        return permissionMapper.toPermissionReponse(permissionRepository.save(permissions));
    }

    @PreAuthorize("hasRole('ADMIN')")
    public PermissionResponse updatePermission(String id, PermissionRequest request){
        Permissions permission = permissionRepository.findById(id).orElseThrow(() -> new ApplicationException(ErrorCode.NOT_FOUND));
        permissionMapper.updatePermission(permission, request);
        return permissionMapper.toPermissionReponse(permissionRepository.save(permission));
    }

    @PreAuthorize("hasRole('ADMIN')")
    public void deletePermission(String id){
        permissionRepository.deleteById(id);
    }

    @PreAuthorize("hasRole('ADMIN')")
    public Page<PermissionGetResponse> getAllPermission(int page, int size){
        Pageable pageable = PageRequest.of(page - 1, size);
        return permissionRepository.findAll(pageable)
                .map(permissionMapper::toPermissionGetReponse);
    }

    @PreAuthorize("hasRole('ADMIN')")
    public Page<PermissionResponse> search(String permissionName, int page, int size){
        Pageable pageable = PageRequest.of(page - 1, size);
        return permissionRepository.search(permissionName, pageable).map(permissionMapper::toPermissionReponse);
    }
}
