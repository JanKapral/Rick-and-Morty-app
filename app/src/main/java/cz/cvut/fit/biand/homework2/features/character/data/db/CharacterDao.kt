package cz.cvut.fit.biand.homework2.features.character.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface CharacterDao {

    @Query("SELECT * FROM characters")
    suspend fun getCharacters(): List<DbCharacter>

    @Query("SELECT * FROM characters WHERE id = :id")
    suspend fun getCharacter(id: String): DbCharacter?

    @Insert
    suspend fun insert(characters: List<DbCharacter>)

    @Query("DELETE FROM characters")
    suspend fun deleteAll()
}