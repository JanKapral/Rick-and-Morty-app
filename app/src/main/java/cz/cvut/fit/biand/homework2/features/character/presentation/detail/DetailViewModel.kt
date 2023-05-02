package cz.cvut.fit.biand.homework2.features.character.presentation.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cz.cvut.fit.biand.homework2.Screens
import cz.cvut.fit.biand.homework2.features.character.data.CharacterRepository
import cz.cvut.fit.biand.homework2.features.character.domain.Character
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class DetailViewModel(
    private val savedStateHandle: SavedStateHandle,
    private val characterRepository: CharacterRepository
) : ViewModel() {

    private val _screenStateStream = MutableStateFlow(CharacterDetailScreenState())
    val screenStateStream get() = _screenStateStream.asStateFlow()


    init {
        viewModelScope.launch {
            val characterId: String = savedStateHandle[Screens.CharacterDetail.ID] ?: throw NullPointerException("Character id is missing")
            val character = characterRepository.getCharacter(characterId)
            _screenStateStream.update { it.copy(character = character) }
        }
    }

    fun onFavoriteClick() {
        _screenStateStream.update { it.copy(favorite = !it.favorite) }
    }
}

data class CharacterDetailScreenState(val character: Character? = null, val favorite: Boolean = false)
