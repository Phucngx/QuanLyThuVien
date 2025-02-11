package com.qltv.QLTV.Controller;

import com.qltv.QLTV.DTO.Request.RoleRequest;
import com.qltv.QLTV.DTO.Response.ApiResponse;
import com.qltv.QLTV.DTO.Response.RoleGetResponse;
import com.qltv.QLTV.DTO.Response.RoleResponse;
import com.qltv.QLTV.DTO.Response.UserReponse;
import com.qltv.QLTV.Entity.Roles;
import com.qltv.QLTV.Service.RoleService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
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
    public ApiResponse<Page<RoleGetResponse>> getAllRole(@RequestParam(name = "page") int page, @RequestParam(name = "size") int size){
        return ApiResponse.<Page<RoleGetResponse>>builder()
                .code(1000)
                .data(roleService.getAllRole(page, size))
                .build();
    }

    @PutMapping(value = "/update/{id}")
    public ApiResponse<RoleResponse> updateRole(@PathVariable String id, @RequestBody RoleRequest request){
        return ApiResponse.<RoleResponse>builder()
                .code(1000)
                .data(roleService.updateRole(id, request))
                .build();
    }

    @DeleteMapping(value = "/delete/{id}")
    public ApiResponse<String> deleteRole(@PathVariable String id){
        roleService.deleteRole(id);
        return ApiResponse.<String>builder()
                .code(1000)
                .data("Permission has been delete")
                .build();
    }

    @GetMapping(value = "/get-detail/{id}")
    public ApiResponse<RoleResponse> getDetail(@PathVariable String id){
        return ApiResponse.<RoleResponse>builder()
                .code(1000)
                .data(roleService.getDetailRole(id))
                .build();
    }

    @PostMapping(value = "/search")
    public ApiResponse<Page<RoleResponse>> search(@RequestParam(name = "roleName") String roleName , @RequestParam(name = "page") int page, @RequestParam(name = "size") int size){
        Page<RoleResponse> rolePage = roleService.search(roleName, page, size);
        return ApiResponse.<Page<RoleResponse>>builder()
                .code(1000)
                .data(rolePage)
                .build();
    }


}
