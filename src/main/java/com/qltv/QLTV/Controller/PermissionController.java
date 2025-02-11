package com.qltv.QLTV.Controller;

import com.qltv.QLTV.DTO.Request.PermissionRequest;
import com.qltv.QLTV.DTO.Response.ApiResponse;
import com.qltv.QLTV.DTO.Response.PermissionGetResponse;
import com.qltv.QLTV.DTO.Response.PermissionResponse;
import com.qltv.QLTV.DTO.Response.RoleResponse;
import com.qltv.QLTV.Entity.Permissions;
import com.qltv.QLTV.Service.PermissionService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@RequestMapping("/permissions")
public class PermissionController {
    PermissionService permissionService;

    @PostMapping(value = "create")
    public ApiResponse<PermissionResponse> createPermission(@RequestBody PermissionRequest request){
        return ApiResponse.<PermissionResponse>builder()
                .code(1000)
                .data(permissionService.createPermission(request))
                .build();
    }

    @GetMapping(value = "get-all")
    public ApiResponse<Page<PermissionGetResponse>> getAllPermission(@RequestParam(name = "page") int page, @RequestParam(name = "size") int size){
        return ApiResponse.<Page<PermissionGetResponse>>builder()
                .code(1000)
                .data(permissionService.getAllPermission(page, size))
                .build();
    }

    @PutMapping(value = "update/{id}")
    public ApiResponse<PermissionResponse> updatePermission(@PathVariable String id, @RequestBody PermissionRequest request){
        return ApiResponse.<PermissionResponse>builder()
                .code(1000)
                .data(permissionService.updatePermission(id, request))
                .build();
    }

    @DeleteMapping(value = "delete/{id}")
    public ApiResponse<String> deletePermission(@PathVariable String id){
        permissionService.deletePermission(id);
        return ApiResponse.<String>builder()
                .code(1000)
                .data("Permission has been delete")
                .build();
    }

    @PostMapping(value = "search")
    public ApiResponse<Page<PermissionResponse>> search(@RequestParam(name = "permissionName") String permissionName, @RequestParam(name = "page") int page, @RequestParam(name = "size") int size){
        Page<PermissionResponse> permissionPage = permissionService.search(permissionName, page, size);
        return ApiResponse.<Page<PermissionResponse>>builder()
                .code(1000)
                .data(permissionPage)
                .build();
    }

}
