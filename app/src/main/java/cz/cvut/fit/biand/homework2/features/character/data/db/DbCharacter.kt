package cz.cvut.fit.biand.homework2.features.character.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "characters")
data class DbCharacter(
    @PrimaryKey val id: String,
    val name: String,
    val status: String,
    val species: String,
    val type: String,
    val gender: String,
    val origin: String,
    val location: String,
    val imageURL: String,
)