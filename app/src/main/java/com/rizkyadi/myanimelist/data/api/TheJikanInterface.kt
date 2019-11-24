package com.rizkyadi.myanimelist.data.api

import com.rizkyadi.myanimelist.data.vo.AnimeDetails
import com.rizkyadi.myanimelist.data.vo.AnimeResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface TheJikanInterface {
    //https://api.jikan.moe/v3/top/anime/1/airing -> anime airing anime
    //https://api.jikan.moe/v3/anime/20 -> Anime Details
    //https://api.jikan.moe/v3/ -> Base URL

    @GET("top/anime/{page}/airing")
    fun getPopularAnime(@Path("page")page: Int): Single<AnimeResponse>

    @GET("anime/{anime_id}")
    fun getAnimeDetails(@Path("anime_id")id: Int): Single<AnimeDetails>
}