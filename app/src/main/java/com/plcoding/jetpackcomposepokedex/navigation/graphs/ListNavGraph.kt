package com.plcoding.jetpackcomposepokedex.navigation.graphs

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.plcoding.jetpackcomposepokedex.pokemonlist.PokemonListScreen

fun NavGraphBuilder.listNavGraph(navController: NavHostController) {
    navigation(
        route = Graph.LIST,
        startDestination = "list_screen"
    ) {
        composable(route = "list_screen") {
            PokemonListScreen(navController = navController)
        }
    }
}