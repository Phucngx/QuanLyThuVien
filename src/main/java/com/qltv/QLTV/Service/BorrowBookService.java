package com.qltv.QLTV.Service;

import com.qltv.QLTV.DTO.Request.BorrowBookRequest;
import com.qltv.QLTV.DTO.Response.BorrowBookResponse;
import com.qltv.QLTV.Entity.Articles;
import com.qltv.QLTV.Entity.Books;
import com.qltv.QLTV.Entity.BorrowBooks;
import com.qltv.QLTV.Entity.Users;
import com.qltv.QLTV.Exception.ApplicationException;
import com.qltv.QLTV.Exception.ErrorCode;
import com.qltv.QLTV.Mapper.BorrowBookMapper;
import com.qltv.QLTV.Repository.BookRepository;
import com.qltv.QLTV.Repository.BorrowBookRepository;
import com.qltv.QLTV.Repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Transactional
public class BorrowBookService {
    BorrowBookRepository borrowBookRepository;
    BorrowBookMapper borrowBookMapper;
    UserRepository userRepository;
    BookRepository bookRepository;

    public BorrowBooks getBorrowBookById(String id) {
        return borrowBookRepository.findById(id).orElseThrow(() -> new ApplicationException(ErrorCode.NOT_FOUND));
    }

    public BorrowBookResponse createBorrowBook(BorrowBookRequest request){
        Users user = userRepository.findById(request.getUserId()).orElseThrow(() -> new ApplicationException(ErrorCode.USER_NOT_FOUND));
        Books book = bookRepository.findById(request.getBookId()).orElseThrow(() -> new ApplicationException((ErrorCode.BOOK_NOT_FOUND)));
        BorrowBooks borrowBook = borrowBookMapper.toBorrowBook(request);
        borrowBook.setUser(user);
        borrowBook.setBook(book);
        borrowBookRepository.save(borrowBook);

        return borrowBookMapper.toBorrowBookResponse(borrowBook);
    }

    @PreAuthorize("@borrowBookService.getBorrowBookById(#id).user.userName == authentication.name or hasRole('ADMIN')")
    public BorrowBookResponse updateBorrowBook(String id, BorrowBookRequest request){
        BorrowBooks borrowBook = borrowBookRepository.findById(id).orElseThrow(() -> new ApplicationException(ErrorCode.NOT_FOUND));
        Users user = userRepository.findById(request.getUserId()).orElseThrow(() -> new ApplicationException(ErrorCode.USER_NOT_FOUND));
        Books book = bookRepository.findById(request.getBookId()).orElseThrow(() -> new ApplicationException((ErrorCode.BOOK_NOT_FOUND)));
        borrowBookMapper.updateBorrowBook(borrowBook, request);
        borrowBook.setUser(user);
        borrowBook.setBook(book);
        borrowBookRepository.save(borrowBook);

        return borrowBookMapper.toBorrowBookResponse(borrowBook);
    }

    @PreAuthorize("@borrowBookService.getBorrowBookById(#id).user.userName == authentication.name or hasRole('ADMIN')")
    public void deleteBorrowBook(String id){
        borrowBookRepository.deleteById(id);
    }

    @PreAuthorize("hasRole('ADMIN')")
    public Page<BorrowBookResponse> getAllBorrowBook(int page, int size){
        Pageable pageable = PageRequest.of(page - 1, size);
        return borrowBookRepository.findAll(pageable).map(borrowBookMapper::toBorrowBookResponse);
    }

    public List<BorrowBookResponse> getMyBorrowBook(){
        SecurityContext securityContext = SecurityContextHolder.getContext();
        String username = securityContext.getAuthentication().getName();
        Users user = userRepository.findByUserName(username).orElseThrow(() -> new ApplicationException(ErrorCode.USER_NOT_FOUND));
        List<BorrowBooks> borrowBooks = borrowBookRepository.findAllByUser_UserId(user.getUserId());
        if(borrowBooks.isEmpty()) throw new ApplicationException(ErrorCode.BORROW_BOOK_NOT_FOUND);
        return borrowBooks.stream().map(borrowBookMapper::toBorrowBookResponse).toList();
    }

    @PreAuthorize("@borrowBookService.getBorrowBookById(#id).user.userName == authentication.name or hasRole('ADMIN')")
    public BorrowBookResponse getDetailBorrowBook(String id){
        BorrowBooks borrowBook = borrowBookRepository.findById(id).orElseThrow(() -> new ApplicationException(ErrorCode.BORROW_BOOK_NOT_FOUND));
        return borrowBookMapper.toBorrowBookResponse(borrowBook);
    }
}
