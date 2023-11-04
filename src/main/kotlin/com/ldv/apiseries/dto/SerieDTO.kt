package com.ldv.apiseries.dto

import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size

data class SerieDto(
    val id: Long?,

    @field:NotNull(message = "Le champ 'nom' ne doit pas être null.")
    @field:Size(min = 1, message = "Le nom de la série ne doit pas être vide.")
    val nom: String,

    val lienImage: String?,

    @field:NotNull(message = "Le champ 'vo' ne doit pas être null.")
    @field:Size(min = 1, message = "La version originale ne doit pas être vide.")
    val vo: String,

    val premise: String?,

    val genres: List<GenreDto>,

    val saisons: List<SaisonDto>
)