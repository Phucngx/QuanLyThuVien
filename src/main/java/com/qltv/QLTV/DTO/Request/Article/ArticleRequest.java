package com.qltv.QLTV.DTO.Request.Article;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ArticleRequest {
    @NotBlank(message = "NOT_NULL")
    String userId;

    @NotBlank(message = "NOT_NULL")
    String bookId;
    String title;
    String content;
}
