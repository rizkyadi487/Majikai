package com.rizkyadi.myanimelist.data.repository

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.rizkyadi.myanimelist.data.api.TheJikanInterface
import com.rizkyadi.myanimelist.data.vo.Anime
import io.reactivex.disposables.CompositeDisposable

class AnimeDataSourceFactory (private val apiService:TheJikanInterface, private val compositeDisposable: CompositeDisposable): DataSource.Factory<Int, Anime>() {

    val animesLiveDataSource = MutableLiveData<AnimeDataSource>()
    override fun create(): DataSource<Int, Anime> {
        val animeDataSource = AnimeDataSource(apiService,compositeDisposable)

        animesLiveDataSource.postValue(animeDataSource)
        return animeDataSource
    }

}