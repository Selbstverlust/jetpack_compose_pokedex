package com.plcoding.jetpackcomposepokedex.util

import androidx.compose.ui.graphics.Color
import com.plcoding.jetpackcomposepokedex.data.remote.responses.Pokemon
import com.plcoding.jetpackcomposepokedex.data.remote.responses.PokemonSpecies
import com.plcoding.jetpackcomposepokedex.ui.theme.*
import java.util.*

fun parseTypeToColor(type: Pokemon.Type): Color {
    return when(type.type.name.toLowerCase(Locale.ROOT)) {
        "normal" -> TypeNormal
        "fire" -> TypeFire
        "water" -> TypeWater
        "electric" -> TypeElectric
        "grass" -> TypeGrass
        "ice" -> TypeIce
        "fighting" -> TypeFighting
        "poison" -> TypePoison
        "ground" -> TypeGround
        "flying" -> TypeFlying
        "psychic" -> TypePsychic
        "bug" -> TypeBug
        "rock" -> TypeRock
        "ghost" -> TypeGhost
        "dragon" -> TypeDragon
        "dark" -> TypeDark
        "steel" -> TypeSteel
        "fairy" -> TypeFairy
        else -> Color.Black
    }
}

fun parseStatToColor(stat: Pokemon.Stat): Color {
    return when(stat.stat.name.toLowerCase()) {
        "hp" -> HPColor
        "attack" -> AtkColor
        "defense" -> DefColor
        "special-attack" -> SpAtkColor
        "special-defense" -> SpDefColor
        "speed" -> SpdColor
        else -> Color.White
    }
}

fun parseStatToAbbr(stat: Pokemon.Stat): String {
    return when(stat.stat.name.toLowerCase()) {
        "hp" -> "HP"
        "attack" -> "Atk"
        "defense" -> "Def"
        "special-attack" -> "SpAtk"
        "special-defense" -> "SpDef"
        "speed" -> "Spd"
        else -> ""
    }
}

fun parseEggName(egg: PokemonSpecies.EggGroup): String {
    return when(egg.name.toLowerCase(Locale.ROOT)) {
        "monster" -> "Monster"
        "water1" -> "Water 1"
        "bug" -> "Bug"
        "flying" -> "Flying"
        "ground" -> "Field"
        "fairy" -> "Fairy"
        "plant" -> "Grass"
        "humanshape" -> "Human-like"
        "water3" -> "Water 3"
        "mineral" -> "Mineral"
        "indeterminate" -> "Amorphous"
        "water2" -> "Water 2"
        "ditto" -> "Ditto"
        "dragon" -> "Dragon"
        else -> "None"
    }
}

fun parseEggColor(egg: PokemonSpecies.EggGroup): Color {
    return when(egg.name.toLowerCase(Locale.ROOT)) {
        "monster" -> EggMonster
        "water1" -> EggWater1
        "bug" -> EggBug
        "flying" -> EggFlying
        "ground" -> EggField
        "fairy" -> EggFairy
        "plant" -> EggGrass
        "humanshape" -> EggHumanLike
        "water3" -> EggWater3
        "mineral" -> EggMineral
        "indeterminate" -> EggAmorphous
        "water2" -> EggWater2
        "ditto" -> EggDitto
        "dragon" -> EggDragon
        else -> EggNone
    }
}