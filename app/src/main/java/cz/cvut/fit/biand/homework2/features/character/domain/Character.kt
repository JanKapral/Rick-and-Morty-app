package cz.cvut.fit.biand.homework2.features.character.domain

data class Character(
    val id: String,
    val name: String,
    val status: String,
    val species: String,
    val type: String,
    val gender: String,
    val origin: String,
    val location: String,
    val imageURL: String,
)
