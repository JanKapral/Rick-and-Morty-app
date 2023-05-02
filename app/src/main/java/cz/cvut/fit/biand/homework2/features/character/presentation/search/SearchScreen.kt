package cz.cvut.fit.biand.homework2.features.character.presentation.search

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cz.cvut.fit.biand.homework2.R
import cz.cvut.fit.biand.homework2.features.character.presentation.list.CharacterListItem
import org.koin.androidx.compose.koinViewModel


@Composable
fun SearchScreen(
    viewModel: SearchViewModel = koinViewModel(),
    navigateToList: () -> Unit,
    navigateToDetail: (id: String) -> Unit
) {
    val screenState by viewModel.screenStateStream.collectAsStateWithLifecycle()
    SearchScreen(
        screenState = screenState,
        navigateToList = navigateToList,
        navigateToDetail = navigateToDetail,
        viewModel
    )
}

@Composable
fun SearchScreen(
    screenState: SearchScreenState,
    navigateToList: () -> Unit,
    navigateToDetail: (id: String) -> Unit,
    viewModel: SearchViewModel
) {
    SearchScreenContent(
        onNavigateBack = navigateToList,
        onCharacterClicked = {
            navigateToDetail(it)
        },
        viewModel = viewModel,
        screenState
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreenContent(
    onNavigateBack: () -> Unit,
    onCharacterClicked: (String) -> Unit,
    viewModel: SearchViewModel,
    screenState: SearchScreenState
) {
    when (screenState) {
        is SearchScreenState.Loading -> LoadingState()
        is SearchScreenState.State ->

            Scaffold(
                topBar = {
                    TopAppBar(
                        navigationIcon = {
                            IconButton(onClick = onNavigateBack) {
                                Icon(
                                    Icons.Default.ArrowBack,
                                    contentDescription = "Arrow back",
                                )
                            }
                        },
                        title = {
                            SearchTopBarTitle(
                                screenState.searchedText,
                                viewModel::onQueryChange,
                                viewModel::onClearSearch,
                                viewModel::onFocus
                            )
                        },
                    )
                },
            ) {paddingValue ->
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValue)
                ) {
                    items(screenState.charactersResult, key = {it.id}) { character ->
                        CharacterListItem(
                            character = character,
                            onCharacterClicked = onCharacterClicked,
                        )
                    }
                }
            }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SearchTopBarTitle(
    text: String,
    onSearchTextChanged: (String) -> Unit,
    onClear: () -> Unit,
    onFocus: (focusState: FocusState) -> Unit,
) {
    TextField(
        modifier = Modifier
            .fillMaxWidth()
            .onFocusChanged { focusState ->
                onFocus(focusState)
            },
        value = text,
        onValueChange = onSearchTextChanged,
        placeholder = {
            Text(text = stringResource(R.string.search))
        },
        colors = TextFieldDefaults.textFieldColors(
            containerColor = Color.White,
            focusedIndicatorColor = Color.White,
            unfocusedIndicatorColor = Color.White,
            cursorColor = LocalContentColor.current.copy(alpha = LocalContentAlpha.current),
        ),
        trailingIcon = {
            IconButton(onClick = {
                onClear()
            }) {
                Icon(
                    imageVector = Icons.Filled.Close,
                    contentDescription = "Clear icon",
                )
            }
        },
    )
}

@Composable
private fun LoadingState() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator()
    }
}
