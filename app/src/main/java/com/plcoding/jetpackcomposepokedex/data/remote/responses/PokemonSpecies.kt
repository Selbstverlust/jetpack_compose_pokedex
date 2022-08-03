package com.plcoding.jetpackcomposepokedex.data.remote.responses


import com.google.gson.annotations.SerializedName

data class PokemonSpecies(
    @SerializedName("base_happiness")
    val baseHappiness: Int,
    @SerializedName("capture_rate")
    val captureRate: Int,
    val color: Color,
    @SerializedName("egg_groups")
    val eggGroups: List<EggGroup>,
    @SerializedName("evolution_chain")
    val evolutionChain: EvolutionChain,
    @SerializedName("evolves_from_species")
    val evolvesFromSpecies: EvolvesFromSpecies,
    @SerializedName("flavor_text_entries")
    val flavorTextEntries: List<FlavorTextEntry>,
    @SerializedName("form_descriptions")
    val formDescriptions: List<Any>,
    @SerializedName("forms_switchable")
    val formsSwitchable: Boolean,
    @SerializedName("gender_rate")
    val genderRate: Int,
    val genera: List<Genera>,
    val generation: Generation,
    @SerializedName("growth_rate")
    val growthRate: GrowthRate,
    val habitat: Habitat,
    @SerializedName("has_gender_differences")
    val hasGenderDifferences: Boolean,
    @SerializedName("hatch_counter")
    val hatchCounter: Int,
    val id: Int,
    @SerializedName("is_baby")
    val isBaby: Boolean,
    @SerializedName("is_legendary")
    val isLegendary: Boolean,
    @SerializedName("is_mythical")
    val isMythical: Boolean,
    val name: String,
    val names: List<Name>,
    val order: Int,
    @SerializedName("pal_park_encounters")
    val palParkEncounters: List<PalParkEncounter>,
    @SerializedName("pokedex_numbers")
    val pokedexNumbers: List<PokedexNumber>,
    val shape: Shape,
    val varieties: List<Variety>
) {
    data class Color(
        val name: String,
        val url: String
    )

    data class EggGroup(
        val name: String,
        val url: String
    )

    data class EvolutionChain(
        val url: String
    )

    data class EvolvesFromSpecies(
        val name: String,
        val url: String
    )

    data class FlavorTextEntry(
        @SerializedName("flavor_text")
        val flavorText: String,
        val language: Language,
        val version: Version
    ) {
        data class Language(
            val name: String,
            val url: String
        )

        data class Version(
            val name: String,
            val url: String
        )
    }

    data class Genera(
        val genus: String,
        val language: Language
    ) {
        data class Language(
            val name: String,
            val url: String
        )
    }

    data class Generation(
        val name: String,
        val url: String
    )

    data class GrowthRate(
        val name: String,
        val url: String
    )

    data class Habitat(
        val name: String,
        val url: String
    )

    data class Name(
        val language: Language,
        val name: String
    ) {
        data class Language(
            val name: String,
            val url: String
        )
    }

    data class PalParkEncounter(
        val area: Area,
        @SerializedName("base_score")
        val baseScore: Int,
        val rate: Int
    ) {
        data class Area(
            val name: String,
            val url: String
        )
    }

    data class PokedexNumber(
        @SerializedName("entry_number")
        val entryNumber: Int,
        val pokedex: Pokedex
    ) {
        data class Pokedex(
            val name: String,
            val url: String
        )
    }

    data class Shape(
        val name: String,
        val url: String
    )

    data class Variety(
        @SerializedName("is_default")
        val isDefault: Boolean,
        val pokemon: Pokemon
    ) {
        data class Pokemon(
            val name: String,
            val url: String
        )
    }
}