package com.ldv.apiseries.dto

import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotNull

data class SaisonDto(
    val id: Long?,

    @field:NotNull(message = "Le champ 'num' ne doit pas être null.")
    val num: Int,

    val nom: String?,

    @field:NotNull(message = "Le champ 'annee' ne doit pas être null.")
    @field:Min(value = 1900, message = "L'année doit être supérieure ou égale à 1900.")
    val annee: Int,

    @field:NotNull(message = "Le champ 'nombreEpisode' ne doit pas être null.")
    @field:Min(value = 1, message = "Le nombre d'épisodes doit être d'au moins 1.")
    @field:Max(value = 100, message = "Le nombre d'épisodes ne doit pas dépasser 100.")
    val nombreEpisode: Int
)