package com.qltv.QLTV.Service;

import com.qltv.QLTV.DTO.Request.Article.ArticleRequest;
import com.qltv.QLTV.DTO.Request.Article.UpdateArticleRequest;
import com.qltv.QLTV.DTO.Response.ArticleResponse;
import com.qltv.QLTV.DTO.Response.CommentResponse;
import com.qltv.QLTV.Entity.Articles;
import com.qltv.QLTV.Entity.Books;
import com.qltv.QLTV.Entity.Comments;
import com.qltv.QLTV.Entity.Users;
import com.qltv.QLTV.Exception.ApplicationException;
import com.qltv.QLTV.Exception.ErrorCode;
import com.qltv.QLTV.Mapper.ArticleMapper;
import com.qltv.QLTV.Mapper.CommentMapper;
import com.qltv.QLTV.Repository.ArticleRepository;
import com.qltv.QLTV.Repository.BookRepository;
import com.qltv.QLTV.Repository.CommentRepository;
import com.qltv.QLTV.Repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Transactional
public class ArticleService {
    ArticleRepository articleRepository;
    ArticleMapper articleMapper;
    BookRepository bookRepository;
    UserRepository userRepository;
    CommentMapper commentMapper;
    private final CommentRepository commentRepository;

    public Articles getArticleById(String id) {
        return articleRepository.findById(id).orElseThrow(() -> new ApplicationException(ErrorCode.ARTICLE_NOT_FOUND));
    }

    public ArticleResponse createArticle(ArticleRequest request){
        Users user = userRepository.findById(request.getUserId()).orElseThrow(() -> new ApplicationException(ErrorCode.USER_NOT_FOUND));
        Books book = bookRepository.findById(request.getBookId()).orElseThrow(() -> new ApplicationException(ErrorCode.BOOK_NOT_FOUND));
        Articles article = articleMapper.toArticle(request);
        article.setUser(user);
        article.setBook(book);
        article.setCreateDate(LocalDate.now());
        article.setUpdateDate(LocalDate.now());

        return articleMapper.toArticleResponse(articleRepository.save(article));
    }

    @PreAuthorize("@articleService.getArticleById(#id).user.userName == authentication.name or hasRole('ADMIN')")
    public ArticleResponse updateArticle(String id, UpdateArticleRequest request){
        Articles article = articleRepository.findById(id).orElseThrow(() -> new ApplicationException(ErrorCode.ARTICLE_NOT_FOUND));
        articleMapper.updateArticle(article, request);
        article.setUpdateDate(LocalDate.now());

        return articleMapper.toArticleResponse(articleRepository.save(article));
    }

    @PreAuthorize("@articleService.getArticleById(#id).user.userName == authentication.name or hasRole('ADMIN')")
    public void deleteArticle(String id){
        articleRepository.deleteById(id);
    }

    @PreAuthorize("hasRole('ADMIN')")
    public Page<ArticleResponse> getAllArticle(int page, int size){
        Pageable pageable = PageRequest.of(page - 1, size);
        return articleRepository.findAll(pageable).map(articleMapper::toArticleResponse);
    }

    public List<CommentResponse> getAllCommentInArticle(String id){
        Articles article = articleRepository.findById(id).orElseThrow(() -> new ApplicationException(ErrorCode.ARTICLE_NOT_FOUND));
        List<Comments> comments = article.getComments();
        return comments.stream().map(commentMapper::toCommentResponse).toList();
    }

    public ArticleResponse getDetailArticle(String id){
        Articles article = articleRepository.findById(id).orElseThrow(() -> new ApplicationException(ErrorCode.ARTICLE_NOT_FOUND));
        return articleMapper.toArticleResponse(article);
    }

}
