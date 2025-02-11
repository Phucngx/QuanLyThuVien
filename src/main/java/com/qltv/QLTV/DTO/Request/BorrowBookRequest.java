package com.qltv.QLTV.DTO.Request;

import com.qltv.QLTV.Entity.Books;
import com.qltv.QLTV.Entity.Users;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BorrowBookRequest {
    @NotBlank(message = "NOT_NULL")
    String userId;

    @NotBlank(message = "NOT_NULL")
    String bookId;

    @NotBlank(message = "NOT_NULL")
    LocalDate borrowDate;

    @NotBlank(message = "NOT_NULL")
    LocalDate returnDate;
    boolean status;
    String note;
}
