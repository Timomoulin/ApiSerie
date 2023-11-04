package com.ldv.apiseries.service

import com.ldv.apiseries.model.dao.SaisonDAO
import com.ldv.apiseries.dto.SaisonDto
import com.ldv.apiseries.model.entity.Saison
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class SaisonService (@Autowired val saisonDAO: SaisonDAO) {
    fun convertSaisonToDto(saison: Saison): SaisonDto {
        return SaisonDto(
            id = saison.id,
            num = saison.num,
            nom = saison.nom,
            annee = saison.annee,
            nombreEpisode = saison.nombreEpisode,

        )
    }
    fun convertDtoToSaison(dto: SaisonDto): Saison {
        return Saison(
            id = dto.id,
            num = dto.num,
            nom = dto.nom,
            annee = dto.annee,
            nombreEpisode = dto.nombreEpisode)

    }

    @Transactional
    fun saveAll(saisons: List<Saison>) {
        saisonDAO.saveAll(saisons)
    }

    @Transactional
    fun delete(saison: Saison){
        this.saisonDAO.delete(saison)
    }
}