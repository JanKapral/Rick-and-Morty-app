package cz.cvut.fit.biand.homework2.features.character.data.api

import kotlinx.serialization.Serializable

@Serializable
data class ApiCharacter(
    val id: Long,
    val name: String?,
    val status: String?,
    val species: String?,
    val type: String?,
    val gender: String?,
    val origin: CharacterOrigin?,
    val location: CharacterLocation?,
    val image: String?,
    val episodes: List<String>? = null,
    val url: String?,
    val created: String?,
)

@Serializable
data class CharacterOrigin(
    val name: String?,
    val url: String?,
)

@Serializable
data class CharacterLocation(
    val name: String?,
    val url: String?,
)