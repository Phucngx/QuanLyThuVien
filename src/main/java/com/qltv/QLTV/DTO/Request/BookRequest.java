package com.qltv.QLTV.DTO.Request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookRequest {
    @NotBlank(message = "NOT_NULL")
    String title;

    @NotBlank(message = "NOT_NULL")
    String author;

    @NotBlank(message = "NOT_NULL")
    String description;

    Set<String> genres;
}
