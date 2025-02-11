package com.qltv.QLTV.Mapper;

import com.qltv.QLTV.DTO.Request.BookRequest;
import com.qltv.QLTV.DTO.Response.BookResponse;
import com.qltv.QLTV.Entity.Books;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface BookMapper {
    @Mapping(target = "genres", ignore = true)
    Books toBook(BookRequest request);
    BookResponse toBookResponse(Books books);

    @Mapping(target = "genres", ignore = true)
    void updateBook(@MappingTarget Books books, BookRequest request);
}
