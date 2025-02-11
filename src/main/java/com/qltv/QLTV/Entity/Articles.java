package com.qltv.QLTV.Entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class Articles {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String articleId;

    String title;
    String content;
    LocalDate createDate;
    LocalDate updateDate;

    @ManyToOne
    @JoinColumn(name = "userId")
    Users user;

    @ManyToOne
    @JoinColumn(name = "bookId")
    Books book;

    @OneToMany(mappedBy = "article", cascade = CascadeType.ALL)
    List<Comments> comments;
}
