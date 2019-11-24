package com.rizkyadi.myanimelist.ui.single_anime_details

import androidx.lifecycle.LiveData
import com.rizkyadi.myanimelist.data.api.TheJikanInterface
import com.rizkyadi.myanimelist.data.repository.AnimeDetailsNetworkDataSource
import com.rizkyadi.myanimelist.data.repository.NetworkState
import com.rizkyadi.myanimelist.data.vo.AnimeDetails
import io.reactivex.disposables.CompositeDisposable

class AnimeDetailsRepository (private val apiService: TheJikanInterface) {
    lateinit var animeDetailsNetworkDataSource: AnimeDetailsNetworkDataSource

    fun fetchSingleAnimeDetails(compositeDisposable: CompositeDisposable, animeId:Int) : LiveData<AnimeDetails>{
        animeDetailsNetworkDataSource = AnimeDetailsNetworkDataSource(apiService, compositeDisposable)
        animeDetailsNetworkDataSource.fetchAnimeDetails(animeId)

        return animeDetailsNetworkDataSource.downloadedAnimeResponse
    }

    fun getAnimeDetailsNetworkState(): LiveData<NetworkState>{
        return animeDetailsNetworkDataSource.networkState
    }
}