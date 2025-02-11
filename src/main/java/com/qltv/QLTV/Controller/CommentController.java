package com.qltv.QLTV.Controller;

import com.qltv.QLTV.DTO.Request.Article.UpdateArticleRequest;
import com.qltv.QLTV.DTO.Request.Comment.CommentRequest;
import com.qltv.QLTV.DTO.Request.Comment.UpdateCommentRequest;
import com.qltv.QLTV.DTO.Response.ApiResponse;
import com.qltv.QLTV.DTO.Response.CommentResponse;
import com.qltv.QLTV.Service.CommentService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@RequestMapping("/comments")
public class CommentController {
    CommentService commentService;

    @PostMapping(value = "/create")
    public ApiResponse<CommentResponse> create(@RequestBody CommentRequest request){
        return ApiResponse.<CommentResponse>builder()
                .code(1000)
                .data(commentService.createComment(request))
                .build();
    }

    @PatchMapping(value = "/update/{id}")
    public ApiResponse<CommentResponse> update(@PathVariable String id, @RequestBody UpdateCommentRequest request){
        return ApiResponse.<CommentResponse>builder()
                .code(1000)
                .data(commentService.updateComment(id, request))
                .build();
    }

    @DeleteMapping(value = "/delete/{id}")
    public ApiResponse<String> delete(@PathVariable String id){
        commentService.deleteComment(id);
        return ApiResponse.<String>builder()
                .code(1000)
                .data("Comment has been deleted")
                .build();
    }

    @GetMapping(value = "/get-all")
    public ApiResponse<List<CommentResponse>> getAll(){
        return ApiResponse.<List<CommentResponse>>builder()
                .code(1000)
                .data(commentService.getAllComment())
                .build();
    }
}
