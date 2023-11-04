package com.ldv.apiseries.model.entity

import jakarta.persistence.*

/**
 * Entité représentant une saison d'une série.
 */
@Entity
class Saison(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    var id: Long? = null,
    var num: Int,
    var nom: String?,
    var annee: Int,
    var nombreEpisode: Int,

    // Relation Many-to-One avec Serie (chaque saison appartient à une série)
    @ManyToOne
    @JoinColumn(name = "serie_id")
    var serie: Serie? = null
){


}