package com.qltv.QLTV.Repository;

import com.qltv.QLTV.Entity.Books;
import com.qltv.QLTV.Entity.BorrowBooks;
import com.qltv.QLTV.Entity.Genres;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BorrowBookRepository extends JpaRepository<BorrowBooks, String> {
    List<BorrowBooks> findAllByUser_UserId(String userId);

//    @Query(value = "select * from borrow_books where genreName = ?1", countQuery = "select count(*) from genres where genreName = ?1", nativeQuery = true)
//    Page<Genres> searchByUserId(String genreName, Pageable pageable);
}
