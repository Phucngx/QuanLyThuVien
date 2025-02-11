package com.qltv.QLTV.Service;

import com.qltv.QLTV.DTO.Request.BookRequest;
import com.qltv.QLTV.DTO.Response.BookResponse;
import com.qltv.QLTV.Entity.Books;
import com.qltv.QLTV.Entity.Genres;
import com.qltv.QLTV.Exception.ApplicationException;
import com.qltv.QLTV.Exception.ErrorCode;
import com.qltv.QLTV.Mapper.BookMapper;
import com.qltv.QLTV.Repository.BookRepository;
import com.qltv.QLTV.Repository.GenreRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class BookService {
    BookRepository bookRepository;
    BookMapper bookMapper;
    GenreRepository genreRepository;

    @PreAuthorize("hasRole('ADMIN')")
    public BookResponse createBook(BookRequest request){
        Books books = bookMapper.toBook(request);
        var genres = genreRepository.findAllByGenreNameIn(request.getGenres());
        if(genres.isEmpty()) throw new ApplicationException(ErrorCode.GENRE_NOT_FOUND);
        books.setGenres(new HashSet<>(genres));
        return bookMapper.toBookResponse(bookRepository.save(books));
    }

    @PreAuthorize("hasRole('ADMIN')")
    public BookResponse updateBook(String id, BookRequest request){
        Books books = bookRepository.findById(id).orElseThrow(() -> new ApplicationException(ErrorCode.BOOK_NOT_FOUND));
        bookMapper.updateBook(books, request);
        var genres = genreRepository.findAllByGenreNameIn(request.getGenres());
        books.setGenres(new HashSet<>(genres));
        return bookMapper.toBookResponse(bookRepository.save(books));
    }

    @PreAuthorize("hasRole('ADMIN')")
    public void deleteBook(String id){
        bookRepository.deleteById(id);
    }

    @PreAuthorize("hasRole('ADMIN')")
    public Page<BookResponse> getAllBook(int page, int size){
        Pageable pageable = PageRequest.of(page - 1, size);
        return bookRepository.findAll(pageable).map(bookMapper::toBookResponse);
    }

    @PreAuthorize("hasRole('ADMIN')")
    public List<Books> getAllBooks(){
        return bookRepository.findAll();
    }

    public BookResponse getDetailBook(String id){
        Books book = bookRepository.findById(id).orElseThrow(() -> new ApplicationException(ErrorCode.BOOK_NOT_FOUND));
        return bookMapper.toBookResponse(book);
    }

    public Page<BookResponse> search(String title, int page, int size){
        Pageable pageable = PageRequest.of(page - 1, size);
        return bookRepository.search(title, pageable).map(bookMapper::toBookResponse);
    }

}
