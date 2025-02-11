package com.qltv.QLTV.Controller;

import com.qltv.QLTV.DTO.Request.BorrowBookRequest;
import com.qltv.QLTV.DTO.Response.ApiResponse;
import com.qltv.QLTV.DTO.Response.BorrowBookResponse;
import com.qltv.QLTV.DTO.Response.RoleResponse;
import com.qltv.QLTV.Service.BorrowBookService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@RequestMapping("/borrow-books")
public class BorrowBookController {
    BorrowBookService borrowBookService;

    @PostMapping(value = "/create")
    public ApiResponse<BorrowBookResponse> create(@RequestBody BorrowBookRequest request){
        return ApiResponse.<BorrowBookResponse>builder()
                .code(1000)
                .data(borrowBookService.createBorrowBook(request))
                .build();
    }

    @PutMapping(value = "/update/{id}")
    public ApiResponse<BorrowBookResponse> update(@PathVariable String id, @RequestBody BorrowBookRequest request){
        return ApiResponse.<BorrowBookResponse>builder()
                .code(1000)
                .data(borrowBookService.updateBorrowBook(id, request))
                .build();
    }

    @DeleteMapping(value = "/delete/{id}")
    public ApiResponse<String> delete(@PathVariable String id){
        borrowBookService.deleteBorrowBook(id);
        return ApiResponse.<String>builder()
                .code(1000)
                .data("Borrow book has been deleted")
                .build();
    }

    @GetMapping(value = "/get-all")
    public ApiResponse<Page<BorrowBookResponse>> getAll(@RequestParam(name = "page") int page, @RequestParam(name = "size") int size){
        return ApiResponse.<Page<BorrowBookResponse>>builder()
                .code(1000)
                .data(borrowBookService.getAllBorrowBook(page, size))
                .build();
    }

    @GetMapping(value = "/get-my-borrow-book")
    public ApiResponse<List<BorrowBookResponse>> getMyBorrowBook(){
        return ApiResponse.<List<BorrowBookResponse>>builder()
                .code(1000)
                .data(borrowBookService.getMyBorrowBook())
                .build();
    }

    @GetMapping(value = "/get-detail/{id}")
    public ApiResponse<BorrowBookResponse> getDetail(String id){
        return ApiResponse.<BorrowBookResponse>builder()
                .code(1000)
                .data(borrowBookService.getDetailBorrowBook(id))
                .build();
    }
}
