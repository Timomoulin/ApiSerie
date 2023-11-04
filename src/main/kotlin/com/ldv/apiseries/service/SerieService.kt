package com.ldv.apiseries.service

import com.ldv.apiseries.model.dao.SerieDAO
import com.ldv.apiseries.dto.SerieDto
import com.ldv.apiseries.model.entity.Serie
import jakarta.persistence.EntityNotFoundException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import kotlin.jvm.optionals.getOrNull

/**
 * Service pour la gestion des entités Serie.
 */
@Service
class SerieService () {

    // Dépendances injectées pour les DAO et les services associés
    @Autowired
    private lateinit var serieDAO: SerieDAO
    @Autowired
    private lateinit var genreService: GenreService
    @Autowired
    private lateinit var saisonService: SaisonService

    /**
     * Convertit une entité Serie en un SerieDto.
     *
     * @param serie L'entité Serie à convertir.
     * @return Le SerieDto correspondant.
     */
    fun convertSerieToDto(serie: Serie): SerieDto {
        // Conversion des genres et saisons associés en DTOs
        val genreDtos = serie.genres.map { this.genreService.convertGenreToDto(it) }
        val saisonDtos = serie.saisons.map { this.saisonService.convertSaisonToDto(it) }

        // Crée un objet SerieDto à partir des données de l'entité Serie
        return SerieDto(
            id = serie.id,
            nom = serie.nom,
            lienImage = serie.lienImage,
            vo = serie.vo,
            premise = serie.premise,
            genres = genreDtos,
            saisons = saisonDtos
        )
    }

    fun getByNom(unNom:String):List<SerieDto>{
        return this.serieDAO.findByNomContainsIgnoreCaseOrderByNomAsc(unNom).map { this.convertSerieToDto(it) };
    }

    /**
     * Convertit un SerieDto en une entité Serie.
     *
     * @param dto Le SerieDto à convertir.
     * @return L'entité Serie correspondante.
     */
    fun convertDtoToSerie(dto: SerieDto): Serie {
        // Conversion des DTO genres et saisons en entités associées
        val serie = Serie(
            id = dto.id,
            nom = dto.nom,
            lienImage = dto.lienImage,
            vo = dto.vo,
            premise = dto.premise
        )
        val genres = dto.genres.map { this.genreService.convertDtoToGenre(it) }
        val saisons = dto.saisons.map { this.saisonService.convertDtoToSaison(it) }
        // Associe les entités genres et saisons à la série
        serie.genres.addAll(genres)
        serie.saisons.addAll(saisons)

        return serie
    }

    /**
     * Récupère toutes les séries sous forme d'objets SerieDto.
     *
     * @return Une liste d'objets SerieDto représentant toutes les séries.
     */
    @Transactional
    fun getAll(): MutableList<SerieDto> {
        val series = serieDAO.findAll()
        val resultat: MutableList<SerieDto> = mutableListOf()
        for (uneSerie in series) {
            val uneSerieDto = this.convertSerieToDto(uneSerie)
            resultat.add(uneSerieDto)
        }
        return resultat
    }

    /**
     * Récupère une série par son ID.
     *
     * @param id L'ID de la série à récupérer.
     * @return Le SerieDto représentant la série demandée.
     * @throws EntityNotFoundException si la série avec l'ID spécifié n'est pas trouvée.
     */
    @Transactional
    fun getSerieById(id: Long): SerieDto {
        val serie = this.serieDAO.findById(id)
            .orElseThrow { EntityNotFoundException("Série introuvable avec l'ID : $id") }
        return convertSerieToDto(serie)
    }

    /**
     * Crée une nouvelle série à partir d'un SerieDto.
     *
     * @param serieDto Le SerieDto à partir duquel créer une nouvelle série.
     * @return Le SerieDto représentant la nouvelle série créée.
     */
@Transactional
    fun createSerie(serieDto: SerieDto): SerieDto {
        val serie = convertDtoToSerie(serieDto)
        val savedSerie = this.serieDAO.save(serie)
        savedSerie.genres.forEach({it.series.add(savedSerie)})
        savedSerie.saisons.forEach({it.serie=savedSerie})
        this.genreService.saveAll(savedSerie.genres)
        this.saisonService.saveAll(savedSerie.saisons)
        return convertSerieToDto(savedSerie)
    }

    /**
     * Met à jour une série existante avec un SerieDto.
     *
     * @param id L'ID de la série à mettre à jour.
     * @param serieDto Le SerieDto contenant les informations mises à jour de la série.
     * @return Le SerieDto représentant la série mise à jour.
     * @throws EntityNotFoundException si la série spécifiée n'est pas trouvée.
     */
    fun updateSerie(id: Long, serieDto: SerieDto): SerieDto {
        val existingSerie = this.serieDAO.findById(id)
            .orElseThrow { EntityNotFoundException("Série introuvable avec l'ID : $id") }

        val updatedSerie = convertDtoToSerie(serieDto)
        updatedSerie.id = existingSerie.id // Assurez-vous que l'ID ne change pas

        // Associez les genres de la série mise à jour à la série existante
        for (genre in updatedSerie.genres) {
            if (!existingSerie.genres.contains(genre)) {
                genre.series.add(existingSerie)
            }
        }
        // Retirez la référence de la série existante des genres qui ne sont plus associés à la série mise à jour
        for (genre in existingSerie.genres.toList()) {
            if (updatedSerie.genres.find ({ it.id==genre.id })==null) {
                genre.series.remove(existingSerie)
                this.genreService.save(genre)
            }
        }
        // Mettez à jour les saisons avec la série existante
        for (saison in updatedSerie.saisons) {
            saison.serie = existingSerie
        }
        for (saison in existingSerie.saisons.toList()) {

            if (updatedSerie.saisons.find({it.id==saison.id})==null) {
                this.saisonService.delete(saison)
            }
        }

        // Sauvegardez la série mise à jour
        val savedSerie = this.serieDAO.save(updatedSerie)

        // Sauvegardez les genres et saisons mis à jour
        this.genreService.saveAll(savedSerie.genres)
        this.saisonService.saveAll(savedSerie.saisons)

        return convertSerieToDto(savedSerie)
    }


    /**
     * Supprime une série par son ID.
     *
     * @param id L'ID de la série à supprimer.
     * @throws EntityNotFoundException si la série spécifiée n'est pas trouvée.
     */
    @Transactional
    fun deleteSerie(id: Long) {
        val serie=this.serieDAO.findById(id).getOrNull()
        if (serie==null) {
            throw EntityNotFoundException("Série introuvable avec l'ID : $id")
        }
        else{
            serie.saisons.forEach({this.saisonService.delete(it)})
            serie.genres.forEach({it.series.remove(serie)})
            this.genreService.saveAll(serie.genres)
            this.serieDAO.deleteById(id)
        }

    }
}