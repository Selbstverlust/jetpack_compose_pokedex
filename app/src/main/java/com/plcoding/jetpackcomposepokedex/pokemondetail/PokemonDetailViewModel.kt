package com.plcoding.jetpackcomposepokedex.pokemondetail

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import com.plcoding.jetpackcomposepokedex.data.remote.responses.Pokemon
import com.plcoding.jetpackcomposepokedex.data.remote.responses.PokemonSpecies
import com.plcoding.jetpackcomposepokedex.repository.PokemonRepository
import com.plcoding.jetpackcomposepokedex.ui.theme.*
import com.plcoding.jetpackcomposepokedex.util.DetailSection
import com.plcoding.jetpackcomposepokedex.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.*
import javax.inject.Inject

@HiltViewModel
class PokemonDetailViewModel @Inject constructor(
    private val repository: PokemonRepository
) : ViewModel() {

    val bottomSectionState = mutableStateOf(DetailSection.Stats)

    suspend fun getPokemonInfo(pokemonName: String): Resource<Pokemon> {
        return repository.getPokemonInfo(pokemonName)
    }
    suspend fun getPokemonSpeciesInfo(pokemonName: String): Resource<PokemonSpecies> {
        return repository.getPokemonSpeciesInfo(pokemonName)
    }
}