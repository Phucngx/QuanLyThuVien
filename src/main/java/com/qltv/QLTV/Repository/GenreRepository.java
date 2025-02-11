package com.qltv.QLTV.Repository;

import com.qltv.QLTV.Entity.Genres;
import com.qltv.QLTV.Entity.Permissions;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface GenreRepository extends JpaRepository<Genres, String> {
    Optional<Genres> findByGenreName(String genreName);
    boolean existsByGenreName(String genreName);
    List<Genres> findAllByGenreNameIn(Set<String> genreName);

    @Query(value = "select * from genres where genre_name = ?1", countQuery = "select count(*) from genres where genre_name = ?1", nativeQuery = true)
    Page<Genres> search(String genreName, Pageable pageable);
}
