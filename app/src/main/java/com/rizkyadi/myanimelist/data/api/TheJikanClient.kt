package com.rizkyadi.myanimelist.data.api

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

const val BASE_URL = "https://api.jikan.moe/v3/"

const val FIRST_PAGE = 1
const val POST_PER_PAGE = 50

//https://api.jikan.moe/v3/top/anime/1/airing -> anime airing anime
//https://api.jikan.moe/v3/anime/20 -> Anime Details
//https://api.jikan.moe/v3/ -> Base URL

object TheJikanClient {
    fun getClient(): TheJikanInterface{
        val requestInterceptor = Interceptor{chain ->
            val url = chain.request()
                .url()
                .newBuilder()
                .build()

            val request = chain.request()
                .newBuilder()
                .url(url)
                .build()

            return@Interceptor chain.proceed(request)
        }

        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(requestInterceptor)
            .connectTimeout(60, TimeUnit.SECONDS)
            .build()

        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(BASE_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(TheJikanInterface::class.java)
    }
}