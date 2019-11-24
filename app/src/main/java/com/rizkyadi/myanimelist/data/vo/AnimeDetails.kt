package com.rizkyadi.myanimelist.data.vo


import com.google.gson.annotations.SerializedName

data class AnimeDetails(
    val airing: Boolean,
    val background: String,
    @SerializedName("ending_themes")
    val endingThemes: List<String>,
    val episodes: Int,
    @SerializedName("image_url")
    val imageUrl: String,
    @SerializedName("mal_id")
    val animeId: Int,
    @SerializedName("opening_themes")
    val openingThemes: List<String>,
    val popularity: Int,
    val premiered: String,
    val rank: Int,
    val rating: String,
    val score: Double,
    val source: String,
    val status: String,
    val synopsis: String,
    val title: String,
    @SerializedName("title_english")
    val titleEnglish: String,
    @SerializedName("title_japanese")
    val titleJapanese: String,
    @SerializedName("title_synonyms")
    val titleSynonyms: List<String>,
    @SerializedName("trailer_url")
    val trailerUrl: String,
    val type: String,
    val url: String
)