package cz.cvut.fit.biand.homework2.features.character.data

import cz.cvut.fit.biand.homework2.features.character.domain.Character

interface CharacterLocalDataSource {
    suspend fun getCharacters(): List<Character>

    suspend fun getCharacter(id: String): Character?

    suspend fun insert(characters: List<Character>)

    suspend fun deleteAll()
}