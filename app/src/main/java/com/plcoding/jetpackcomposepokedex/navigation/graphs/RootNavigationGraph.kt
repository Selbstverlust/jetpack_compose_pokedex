package com.plcoding.jetpackcomposepokedex.navigation.graphs

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.plcoding.jetpackcomposepokedex.pokemondetail.PokemonDetailScreen

@Composable
fun RootNavigationGraph(navController: NavHostController) {
    NavHost(navController = navController, route = Graph.ROOT, startDestination = Graph.LIST) {
        listNavGraph(navController = navController)
        composable(
            "detail_screen/{pokemonName}",
            arguments = listOf(
                navArgument("pokemonName") {
                    type = NavType.StringType
                }
            )
        ) {
            val pokemonName = remember {
                it.arguments?.getString("pokemonName")
            }
            PokemonDetailScreen(
                pokemonName = pokemonName?.toLowerCase() ?: ""
            )
        }
    }
}

object Graph {
    const val ROOT = "root_graph"
    const val LIST = "list_graph"
    const val DETAIL = "detail_graph"
}