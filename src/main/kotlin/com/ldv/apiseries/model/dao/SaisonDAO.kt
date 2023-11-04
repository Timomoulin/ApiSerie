package com.ldv.apiseries.model.dao;

import com.ldv.apiseries.model.entity.Saison
import org.springframework.data.jpa.repository.JpaRepository

interface SaisonDAO : JpaRepository<Saison, Long> {
}