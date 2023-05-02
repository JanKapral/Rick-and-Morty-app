package cz.cvut.fit.biand.homework2.features.character.presentation.detail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import cz.cvut.fit.biand.homework2.R
import cz.cvut.fit.biand.homework2.core.presentation.theme.Blue
import cz.cvut.fit.biand.homework2.core.presentation.theme.Gray
import org.koin.androidx.compose.koinViewModel
import cz.cvut.fit.biand.homework2.features.character.domain.Character

@Composable
fun DetailScreen(
    viewModel: DetailViewModel = koinViewModel(),
    navigateUp: () -> Unit,
) {
    val screenState by viewModel.screenStateStream.collectAsStateWithLifecycle()
    DetailScreen(screenState = screenState, navigateUp, viewModel::onFavoriteClick)
}

@Composable
private fun DetailScreen(
    screenState: CharacterDetailScreenState,
    navigateUp: () -> Unit,
    onFavorite: () -> Unit
) {
    DetailScreenContent(
        character = screenState.character,
        favorite = screenState.favorite,
        onFavorite = onFavorite,
        onNavigateBack = navigateUp
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreenContent(
    character: Character?,
    favorite: Boolean,
    onFavorite: () -> Unit,
    onNavigateBack: () -> Unit,
) {
    character?.let {
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
                        Text(text = character.name)
                    },
                    actions = {
                        IconButton(onClick = onFavorite) {
                            Icon(
                                painter = if (favorite) {
                                    painterResource(id = R.drawable.ic_favorites_filled)
                                } else {
                                    painterResource(
                                        id = R.drawable.ic_favorites,
                                    )
                                },
                                tint = Blue,
                                contentDescription = "Favourite navigation icon",
                            )
                        }
                    },
                )
            },
        ) {
            Column(modifier = Modifier.padding(it)) {
                CharacterDetail(
                    character,
                )
            }
        }
    }
}

@Composable
fun CharacterDetail(character: Character) {
    Column(
        modifier = Modifier.fillMaxWidth(),
    ) {
        Row(modifier = Modifier.padding(all = 16.dp)) {
            AsyncImage(
                model = character.imageURL,
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .size(150.dp),
                contentDescription = "Image",
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        start = 8.dp,
                        bottom = 8.dp,
                    ),
            ) {
                Text(
                    text = stringResource(R.string.name),
                    style = MaterialTheme.typography.bodyLarge,
                )
                Text(
                    modifier = Modifier.padding(top = 16.dp),
                    text = character.name,
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                )
            }
        }
        Divider(
            color = Gray,
            modifier = Modifier
                .padding(top = 16.dp)
                .fillMaxWidth()
                .width(1.dp),
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp),
        ) {
            Information(title = stringResource(R.string.status), value = character.status)
            Information(title = stringResource(R.string.species), value = character.species)
            Information(title = stringResource(R.string.type), value = character.type)
            Information(title = stringResource(R.string.gender), value = character.gender)
            Information(title = stringResource(R.string.origin), value = character.origin)
            Information(title = stringResource(R.string.location), value = character.location)
        }
    }
}

@Composable
fun Information(title: String, value: String) {
    Column(
        modifier = Modifier.padding(top = 16.dp),
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.bodyLarge,
        )
        Text(
            modifier = Modifier.fillMaxWidth(0.5F),
            text = value,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.ExtraBold,
        )
    }
}
