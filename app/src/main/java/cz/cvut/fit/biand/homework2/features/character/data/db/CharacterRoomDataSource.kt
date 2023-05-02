package cz.cvut.fit.biand.homework2.features.character.data.db

import cz.cvut.fit.biand.homework2.features.character.data.CharacterLocalDataSource
import cz.cvut.fit.biand.homework2.features.character.domain.Character

class CharacterRoomDataSource(private val characterDao: CharacterDao): CharacterLocalDataSource {
    override suspend fun getCharacters(): List<Character> {
        return characterDao.getCharacters().map { it.toCharacter() }
    }

    private fun DbCharacter.toCharacter(): Character {
        return Character(
            id = id,
            name = name,
            status = status,
            species = species,
            type = type,
            gender = gender,
            origin = origin,
            location = location,
            imageURL = imageURL,
        )
    }

    override suspend fun getCharacter(id: String): Character? {
        return characterDao.getCharacter(id)?.toCharacter()
    }

    override suspend fun insert(characters: List<Character>) {
        characterDao.insert(characters.map { it.toDb() })
    }

    private fun Character.toDb() : DbCharacter {
        return DbCharacter(
            id = id,
            name = name,
            status = status,
            species = species,
            type = type,
            gender = gender,
            origin = origin,
            location = location,
            imageURL = imageURL,
        )
    }

    override suspend fun deleteAll() {
        characterDao.deleteAll()
    }

}