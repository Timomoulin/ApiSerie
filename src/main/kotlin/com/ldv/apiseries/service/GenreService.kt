package com.ldv.apiseries.service

import com.ldv.apiseries.model.dao.GenreDAO
import com.ldv.apiseries.dto.GenreDto
import com.ldv.apiseries.model.entity.Genre
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class GenreService (@Autowired genreDAO: GenreDAO) {

    fun convertGenreToDto(genre: Genre): GenreDto {
        return GenreDto(
            id = genre.id,
            nom = genre.nom,
        )
    }

    fun convertDtoToGenre(dto: GenreDto): Genre {
        return Genre(
            id = dto.id,
            nom = dto.nom
        )
    }
}