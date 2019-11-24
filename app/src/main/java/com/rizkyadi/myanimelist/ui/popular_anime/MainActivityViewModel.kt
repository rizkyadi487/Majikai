package com.rizkyadi.myanimelist.ui.popular_anime

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.rizkyadi.myanimelist.data.repository.NetworkState
import com.rizkyadi.myanimelist.data.vo.Anime
import io.reactivex.disposables.CompositeDisposable

class MainActivityViewModel (private val animeRepository:AnimePagedListRepository):ViewModel() {
    private val compositeDisposable = CompositeDisposable()

    val animePagedList : LiveData<PagedList<Anime>> by lazy {
        animeRepository.fetchLiveAnimePagedList(compositeDisposable)
    }

    val networkState : LiveData<NetworkState> by lazy {
        animeRepository.getNetworkState()
    }

    fun listIsEmpty(): Boolean{
        return animePagedList.value?.isEmpty()?:true
    }

    override fun onCleared(){
        super.onCleared()
        compositeDisposable.dispose()
    }
}