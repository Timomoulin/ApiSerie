package com.ldv.apiseries.model.dao;

import com.ldv.apiseries.model.entity.Serie
import org.springframework.data.jpa.repository.JpaRepository

interface SerieDAO : JpaRepository<Serie, Long> {


    fun findByNomContainsIgnoreCaseOrderByNomAsc(nom: String): List<Serie>

}