package com.qltv.QLTV.Controller;

import com.qltv.QLTV.DTO.Request.BookRequest;
import com.qltv.QLTV.DTO.Response.ApiResponse;
import com.qltv.QLTV.DTO.Response.BookResponse;
import com.qltv.QLTV.DTO.Response.BorrowBookResponse;
import com.qltv.QLTV.Entity.Books;
import com.qltv.QLTV.ExcelExport.BookExcelExporter;
import com.qltv.QLTV.Service.BookService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.awt.print.Book;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/books")
@RequiredArgsConstructor
public class BookController {
    BookService bookService;

    @PostMapping("/create")
    public ApiResponse<BookResponse> createBook(@RequestBody BookRequest request){
        return ApiResponse.<BookResponse>builder()
                .code(1000)
                .data(bookService.createBook(request))
                .build();
    }

    @PutMapping("/update/{id}")
    public ApiResponse<BookResponse> updateBook(@PathVariable String id, @RequestBody BookRequest request){
        return ApiResponse.<BookResponse>builder()
                .code(1000)
                .data(bookService.updateBook(id, request))
                .build();
    }

    @DeleteMapping("/delete/{id}")
    public ApiResponse<String> deleteBook(@PathVariable String id){
        bookService.deleteBook(id);
        return ApiResponse.<String>builder()
                .code(1000)
                .data("Book has been deleted")
                .build();
    }

    @GetMapping(value = "/get-detail/{id}")
    public ApiResponse<BookResponse> getDetail(String id){
        return ApiResponse.<BookResponse>builder()
                .code(1000)
                .data(bookService.getDetailBook(id))
                .build();
    }

    @GetMapping(value = "/export")
    public ApiResponse<String> exportToExcel(HttpServletResponse response) throws IOException {
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        String headerKey = "Content-Disposition";

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
        String currentDateTime = dateFormat.format(new Date());
        String fileName = "books" + currentDateTime + ".xlsx";
        String headerValue = "attachment; filename=" + fileName;

        response.setHeader(headerKey, headerValue);
        List<Books> booksList = bookService.getAllBooks();

        BookExcelExporter bookExcelExporter = new BookExcelExporter(booksList);
        bookExcelExporter.export(response);

        return ApiResponse.<String>builder()
                .code(1000)
                .data("Export file book excel successfully")
                .build();
    }

    @GetMapping("/get-all")
    public ApiResponse<Page<BookResponse>> getAllBook(@RequestParam(name = "page") int page, @RequestParam(name = "size") int size){
        return ApiResponse.<Page<BookResponse>>builder()
                .code(1000)
                .data(bookService.getAllBook(page, size))
                .build();
    }
}
