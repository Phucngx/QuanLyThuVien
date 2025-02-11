package com.qltv.QLTV.Entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Entity
@Table(name = "Genres")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class Genres {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String genreId;
    String genreName;

    @ManyToMany(mappedBy = "genres")
    Set<Books> books;
}
