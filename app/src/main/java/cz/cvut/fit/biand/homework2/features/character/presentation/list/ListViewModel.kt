package cz.cvut.fit.biand.homework2.features.character.presentation.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cz.cvut.fit.biand.homework2.features.character.data.CharacterRepository
import cz.cvut.fit.biand.homework2.features.character.domain.CharactersResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ListViewModel(private val characterRepository: CharacterRepository) : ViewModel() {
    private val _screenStateStream = MutableStateFlow<CharactersScreenState>(CharactersScreenState.Loading)
    val screenStateStream get() = _screenStateStream.asStateFlow()

    init {
        viewModelScope.launch {
            val result = characterRepository.getCharacters()
            _screenStateStream.value = CharactersScreenState.Loaded(result)
        }
    }
}

sealed interface CharactersScreenState {

    object Loading : CharactersScreenState

    data class Loaded(val charactersResult: CharactersResult) : CharactersScreenState
}
