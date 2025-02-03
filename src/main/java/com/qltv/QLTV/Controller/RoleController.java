package com.qltv.QLTV.Controller;

import com.qltv.QLTV.DTO.Request.RoleRequest;
import com.qltv.QLTV.DTO.Response.ApiResponse;
import com.qltv.QLTV.DTO.Response.RoleGetResponse;
import com.qltv.QLTV.DTO.Response.RoleResponse;
import com.qltv.QLTV.Entity.Roles;
import com.qltv.QLTV.Service.RoleService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@RequestMapping("/roles")
public class RoleController {
    RoleService roleService;

    @PostMapping(value = "create")
    public ApiResponse<RoleResponse> createRole(@RequestBody RoleRequest request){
        return ApiResponse.<RoleResponse>builder()
                .code(1000)
                .data(roleService.createRole(request))
                .build();
    }

    @GetMapping(value = "get-all")
    public ApiResponse<List<RoleGetResponse>> getAllRole(){
        return ApiResponse.<List<RoleGetResponse>>builder()
                .code(1000)
                .data(roleService.getAllRole())
                .build();
    }

    @PutMapping("/update/{id}")
    public ApiResponse<RoleResponse> updateRole(@PathVariable String id, @RequestBody RoleRequest request){
        return ApiResponse.<RoleResponse>builder()
                .code(1000)
                .data(roleService.updateRole(id, request))
                .build();
    }

    @DeleteMapping("/delete/{id}")
    public ApiResponse<String> deleteRole(@PathVariable String id){
        roleService.deleteRole(id);
        return ApiResponse.<String>builder()
                .code(1000)
                .data("Permission has been delete")
                .build();
    }

}
