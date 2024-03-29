package com.rizkyadi.myanimelist.data.vo


import com.google.gson.annotations.SerializedName

data class Anime(
    @SerializedName("end_date")
    val endDate: String,
    val episodes: Int,
    @SerializedName("image_url")
    val imageUrl: String,
    @SerializedName("mal_id")
    val id: Int,
    val rank: Int,
    val score: Double,
    @SerializedName("start_date")
    val startDate: String,
    val title: String,
    val type: String,
    val url: String
)