package com.ldv.apiseries.model.entity

import jakarta.persistence.*
import org.hibernate.Hibernate

/**
 * Entité représentant une série.
 */
@Entity
class Serie constructor(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    var id: Long? = null,
    var nom: String,
    var lienImage: String?,
    var vo: String,
    var premise: String?,

    // Association One-to-Many avec Saison (chaque série peut avoir plusieurs saisons)
    @OneToMany(mappedBy = "serie",cascade = arrayOf(CascadeType.REMOVE), orphanRemoval = true)
    var saisons: MutableList<Saison> = mutableListOf(),

    // Association Many-to-Many avec Genre (une série peut avoir plusieurs genres)
    @ManyToMany(mappedBy = "series")
    var genres: MutableList<Genre> = mutableListOf()
) {
    /**
     * Calcule le nombre total d'épisodes de la série en additionnant le nombre
     * d'épisodes de toutes les saisons de la série.
     *
     * @return Le nombre total d'épisodes de la série.
     */
    fun getNombreTotalEpisodes(): Int {
        var totalEpisodes = 0
        for (saison in saisons) {
            totalEpisodes += saison.nombreEpisode
        }
        return totalEpisodes
    }

}