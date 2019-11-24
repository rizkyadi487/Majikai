package com.rizkyadi.myanimelist.data.vo

import com.google.gson.annotations.SerializedName

data class AnimeResponse(
    @SerializedName("top")
    val animeList: List<Anime>
)