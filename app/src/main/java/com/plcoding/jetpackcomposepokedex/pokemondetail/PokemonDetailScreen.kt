package com.plcoding.jetpackcomposepokedex.pokemondetail

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.plcoding.jetpackcomposepokedex.R
import com.plcoding.jetpackcomposepokedex.data.remote.responses.Pokemon
import com.plcoding.jetpackcomposepokedex.data.remote.responses.PokemonAbility
import com.plcoding.jetpackcomposepokedex.data.remote.responses.PokemonSpecies
import com.plcoding.jetpackcomposepokedex.ui.theme.TypeNormal
import com.plcoding.jetpackcomposepokedex.ui.theme.pressStart2P
import com.plcoding.jetpackcomposepokedex.util.*
import java.util.*

@Composable
fun PokemonDetailScreen(
    pokemonName: String,
    topPadding: Dp = 20.dp,
    pokemonImageSize: Dp = 200.dp,
    navController: NavHostController = rememberNavController(),
    viewModel: PokemonDetailViewModel = hiltViewModel()
) {

    val pokemonInfo = produceState<Resource<Pokemon>>(initialValue = Resource.Loading()) {
        value = viewModel.getPokemonInfo(pokemonName)
    }.value

    val pokemonSpeciesInfo =
        produceState<Resource<PokemonSpecies>>(initialValue = Resource.Loading()) {
            value = viewModel.getPokemonSpeciesInfo(pokemonName)
        }.value

    val backgroundColor = if (pokemonInfo is Resource.Success) {
        parseTypeToColor(pokemonInfo.data!!.types[0])
    } else {
        MaterialTheme.colors.surface
    }
    Scaffold(
        bottomBar = {
            BottomNavigation(backgroundColor = backgroundColor) {
                val iconSize = 25.dp
                BottomNavigationItem(
                    icon = { Icon(painterResource(id = R.drawable.ic_stats), contentDescription = null, modifier = Modifier.size(iconSize))},
                    label = { Text("Stats") },
                    selected = viewModel.detailSection.value == DetailSection.Stats,
                    onClick = {
                        viewModel.detailSection.value = DetailSection.Stats
                    }
                )
                BottomNavigationItem(
                    icon = { Icon(painterResource(id = R.drawable.ic_abilities), contentDescription = null, modifier = Modifier.size(iconSize)) },
                    label = { Text("Abilities") },
                    selected = viewModel.detailSection.value == DetailSection.Abilities,
                    onClick = {
                        viewModel.detailSection.value = DetailSection.Abilities
                    }
                )
                BottomNavigationItem(
                    icon = { Icon(painterResource(id = R.drawable.ic_moves), contentDescription = null, modifier = Modifier.size(iconSize)) },
                    label = { Text("Moves") },
                    selected = viewModel.detailSection.value == DetailSection.Moves,
                    onClick = {
                        viewModel.detailSection.value = DetailSection.Moves
                    }
                )

            }
        }
    ) {
        Box(modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
            .padding(bottom = 16.dp)
        ) {
            PokemonDetailTopSection(
                navController = navController,
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.2f)
                    .align(Alignment.TopCenter)
            )
            PokemonDetailStateWrapper(
                pokemonInfo = pokemonInfo,
                pokemonSpecies = pokemonSpeciesInfo,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(
                        top = topPadding + pokemonImageSize / 2f,
                        start = 16.dp,
                        end = 16.dp,
                        bottom = 50.dp
                    )
                    .shadow(10.dp, RoundedCornerShape(10.dp))
                    .clip(RoundedCornerShape(10.dp))
                    .background(MaterialTheme.colors.surface)
                    .padding(16.dp)
                    .align(Alignment.BottomCenter),
                loadingModifier = Modifier
                    .size(100.dp)
                    .align(Alignment.Center)
                    .padding(
                        top = topPadding + pokemonImageSize / 2f,
                        start = 16.dp,
                        end = 16.dp,
                        bottom = 16.dp
                    )
            )
            Box(
                contentAlignment = Alignment.TopCenter,
                modifier = Modifier
                    .fillMaxSize()
            ) {
                SubcomposeAsyncImage(model = ImageRequest.Builder(LocalContext.current)
                    .data(pokemonInfo.data?.sprites?.other?.officialArtwork?.frontDefault)
                    .crossfade(true)
                    .build(),
                    contentDescription = null,
                    modifier = Modifier
                        .size(pokemonImageSize)
                        .offset(y = topPadding)
                )
            }
        }
    }
}

@Composable
fun PokemonDetailTopSection(
    navController: NavController,
    modifier: Modifier = Modifier,
) {
    Box(
        contentAlignment = Alignment.TopStart,
        modifier = modifier
            .background(
                Brush.verticalGradient(
                    listOf(
                        Color.Black,
                        Color.Transparent
                    )
                )
            )
    ) {
        Icon(
            imageVector = Icons.Default.ArrowBack,
            contentDescription = null,
            tint = Color.White,
            modifier = Modifier
                .size(36.dp)
                .offset(16.dp, 16.dp)
                .clickable {
                    navController.navigate("pokemon_list_screen")
                }
        )
    }
}

@Composable
fun PokemonDetailStateWrapper(
    pokemonInfo: Resource<Pokemon>,
    modifier: Modifier = Modifier,
    pokemonSpecies: Resource<PokemonSpecies>?,
    loadingModifier: Modifier = Modifier,
) {
    if (pokemonInfo is Resource.Success && pokemonSpecies is Resource.Success) {
        PokemonDetailSection(
            pokemonInfo = pokemonInfo.data!!,
            pokemonSpecies = pokemonSpecies.data!!,
            modifier = modifier
                .offset(y = (-20).dp)
        )
    }
    if (pokemonInfo is Resource.Success && pokemonSpecies is Resource.Error) {
        PokemonDetailSection(
            pokemonInfo = pokemonInfo.data!!,
            modifier = modifier
                .offset(y = (-20).dp)
        )
    }
    if (pokemonInfo is Resource.Error && pokemonSpecies is Resource.Error) {
        Text(
            text = pokemonInfo.message!!,
            color = Color.Red,
            modifier = modifier
        )
    }
    if (pokemonInfo is Resource.Loading || pokemonSpecies is Resource.Loading) {
        CircularProgressIndicator(
            color = MaterialTheme.colors.primary,
            modifier = loadingModifier
        )
    }
}

@Composable
fun PokemonDetailSection(
    pokemonInfo: Pokemon,
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    viewModel: PokemonDetailViewModel = hiltViewModel(),
    pokemonSpecies: PokemonSpecies? = null,
) {
    val scrollState = rememberScrollState()

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxSize()
            .offset(y = 100.dp)
            .verticalScroll(scrollState)
    ) {
        Text(
            text = "#${pokemonInfo.id} ${pokemonInfo.name.capitalize(Locale.getDefault())}",
            fontFamily = pressStart2P,
            fontSize = 20.sp,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colors.onSurface
        )
        PokemonTypeSection(types = pokemonInfo.types)
        PokemonDetailDataSection(pokemonWeight = pokemonInfo.weight,
            pokemonHeight = pokemonInfo.height,
            pokemonSpecies = pokemonSpecies)

        when (viewModel.detailSection.value) {
            DetailSection.Stats -> PokemonBaseStats(pokemonInfo = pokemonInfo)
            DetailSection.Abilities -> PokemonAbilitiesList(pokemonInfo = pokemonInfo)
            DetailSection.AbilityDetail -> PokemonAbilityDetail()
            DetailSection.Moves -> PokemonBaseStats(pokemonInfo = pokemonInfo)
            DetailSection.MoveDetail -> PokemonBaseStats(pokemonInfo = pokemonInfo)
        }
    }
}

@Composable
fun PokemonTypeSection(types: List<Pokemon.Type>) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(16.dp)
    ) {
        for (type in types) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 8.dp)
                    .clip(CircleShape)
                    .background(parseTypeToColor(type))
                    .height(35.dp)
            ) {
                Text(
                    text = type.type.name.capitalize(Locale.ROOT),
                    color = Color.White,
                    fontSize = 18.sp
                )
            }
        }
    }
}

@Composable
fun PokemonDetailDataSection(
    pokemonWeight: Int,
    pokemonHeight: Int,
    pokemonSpecies: PokemonSpecies? = null,
    sectionHeight: Dp = 80.dp,
) {
    val pokemonWeightInKg = remember {
        pokemonWeight / 10f
    }
    val pokemonHeightInMeters = remember {
        pokemonHeight / 10f
    }
    val eggs = pokemonSpecies?.eggGroups

    Row(modifier = Modifier.fillMaxWidth()) {
        PokemonDetailDataItem(
            dataValue = pokemonWeightInKg,
            dataUnit = "kg",
            dataIcon = painterResource(id = R.drawable.ic_weight),
            modifier = Modifier.weight(1f)
        )
        Spacer(
            modifier = Modifier
                .size(width = 1.dp, sectionHeight)
                .background(Color.LightGray)
        )
        PokemonDetailDataItem(
            dataValue = pokemonHeightInMeters,
            dataUnit = "m",
            dataIcon = painterResource(id = R.drawable.ic_height),
            modifier = Modifier.weight(1f)
        )
        if (eggs != null) {
            for (egg in eggs) {
                Spacer(
                    modifier = Modifier
                        .size(width = 1.dp, sectionHeight)
                        .background(Color.LightGray)
                )
                PokemonDetailEggDataItem(
                    name = parseEggName(egg),
                    tint = parseEggColor(egg),
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

@Composable
fun PokemonDetailDataItem(
    dataValue: Float,
    dataUnit: String,
    dataIcon: Painter,
    modifier: Modifier = Modifier,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier
    ) {
        Icon(
            painter = dataIcon,
            contentDescription = null,
            tint = MaterialTheme.colors.onSurface
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "$dataValue$dataUnit",
            color = MaterialTheme.colors.onSurface
        )
    }
}

@Composable
fun PokemonDetailEggDataItem(
    name: String,
    tint: Color,
    modifier: Modifier = Modifier,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_egg),
            contentDescription = null,
            tint = tint,
            modifier = Modifier.size(width = 36.dp, height = 36.dp)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = name,
            color = MaterialTheme.colors.onSurface
        )
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
    pokemonInfo: Pokemon
) {
    val abilities = pokemonInfo.abilities

    for (item in abilities) {
        PokemonAbilityBox(name = item.ability.name.capitalize())
    }
}

@Composable
fun PokemonAbilityBox(
    name: String,
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
                viewModel.abilityName.value = name
                viewModel.detailSection.value = DetailSection.AbilityDetail
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
    abilityName: String? = "stench",
    viewModel: PokemonDetailViewModel = hiltViewModel()
) {
    if (abilityName != null) {
        val abilityInfo =
            produceState<Resource<PokemonAbility>>(initialValue = Resource.Loading()) {
                value = viewModel.getPokemonAbilityInfo(abilityName.toLowerCase())
            }.value

        if (abilityInfo is Resource.Success){
            Row {
                PokemonAbilityBox(name = abilityInfo.data!!.name)
                Text(text = abilityInfo.data.effectEntries[1].effect)
            }
        }
    }
}