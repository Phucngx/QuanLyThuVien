package com.qltv.QLTV.Controller;

import com.qltv.QLTV.DTO.Request.PermissionRequest;
import com.qltv.QLTV.DTO.Response.ApiResponse;
import com.qltv.QLTV.DTO.Response.PermissionGetResponse;
import com.qltv.QLTV.DTO.Response.PermissionResponse;
import com.qltv.QLTV.Entity.Permissions;
import com.qltv.QLTV.Service.PermissionService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
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
    public ApiResponse<List<PermissionGetResponse>> getAllPermission(){
        return ApiResponse.<List<PermissionGetResponse>>builder()
                .code(1000)
                .data(permissionService.getAllPermission())
                .build();
    }

    @PutMapping("update/{id}")
    public ApiResponse<PermissionResponse> updatePermission(@PathVariable String id, @RequestBody PermissionRequest request){
        return ApiResponse.<PermissionResponse>builder()
                .code(1000)
                .data(permissionService.updatePermission(id, request))
                .build();
    }

    @DeleteMapping("delete/{id}")
    public ApiResponse<String> deletePermission(@PathVariable String id){
        permissionService.deletePermission(id);
        return ApiResponse.<String>builder()
                .code(1000)
                .data("Permission has been delete")
                .build();
    }

}
