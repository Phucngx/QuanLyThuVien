package com.qltv.QLTV.Service;

import com.qltv.QLTV.DTO.Request.GenreRequest;
import com.qltv.QLTV.DTO.Response.GenreGetResponse;
import com.qltv.QLTV.DTO.Response.GenreResponse;
import com.qltv.QLTV.Entity.Genres;
import com.qltv.QLTV.Exception.ApplicationException;
import com.qltv.QLTV.Exception.ErrorCode;
import com.qltv.QLTV.Mapper.GenreMapper;
import com.qltv.QLTV.Repository.GenreRepository;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class GenreService {
    GenreRepository genreRepository;
    GenreMapper genreMapper;

    @PreAuthorize("hasRole('ADMIN')")
    public GenreResponse createGenre(GenreRequest request){
        if(genreRepository.existsByGenreName(request.getGenreName())) throw new ApplicationException(ErrorCode.USERNAME_NOT_EXISTS);
        Genres genres = genreMapper.toGenre(request);
        return genreMapper.toGenreResponse(genreRepository.save(genres));
    }

    @PreAuthorize("hasRole('ADMIN')")
    public GenreResponse updateGenre(String id, GenreRequest request){
        Genres genres = genreRepository.findById(id).orElseThrow(() -> new ApplicationException(ErrorCode.GENRE_NOT_FOUND));
        genreMapper.updateGenre(genres, request);
        return genreMapper.toGenreResponse(genreRepository.save(genres));
    }

    @PreAuthorize("hasRole('ADMIN')")
    public Page<GenreGetResponse> getAllGenre(int page, int size){
        Pageable pageable = PageRequest.of(page - 1, size);
        return genreRepository.findAll(pageable).map(genreMapper::toGenreGetResponse);
    }

    @PreAuthorize("hasRole('ADMIN')")
    public void deleteGenre(String id){
        genreRepository.deleteById(id);
    }

}
