package cz.cvut.fit.biand.homework2.features.character.presentation.list

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cz.cvut.fit.biand.homework2.R
import cz.cvut.fit.biand.homework2.features.character.domain.CharactersResult
import org.koin.androidx.compose.koinViewModel
import cz.cvut.fit.biand.homework2.features.character.domain.Character
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import coil.compose.AsyncImage

@Composable
fun ListScreen(
    viewModel: ListViewModel = koinViewModel(),
    navigateToCharacterDetail: (id: String) -> Unit,
    navigateToSearch: () -> Unit
) {
    val screenState by viewModel.screenStateStream.collectAsStateWithLifecycle()
    CharactersScreen(screenState, navigateToCharacterDetail, navigateToSearch)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CharactersScreen(
    screenState: CharactersScreenState,
    navigateToCharacterDetail: (id: String) -> Unit,
    navigateToSearch: () -> Unit
) {
    Scaffold(topBar = { TopBar(navigateToSearch) }, bottomBar = { BottomBar() }) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
        ) {
            when (screenState) {
                is CharactersScreenState.Loading -> LoadingState()
                is CharactersScreenState.Loaded -> LoadedState(
                    charactersResult = screenState.charactersResult,
                    onCharacterClick = { navigateToCharacterDetail(it.id) },
                )
            }
        }
    }
}

@Composable
private fun LoadingState() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator()
    }
}

@Composable
private fun LoadedState(charactersResult: CharactersResult, onCharacterClick: (Character) -> Unit) {
    Column(Modifier.fillMaxSize()) {
        OutdatedDataBanner(show = !charactersResult.isSuccess)
        Characters(characters = charactersResult.characters, onCharacterClick = onCharacterClick)
    }
}

@Composable
private fun OutdatedDataBanner(show: Boolean) {
    if (show) {
        Text(
            text = stringResource(R.string.outdated_data),
            modifier = Modifier
                .background(color = MaterialTheme.colorScheme.errorContainer)
                .fillMaxWidth()
                .padding(16.dp)
        )
    }
}

@Composable
private fun Characters(characters: List<Character>, onCharacterClick: (Character) -> Unit) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp),
    ) {
        items(characters, key = { it.id }) { character ->
            CharacterListItem(
                character = character,
                onCharacterClicked = { onCharacterClick(character) },
            )
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(navigateToSearch: () -> Unit) {
    val focusRequester = remember { FocusRequester() }

    return (TopAppBar(
        title = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .focusRequester(focusRequester),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(text = stringResource(id = R.string.characters))
                Icon(
                    modifier = Modifier
                        .padding(start = 8.dp, end = 24.dp)
                        .clickable {
                            navigateToSearch()
                        },
                    painter = painterResource(id = R.drawable.ic_search),
                    contentDescription = "Search",
                )
            }
        },
    ))
}


@Composable
fun BottomBar() {
    BottomNavigation() {
        BottomNavigationItem(
            label = {
                Text(
                    text = stringResource(id = R.string.characters),
                    color = Color.Blue,
                )
            },
            onClick = {},
            selected = true,
            icon = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_characters),
                    tint = Color.Blue,
                    contentDescription = "Favourite navigation icon",
                )
            },
        )
        BottomNavigationItem(
            label = {
                Text(
                    text = stringResource(id = R.string.favorites),
                )
            },
            selected = false,
            onClick = {},
            icon = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_favorites_filled),
                    contentDescription = "Favourite navigation icon",
                )
            },
        )
    }
}

@Composable
fun CharacterListItem(
    character: Character,
    onCharacterClicked: (String) -> Unit,
) {
    Card(
        modifier = Modifier
            .padding(start = 8.dp, end = 8.dp, top = 8.dp)
            .clickable {
                onCharacterClicked(character.id)
            },
        elevation = CardDefaults.cardElevation(12.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(Color.White)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(all = 8.dp),
        ) {
            AsyncImage(
                model = character.imageURL, contentDescription = "Character avatar",
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .size(64.dp),
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(all = 8.dp),
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = character.name,
                        style = MaterialTheme.typography.titleLarge,
                    )
                }
                Text(text = character.status)
            }
        }
    }
}
