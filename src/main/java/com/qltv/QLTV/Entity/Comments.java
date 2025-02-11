package com.qltv.QLTV.Entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class Comments {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String commentId;
    String content;
    LocalDate createDate;

    @ManyToOne
    @JoinColumn(name = "userId")
    Users user;

    @ManyToOne
    @JoinColumn(name = "articleId")
    Articles article;

}
