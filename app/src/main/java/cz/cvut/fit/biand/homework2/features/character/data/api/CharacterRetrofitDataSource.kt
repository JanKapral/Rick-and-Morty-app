package cz.cvut.fit.biand.homework2.features.character.data.api

import android.util.Log
import cz.cvut.fit.biand.homework2.features.character.data.CharacterRemoteDataSource
import cz.cvut.fit.biand.homework2.features.character.domain.Character

class CharacterRetrofitDataSource(private val apiDescription: CharacterApiDescription) :
    CharacterRemoteDataSource {

    override suspend fun getCharacters(): List<Character> {
        return apiDescription.getCharacters().results.map {
            Log.d("CCC", it.toString())
            it.toCharacter()
        }
    }

    private fun ApiCharacter.toCharacter(): Character {
        return Character(
            id = id.toString(),
            name = name.orEmpty(),
            status = status.orEmpty(),
            species = species.orEmpty(),
            type = type.orEmpty(),
            gender = gender.orEmpty(),
            origin = origin?.name.orEmpty(),
            location = location?.name.orEmpty(),
            imageURL = image.orEmpty(),
        )
    }

    override suspend fun searchCharacters(name: String?): List<Character> {
        return try {
            apiDescription.searchCharacters(name = name.orEmpty()).results.map { it.toCharacter() }
        } catch (t: Throwable) {
            emptyList()
        }
    }

}