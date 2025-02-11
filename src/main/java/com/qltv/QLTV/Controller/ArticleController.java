package com.qltv.QLTV.Controller;

import com.qltv.QLTV.DTO.Request.Article.ArticleRequest;
import com.qltv.QLTV.DTO.Request.Article.UpdateArticleRequest;
import com.qltv.QLTV.DTO.Response.ApiResponse;
import com.qltv.QLTV.DTO.Response.ArticleResponse;
import com.qltv.QLTV.DTO.Response.BorrowBookResponse;
import com.qltv.QLTV.DTO.Response.CommentResponse;
import com.qltv.QLTV.Service.ArticleService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@RequestMapping("/article")
public class ArticleController {
    ArticleService articleService;

    @PostMapping(value = "/create")
    public ApiResponse<ArticleResponse> create(@RequestBody ArticleRequest request){
        return ApiResponse.<ArticleResponse>builder()
                .code(1000)
                .data(articleService.createArticle(request))
                .build();
    }

    @PatchMapping(value = "/update/{id}")
    public ApiResponse<ArticleResponse> update(@PathVariable String id, @RequestBody UpdateArticleRequest request){
        return ApiResponse.<ArticleResponse>builder()
                .code(1000)
                .data(articleService.updateArticle(id, request))
                .build();
    }

    @DeleteMapping(value = "/delete/{id}")
    public ApiResponse<String> delete(@PathVariable String id){
        articleService.deleteArticle(id);
        return ApiResponse.<String>builder()
                .code(1000)
                .data("Article has been deleted")
                .build();
    }

    @GetMapping(value = "/get-all")
    public ApiResponse<Page<ArticleResponse>> getAll(@RequestParam(name = "page") int page, @RequestParam(name = "size") int size){
        return ApiResponse.<Page<ArticleResponse>>builder()
                .code(1000)
                .data(articleService.getAllArticle(page, size))
                .build();
    }

    @GetMapping(value = "/get-detail/{id}")
    public ApiResponse<ArticleResponse> getDetail(String id){
        return ApiResponse.<ArticleResponse>builder()
                .code(1000)
                .data(articleService.getDetailArticle(id))
                .build();
    }

    @GetMapping(value = "get-all-comments/{id}")
    public ApiResponse<List<CommentResponse>> getAllComment(@PathVariable String id){
        return ApiResponse.<List<CommentResponse>>builder()
                .code(1000)
                .data(articleService.getAllCommentInArticle(id))
                .build();
    }
}
