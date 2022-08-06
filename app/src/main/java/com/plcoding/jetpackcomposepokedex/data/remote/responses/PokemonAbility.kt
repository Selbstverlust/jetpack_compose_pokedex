package com.plcoding.jetpackcomposepokedex.data.remote.responses


import com.google.gson.annotations.SerializedName

data class PokemonAbility(
    @SerializedName("effect_changes")
    val effectChanges: List<EffectChange>,
    @SerializedName("effect_entries")
    val effectEntries: List<EffectEntry>,
    @SerializedName("flavor_text_entries")
    val flavorTextEntries: List<FlavorTextEntry>,
    val generation: Generation,
    val id: Int,
    @SerializedName("is_main_series")
    val isMainSeries: Boolean,
    val name: String,
    val names: List<Name>,
    val pokemon: List<Pokemon>
) {
    data class EffectChange(
        @SerializedName("effect_entries")
        val effectEntries: List<EffectEntry>,
        @SerializedName("version_group")
        val versionGroup: VersionGroup
    ) {
        data class EffectEntry(
            val effect: String,
            val language: Language
        ) {
            data class Language(
                val name: String,
                val url: String
            )
        }

        data class VersionGroup(
            val name: String,
            val url: String
        )
    }

    data class EffectEntry(
        val effect: String,
        val language: Language,
        @SerializedName("short_effect")
        val shortEffect: String
    ) {
        data class Language(
            val name: String,
            val url: String
        )
    }

    data class FlavorTextEntry(
        @SerializedName("flavor_text")
        val flavorText: String,
        val language: Language,
        @SerializedName("version_group")
        val versionGroup: VersionGroup
    ) {
        data class Language(
            val name: String,
            val url: String
        )

        data class VersionGroup(
            val name: String,
            val url: String
        )
    }

    data class Generation(
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

    data class Pokemon(
        @SerializedName("is_hidden")
        val isHidden: Boolean,
        val pokemon: Pokemon,
        val slot: Int
    ) {
        data class Pokemon(
            val name: String,
            val url: String
        )
    }
}