package com.qltv.QLTV.Controller;

import com.qltv.QLTV.DTO.Request.User.CreateUserRequest;
import com.qltv.QLTV.DTO.Request.User.UpdateUserRequest;
import com.qltv.QLTV.DTO.Response.ApiResponse;
import com.qltv.QLTV.DTO.Response.UserReponse;
import com.qltv.QLTV.Service.UsersService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    UsersService usersService;

    @PostMapping(value = "create")
    public ApiResponse<UserReponse> createUser(@RequestBody @Valid CreateUserRequest request){
        return ApiResponse.<UserReponse>builder()
                .code(1000)
                .data(usersService.createUser(request))
                .build();
    }

    @PutMapping("/update/{id}")
    public ApiResponse<UserReponse> updateUser(@PathVariable String id, @RequestBody @Valid UpdateUserRequest request){
        return ApiResponse.<UserReponse>builder()
                .code(1000)
                .data(usersService.updateUser(id, request))
                .build();
    }

    @GetMapping(value = "get-all")
    public ApiResponse<List<UserReponse>> getAllUser(){
        return ApiResponse.<List<UserReponse>>builder()
                .code(1000)
                .data(usersService.getAllUser())
                .build();
    }

    @GetMapping(value = "get-detail/{id}")
    public ApiResponse<UserReponse> getDetailUser(@PathVariable String id){
        return ApiResponse.<UserReponse>builder()
                .code(1000)
                .data(usersService.getDetailUser(id))
                .build();
    }

    @GetMapping(value = "get-my-info")
    public ApiResponse<UserReponse> getMyInfo(){
        return ApiResponse.<UserReponse>builder()
                .code(1000)
                .data(usersService.getMyInfo())
                .build();
    }

    @DeleteMapping("/delete/{id}")
    public ApiResponse<String> deleteUser(@PathVariable String id){
        usersService.deleteUser(id);
        return ApiResponse.<String>builder()
                .code(1000)
                .data("User has been deleted")
                .build();
    }
}
