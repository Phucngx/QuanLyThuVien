package com.qltv.QLTV.Entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;
import java.util.Set;

@Entity
@Table(name = "Books")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class Books {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String bookId;
    String title;
    String author;
    String description;

    @ManyToMany
    @JoinTable(
            name = "Books_Genres",
            joinColumns = @JoinColumn(name = "bookId"),
            inverseJoinColumns = @JoinColumn(name = "genreId")
    )
    Set<Genres> genres;

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL)
    List<BorrowBooks> borrowBooks;

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL)
    List<Articles> articles;
}
