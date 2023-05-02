package cz.cvut.fit.biand.homework2.features.character.di

import cz.cvut.fit.biand.homework2.core.data.db.CharacterDatabase
import cz.cvut.fit.biand.homework2.features.character.data.CharacterLocalDataSource
import cz.cvut.fit.biand.homework2.features.character.data.CharacterRemoteDataSource
import cz.cvut.fit.biand.homework2.features.character.data.api.CharacterApiDescription
import cz.cvut.fit.biand.homework2.features.character.data.api.CharacterRetrofitDataSource
import cz.cvut.fit.biand.homework2.features.character.data.db.CharacterRoomDataSource
import cz.cvut.fit.biand.homework2.features.character.presentation.list.ListViewModel
import cz.cvut.fit.biand.homework2.features.character.presentation.detail.DetailViewModel
import cz.cvut.fit.biand.homework2.features.character.presentation.search.SearchViewModel
import cz.cvut.fit.biand.homework2.features.character.data.CharacterRepository
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module
import retrofit2.Retrofit

val characterModule get() = module {
    single { get<Retrofit>().create(CharacterApiDescription::class.java) }
    factory<CharacterRemoteDataSource> { CharacterRetrofitDataSource(apiDescription = get()) }

    single { get<CharacterDatabase>().characterDao() }
    factory<CharacterLocalDataSource> { CharacterRoomDataSource(characterDao = get()) }

    factoryOf(::CharacterRepository)

    viewModelOf(::DetailViewModel)
    viewModelOf(::ListViewModel)
    viewModelOf(::SearchViewModel)
}