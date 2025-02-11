package com.qltv.QLTV.Service;

import com.qltv.QLTV.DTO.Request.Comment.CommentRequest;
import com.qltv.QLTV.DTO.Request.Comment.UpdateCommentRequest;
import com.qltv.QLTV.DTO.Response.CommentResponse;
import com.qltv.QLTV.Entity.Articles;
import com.qltv.QLTV.Entity.Comments;
import com.qltv.QLTV.Entity.Users;
import com.qltv.QLTV.Exception.ApplicationException;
import com.qltv.QLTV.Exception.ErrorCode;
import com.qltv.QLTV.Mapper.CommentMapper;
import com.qltv.QLTV.Repository.ArticleRepository;
import com.qltv.QLTV.Repository.CommentRepository;
import com.qltv.QLTV.Repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Transactional
public class CommentService {
    CommentRepository commentRepository;
    UserRepository userRepository;
    ArticleRepository articleRepository;
    CommentMapper commentMapper;

    public Comments getCommentById(String id){
        return commentRepository.findById(id).orElseThrow(() -> new ApplicationException(ErrorCode.COMMENT_NOT_FOUND));
    }

    public CommentResponse createComment(CommentRequest request){
        Users user = userRepository.findById(request.getUserId()).orElseThrow(() -> new ApplicationException(ErrorCode.USER_NOT_FOUND));
        Articles article = articleRepository.findById(request.getArticleId()).orElseThrow(() -> new ApplicationException(ErrorCode.ARTICLE_NOT_FOUND));
        Comments comment = commentMapper.toComment(request);
        comment.setUser(user);
        comment.setArticle(article);
        comment.setCreateDate(LocalDate.now());

        return commentMapper.toCommentResponse(commentRepository.save(comment));
    }

    @PreAuthorize("@commentService.getCommentById(#id).user.userName == authentication.name or hasRole('ADMIN')")
    public CommentResponse updateComment(String id, UpdateCommentRequest request){
        Comments comment = commentRepository.findById(id).orElseThrow(() -> new ApplicationException(ErrorCode.COMMENT_NOT_FOUND));
        commentMapper.updateComment(comment, request);

        return commentMapper.toCommentResponse(commentRepository.save(comment));
    }

    @PreAuthorize("@commentService.getCommentById(#id).user.userName == authentication.name or hasRole('ADMIN')")
    public void deleteComment(String id){
        commentRepository.deleteById(id);
    }

    @PreAuthorize("hasRole('ADMIN')")
    public List<CommentResponse> getAllComment(){
        return commentRepository.findAll().stream().map(commentMapper::toCommentResponse).toList();
    }


}
