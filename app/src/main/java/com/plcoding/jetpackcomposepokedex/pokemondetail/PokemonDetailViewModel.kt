package com.plcoding.jetpackcomposepokedex.pokemondetail

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.compose.rememberNavController
import com.plcoding.jetpackcomposepokedex.data.remote.responses.Pokemon
import com.plcoding.jetpackcomposepokedex.data.remote.responses.PokemonAbility
import com.plcoding.jetpackcomposepokedex.data.remote.responses.PokemonSpecies
import com.plcoding.jetpackcomposepokedex.repository.PokemonRepository
import com.plcoding.jetpackcomposepokedex.util.DetailSection
import com.plcoding.jetpackcomposepokedex.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokemonDetailViewModel @Inject constructor(
    private val repository: PokemonRepository
) : ViewModel() {

    var detailSection = mutableStateOf(DetailSection.Stats)
    var abilityName = mutableStateOf("")

    suspend fun getPokemonInfo(pokemonName: String): Resource<Pokemon> {
        return repository.getPokemonInfo(pokemonName)
    }
    suspend fun getPokemonSpeciesInfo(pokemonName: String): Resource<PokemonSpecies> {
        return repository.getPokemonSpeciesInfo(pokemonName)
    }
    suspend fun getPokemonAbilityInfo(abilityName: String): Resource<PokemonAbility> {
        return repository.getPokemonAbilityInfo(abilityName)
    }
}