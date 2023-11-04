package com.ldv.apiseries.model.dao;

import com.ldv.apiseries.model.entity.Genre
import org.springframework.data.jpa.repository.JpaRepository


interface GenreDAO : JpaRepository<Genre, Long> {
}