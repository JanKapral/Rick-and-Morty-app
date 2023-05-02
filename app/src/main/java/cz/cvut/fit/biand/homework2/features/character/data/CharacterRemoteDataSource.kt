package cz.cvut.fit.biand.homework2.features.character.data

import cz.cvut.fit.biand.homework2.features.character.domain.Character

interface CharacterRemoteDataSource {
    suspend fun getCharacters(): List<Character>
    suspend fun searchCharacters(name: String?) :List<Character>
}