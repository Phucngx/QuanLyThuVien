package com.qltv.QLTV.DTO.Response;

import com.qltv.QLTV.Entity.Books;
import com.qltv.QLTV.Entity.Users;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BorrowBookResponse {
    String borrowingId;
    String userName;
    String title;
    LocalDate borrowDate;
    LocalDate returnDate;
    boolean status;
    String note;
}
