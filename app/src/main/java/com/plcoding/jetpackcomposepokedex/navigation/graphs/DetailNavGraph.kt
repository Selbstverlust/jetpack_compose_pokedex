package com.plcoding.jetpackcomposepokedex.navigation.graphs

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.plcoding.jetpackcomposepokedex.data.remote.responses.Pokemon
import com.plcoding.jetpackcomposepokedex.data.remote.responses.PokemonAbility
import com.plcoding.jetpackcomposepokedex.pokemondetail.PokemonDetailViewModel
import com.plcoding.jetpackcomposepokedex.ui.theme.TypeNormal
import com.plcoding.jetpackcomposepokedex.util.Resource
import com.plcoding.jetpackcomposepokedex.util.parseStatToAbbr
import com.plcoding.jetpackcomposepokedex.util.parseStatToColor

@Composable
fun DetailNavGraph(
    navController: NavHostController,
    pokemonInfo: Pokemon
) {
    NavHost(
        navController = navController,
        route = Graph.DETAIL,
        startDestination = "detail_section_stats"
    ) {
        composable(route = "detail_section_stats") {
            PokemonBaseStats(pokemonInfo = pokemonInfo)
        }
        composable(route = "detail_section_ability_list") {
            PokemonAbilitiesList(pokemonInfo = pokemonInfo, navController = navController)
        }
        composable(route = "detail_section_ability_info/{name}", arguments = listOf(
            navArgument("name") {
                type = NavType.StringType
            })
        ) {
            val name = remember {
                it.arguments?.getString("name")
            }
            PokemonAbilityDetail(abilityName = name, navController = navController)
        }
    }
}

@Composable
fun PokemonStat(
    statName: String,
    statValue: Int,
    statMaxValue: Int,
    statColor: Color,
    height: Dp = 28.dp,
    animDuration: Int = 1000,
    animDelay: Int = 0,
) {
    var animationPlayed by remember {
        mutableStateOf(false)
    }
    val curPercent = animateFloatAsState(
        targetValue = if (animationPlayed) {
            statValue / statMaxValue.toFloat()
        } else 0f,
        animationSpec = tween(
            animDuration,
            animDelay
        )
    )
    LaunchedEffect(key1 = true) {
        animationPlayed = true
    }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(height)
            .clip(CircleShape)
            .background(
                if (isSystemInDarkTheme()) {
                    Color(0xFF505050)
                } else {
                    Color.LightGray
                }
            )
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth(curPercent.value)
                .clip(CircleShape)
                .background(statColor)
                .padding(horizontal = 8.dp)
        ) {
            Text(
                text = statName,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = (curPercent.value * statMaxValue).toInt().toString(),
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun PokemonBaseStats(
    pokemonInfo: Pokemon,
    animDelayPerItem: Int = 100,
) {
    val maxBaseStat = remember {
        pokemonInfo.stats.maxOf { it.baseStat }
    }
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = "Base stats:",
            fontSize = 20.sp,
            color = MaterialTheme.colors.onSurface
        )
        Spacer(modifier = Modifier.height(4.dp))

        for (i in pokemonInfo.stats.indices) {
            val stat = pokemonInfo.stats[i]
            PokemonStat(
                statName = parseStatToAbbr(stat),
                statValue = stat.baseStat,
                statMaxValue = maxBaseStat,
                statColor = parseStatToColor(stat),
                animDelay = i * animDelayPerItem
            )
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
fun PokemonAbilitiesList(
    pokemonInfo: Pokemon,
    navController: NavHostController
) {
    val abilities = pokemonInfo.abilities

    for (item in abilities) {
        PokemonAbilityBox(name = item.ability.name.capitalize(), navController = navController)
    }
}

@Composable
fun PokemonAbilityBox(
    name: String,
    navController: NavHostController,
    viewModel: PokemonDetailViewModel = hiltViewModel(),
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .padding(8.dp)
            .clip(CircleShape)
            .background(TypeNormal)
            .height(35.dp)
            .width(150.dp)
            .clickable {

            }
    ) {
        Text(
            text = name,
            color = Color.White,
            fontSize = 18.sp,
            modifier = Modifier.padding(4.dp)
        )
    }
}

@Composable
fun PokemonAbilityDetail(
    abilityName: String?,
    navController: NavHostController,
    viewModel: PokemonDetailViewModel = hiltViewModel()
) {
    if (abilityName != null) {
        val abilityInfo =
            produceState<Resource<PokemonAbility>>(initialValue = Resource.Loading()) {
                value = viewModel.getPokemonAbilityInfo(abilityName)
            }.value

        if (abilityInfo is Resource.Success){
            Row {
                PokemonAbilityBox(name = abilityInfo.data!!.name, navController = navController)
//                Text(text = abilityInfo.data.effectEntries[0].effect[0].toString())
            }
        }
    }
}