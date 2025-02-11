package com.qltv.QLTV.Controller;

import com.qltv.QLTV.DTO.Request.User.CreateUserRequest;
import com.qltv.QLTV.DTO.Request.User.UpdateUserRequest;
import com.qltv.QLTV.DTO.Response.ApiResponse;
import com.qltv.QLTV.DTO.Response.UserExportReponse;
import com.qltv.QLTV.DTO.Response.UserReponse;
import com.qltv.QLTV.Entity.Users;
import com.qltv.QLTV.ExcelExport.UserExcelExporter;
import com.qltv.QLTV.Service.UsersService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    UsersService usersService;
//    UserExcelExporter excelExporter;

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
    public ApiResponse<Page<UserReponse>> getAllUser(@RequestParam(name = "page") int page, @RequestParam(name = "size") int size){
        return ApiResponse.<Page<UserReponse>>builder()
                .code(1000)
                .data(usersService.getAllUser(page, size))
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

    @DeleteMapping(value = "/delete/{id}")
    public ApiResponse<String> deleteUser(@PathVariable String id){
        usersService.deleteUser(id);
        return ApiResponse.<String>builder()
                .code(1000)
                .data("User has been deleted")
                .build();
    }

    @PostMapping(value = "/search")
    public ApiResponse<Page<UserReponse>> search(@RequestParam(name = "keywords") String keywords, @RequestParam(name = "page") int page, @RequestParam(name = "size") int size){
        Page<UserReponse> userPage = usersService.search(keywords, page, size);
        return ApiResponse.<Page<UserReponse>>builder()
                .code(1000)
                .data(userPage)
                .build();

    }

    @GetMapping(value = "/export")
    public ApiResponse<String> exportToExcel(HttpServletResponse response) throws IOException {
//        response.setContentType("application/octet-stream");
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        String headerKey = "Content-Disposition";

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
        String currentDateTime = dateFormat.format(new Date());
        String fileName = "users_" + currentDateTime + ".xlsx";

        String headerValue = "attachment; filename=" + fileName;

        response.setHeader(headerKey, headerValue);
        List<Users> users = usersService.getAllUsers();

        UserExcelExporter excelExporter = new UserExcelExporter(users);
        excelExporter.export(response);

        return ApiResponse.<String>builder()
                .code(1000)
                .data("Export file user excel successfully")
                .build();
    }

}
