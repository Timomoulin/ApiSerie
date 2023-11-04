package com.ldv.apiseries.service

import com.ldv.apiseries.model.dao.GenreDAO
import com.ldv.apiseries.dto.GenreDto
import com.ldv.apiseries.model.entity.Genre
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class GenreService (@Autowired val genreDAO: GenreDAO) {

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

    @Transactional
    fun saveAll(genres: List<Genre>) {
        this.genreDAO.saveAll(genres)
    }

    @Transactional
    fun save(genre: Genre) {
        this.genreDAO.save(genre)
    }
}