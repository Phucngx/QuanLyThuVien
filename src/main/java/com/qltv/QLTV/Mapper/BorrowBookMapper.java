package com.qltv.QLTV.Mapper;

import com.qltv.QLTV.DTO.Request.BookRequest;
import com.qltv.QLTV.DTO.Request.BorrowBookRequest;
import com.qltv.QLTV.DTO.Response.BorrowBookResponse;
import com.qltv.QLTV.Entity.Books;
import com.qltv.QLTV.Entity.BorrowBooks;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface BorrowBookMapper {
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "book", ignore = true)
    BorrowBooks toBorrowBook(BorrowBookRequest request);

    @Mapping(target = "userName", source = "user.userName")
    @Mapping(target = "title", source = "book.title")
    BorrowBookResponse toBorrowBookResponse(BorrowBooks borrowBooks);

    @Mapping(target = "user", ignore = true)
    @Mapping(target = "book", ignore = true)
    void updateBorrowBook(@MappingTarget BorrowBooks borrowBooks, BorrowBookRequest request);
}
