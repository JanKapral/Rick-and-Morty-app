package cz.cvut.fit.biand.homework2.features.character.presentation.search

import androidx.compose.ui.focus.FocusState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import cz.cvut.fit.biand.homework2.features.character.data.CharacterRepository
import cz.cvut.fit.biand.homework2.features.character.domain.Character
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SearchViewModel(private val characterRepository: CharacterRepository) : ViewModel() {
    private val _screenStateStream = MutableStateFlow<SearchScreenState>(SearchScreenState.Loading)
    val screenStateStream get() = _screenStateStream.asStateFlow()

    init {
        viewModelScope.launch {
            val result = emptyList<Character>()
            _screenStateStream.value = SearchScreenState.State(result)
        }
    }

    fun onQueryChange(searchedText: String) {

        viewModelScope.launch {
            when (screenStateStream.value) {
                is SearchScreenState.State -> _screenStateStream.value = (screenStateStream.value as SearchScreenState.State).copy(searchedText = searchedText)
                else -> {}
            }
            val result = characterRepository.searchCharacters(searchedText)
            _screenStateStream.value = (screenStateStream.value as SearchScreenState.State).copy(charactersResult = result)
        }
    }

    fun onClearSearch() {
        viewModelScope.launch {
            val result = emptyList<Character>()
            _screenStateStream.value = SearchScreenState.State(result)
        }
    }

    fun onFocus(focusState: FocusState) {

    }
}

sealed interface SearchScreenState {

    object Loading : SearchScreenState

    data class State(
        val charactersResult: List<Character>,
        val searchedText: String = "",
        val showClear: Boolean = false
    ) : SearchScreenState
}
