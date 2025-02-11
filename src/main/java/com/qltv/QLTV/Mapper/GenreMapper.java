package com.qltv.QLTV.Mapper;

import com.qltv.QLTV.DTO.Request.GenreRequest;
import com.qltv.QLTV.DTO.Response.GenreGetResponse;
import com.qltv.QLTV.DTO.Response.GenreResponse;
import com.qltv.QLTV.Entity.Genres;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface GenreMapper {
    Genres toGenre(GenreRequest request);
    GenreResponse toGenreResponse(Genres genres);
    GenreGetResponse toGenreGetResponse(Genres genres);
    void updateGenre(@MappingTarget Genres genres, GenreRequest request);
}
