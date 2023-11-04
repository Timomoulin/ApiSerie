package com.ldv.apiseries.controller

import com.ldv.apiseries.dto.SerieDto
import com.ldv.apiseries.service.SerieService
import jakarta.validation.Valid
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
/**
 * Contrôleur pour la gestion des séries.
 */
@RestController
class SerieController {

    // Dépendance injectée pour le service SerieService
    @Autowired
    private lateinit var serieService: SerieService

    /**
     * Récupère toutes les séries disponibles.
     *
     * @return Une liste de SerieDto représentant toutes les séries.
     */
    @GetMapping("/series", "/series/all")
    fun index(): MutableList<SerieDto> {
        return serieService.getAll()
    }

    /**
     * Récupère une série spécifique par son ID.
     *
     * @param id L'ID de la série à récupérer.
     * @return Le SerieDto représentant la série demandée.
     */
    @GetMapping("/series/{id}")
    fun show(@PathVariable id: Long): SerieDto {
        return serieService.getSerieById(id)
    }

    /**
     * Récupère les séries a partir de leurs nom.
     *
     * @param nomRechercher Le nom ou une partie du nom
     * @return Les SerieDto qui contiennent dans leurs nom la valeur rechercher
     */
    @GetMapping("/series/nom/{nomRechercher}")
    fun rechercheNom(@PathVariable nomRechercher: String): List<SerieDto> {
        return serieService.getByNom(nomRechercher)
    }

    /**
     * Crée une nouvelle série en utilisant un SerieDto valide.
     *
     * @param serieDto Le SerieDto contenant les informations de la nouvelle série.
     * @return Le SerieDto représentant la nouvelle série créée.
     */
    @PostMapping("/series")
    fun store(@Valid @RequestBody serieDto: SerieDto): SerieDto {
        return serieService.createSerie(serieDto)
    }

    /**
     * Met à jour une série existante en utilisant un SerieDto valide.
     *
     * @param id L'ID de la série à mettre à jour.
     * @param serieDto Le SerieDto contenant les informations mises à jour de la série.
     * @return Le SerieDto représentant la série mise à jour.
     */
    @PutMapping("/series/{id}")
    fun update(@PathVariable id: Long, @Valid @RequestBody serieDto: SerieDto): SerieDto {
        return serieService.updateSerie(id, serieDto)
    }

    /**
     * Supprime une série par son ID.
     *
     * @param id L'ID de la série à supprimer.
     */
    @DeleteMapping("/series/{id}")
    fun delete(@PathVariable id: Long) {
        serieService.deleteSerie(id)
    }
}