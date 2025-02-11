package com.qltv.QLTV.Mapper;

import com.qltv.QLTV.DTO.Request.Comment.CommentRequest;
import com.qltv.QLTV.DTO.Request.Comment.UpdateCommentRequest;
import com.qltv.QLTV.DTO.Response.CommentResponse;
import com.qltv.QLTV.Entity.Comments;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface CommentMapper {
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "article", ignore = true)
    Comments toComment(CommentRequest request);

    @Mapping(target = "userName", source = "user.userName")
    @Mapping(target = "articleId", source = "article.articleId")
    CommentResponse toCommentResponse(Comments comments);

    void updateComment(@MappingTarget Comments comments, UpdateCommentRequest request);
}
