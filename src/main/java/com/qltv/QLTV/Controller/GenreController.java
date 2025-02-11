package com.qltv.QLTV.Controller;

import com.qltv.QLTV.DTO.Request.GenreRequest;
import com.qltv.QLTV.DTO.Response.ApiResponse;
import com.qltv.QLTV.DTO.Response.GenreGetResponse;
import com.qltv.QLTV.DTO.Response.GenreResponse;
import com.qltv.QLTV.Repository.GenreRepository;
import com.qltv.QLTV.Service.GenreService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/genres")
public class GenreController {
    GenreService genreService;

    @PostMapping(value = "/create")
    public ApiResponse<GenreResponse> createGenre(@RequestBody GenreRequest request){
        return ApiResponse.<GenreResponse>builder()
                .code(1000)
                .data(genreService.createGenre(request))
                .build();
    }

    @PutMapping(value = "/update/{id}")
    public ApiResponse<GenreResponse> updateGenre(@PathVariable String id, @RequestBody GenreRequest request){
        return ApiResponse.<GenreResponse>builder()
                .code(1000)
                .data(genreService.updateGenre(id, request))
                .build();
    }

    @DeleteMapping(value = "/delete/{id}")
    public ApiResponse<String> deleteGenre(@PathVariable String id){
        genreService.deleteGenre(id);
        return ApiResponse.<String>builder()
                .code(1000)
                .data("Genre has been deleted")
                .build();
    }

    @GetMapping(value = "get-all")
    public ApiResponse<Page<GenreGetResponse>> getAllGenre(@RequestParam(name = "page") int page, @RequestParam(name = "size") int size){
        return ApiResponse.<Page<GenreGetResponse>>builder()
                .code(1000)
                .data(genreService.getAllGenre(page, size))
                .build();
    }

}
