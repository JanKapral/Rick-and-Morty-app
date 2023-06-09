package cz.cvut.fit.biand.homework2.features.character.data.api

import retrofit2.http.GET
import retrofit2.http.Query

interface CharacterApiDescription {

    @GET("character")
    suspend fun getCharacters(): CharactersResponse

    @GET("character?")
    suspend fun searchCharacters(@Query("name") name: String) : CharactersResponse
}