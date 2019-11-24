package com.rizkyadi.myanimelist.ui.single_anime_details

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.rizkyadi.myanimelist.data.repository.NetworkState
import com.rizkyadi.myanimelist.data.vo.AnimeDetails
import io.reactivex.disposables.CompositeDisposable

class SingleAnimeViewModel (private val animeRepository : AnimeDetailsRepository, animeId : Int ): ViewModel() {
    private val compositeDisposable = CompositeDisposable()

    val animeDetails:LiveData<AnimeDetails> by lazy{
        animeRepository.fetchSingleAnimeDetails(compositeDisposable,animeId)
    }

    val networkState:LiveData<NetworkState> by lazy{
        animeRepository.getAnimeDetailsNetworkState()
    }

    override fun onCleared(){
        super.onCleared()
        compositeDisposable.dispose()
    }
}