package com.qltv.QLTV.Entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Entity
@Table(name = "BorrowBooks")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BorrowBooks {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String borrowingId;
    LocalDate borrowDate;
    LocalDate returnDate;
    boolean status;
    String note;

    @ManyToOne
    @JoinColumn(name = "UserId")
    Users user;

    @ManyToOne
    @JoinColumn(name = "bookId")
    Books book;
}
