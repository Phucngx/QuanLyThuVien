package com.qltv.QLTV.Mapper;

import com.qltv.QLTV.DTO.Request.Article.ArticleRequest;
import com.qltv.QLTV.DTO.Request.Article.UpdateArticleRequest;
import com.qltv.QLTV.DTO.Response.ArticleResponse;
import com.qltv.QLTV.Entity.Articles;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ArticleMapper {
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "book", ignore = true)
    Articles toArticle(ArticleRequest request);

    @Mapping(target = "userName", source = "user.userName")
    @Mapping(target = "bookTitle", source = "book.title")
    ArticleResponse toArticleResponse(Articles articles);

    void updateArticle(@MappingTarget Articles articles, UpdateArticleRequest request);
}
