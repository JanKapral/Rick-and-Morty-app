package cz.cvut.fit.biand.homework2

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import cz.cvut.fit.biand.homework2.features.character.presentation.list.ListScreen
import cz.cvut.fit.biand.homework2.features.character.presentation.detail.DetailScreen
import cz.cvut.fit.biand.homework2.features.character.presentation.search.SearchScreen

sealed class Screens(val route: String) {

    sealed class TopLevel(route: String) : Screens(route) {

        @get:StringRes
        abstract val title: Int


        object Characters : TopLevel("characters") {
            override val title = R.string.characters
        }

        object Favourites : TopLevel("favourites") {
            override val title = R.string.favorites
        }

        object Search : TopLevel("search") {
            override val title = R.string.search
        }


    }

    class CharacterDetail(characterId: String) : Screens("characters/$characterId") {
        companion object {
            const val ID = "id"
        }
    }
}

@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screens.TopLevel.Characters.route) {
        composable(route = Screens.TopLevel.Characters.route) {
            ListScreen(
                navigateToCharacterDetail = {
                    navController.navigate(Screens.CharacterDetail(it).route)
                },
                navigateToSearch = {
                    navController.navigate(Screens.TopLevel.Search.route)
                }
            )
        }
        composable(
            route = Screens.CharacterDetail("{${Screens.CharacterDetail.ID}}").route,
            arguments = listOf(navArgument(Screens.CharacterDetail.ID) {
                type = NavType.StringType
            }),
        ) {
            DetailScreen(navigateUp = { navController.navigateUp() })
        }
        composable(route = Screens.TopLevel.Search.route) {
            SearchScreen(navigateToList = { navController.navigate(Screens.TopLevel.Characters.route) },
                    navigateToDetail = { navController.navigate(Screens.CharacterDetail(it).route) })
        }
    }
}
