package com.qltv.QLTV.DTO.Response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ArticleResponse {
    String articleId;
    String userName;
    String bookTitle;
    String title;
    String content;
    LocalDate createDate;
    LocalDate updateDate;
}
