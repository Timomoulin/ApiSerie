package com.ldv.apiseries.model.entity

import jakarta.persistence.*

/**
 * Entité représentant un genre de série.
 */
@Entity
class Genre constructor(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    var id: Long? = null,
    @Column(unique = true)
    @Basic(optional = false)
    var nom: String,

    // Association Many-to-Many avec Serie (un genre peut être associé à plusieurs séries)
    @ManyToMany
    @JoinTable(
        name = "Genre_series",
        joinColumns = [JoinColumn(name = "genre_id")],
        inverseJoinColumns = [JoinColumn(name = "series_id")]
    )
    var series: MutableList<Serie> = mutableListOf()
)  {



}