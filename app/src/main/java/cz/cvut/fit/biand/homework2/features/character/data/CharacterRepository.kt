package cz.cvut.fit.biand.homework2.features.character.data

import android.util.Log
import cz.cvut.fit.biand.homework2.features.character.domain.Character
import cz.cvut.fit.biand.homework2.features.character.domain.CharactersResult

class CharacterRepository(
    private val characterRemoteDataSource: CharacterRemoteDataSource,
    private val characterLocalDataSource: CharacterLocalDataSource,
) {

    suspend fun getCharacters(): CharactersResult {
        return try {
            val characters = characterRemoteDataSource.getCharacters()
            Log.d("AAA", characters.toString())
            characterLocalDataSource.deleteAll()
            characterLocalDataSource.insert(characters)
            CharactersResult(characters, isSuccess = true)
        } catch (t: Throwable) {
            t.printStackTrace()
            CharactersResult(characterLocalDataSource.getCharacters(), isSuccess = true)
        }
    }

    suspend fun getCharacter(id: String): Character? {
        return characterLocalDataSource.getCharacter(id)
    }

    suspend fun searchCharacters(name: String): List<Character> {
        return characterRemoteDataSource.searchCharacters(name)
    }
}