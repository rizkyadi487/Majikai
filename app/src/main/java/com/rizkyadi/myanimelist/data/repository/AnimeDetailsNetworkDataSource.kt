package com.rizkyadi.myanimelist.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.rizkyadi.myanimelist.data.api.TheJikanInterface
import com.rizkyadi.myanimelist.data.vo.AnimeDetails
import io.reactivex.Scheduler
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class AnimeDetailsNetworkDataSource (private val apiService : TheJikanInterface, private val compositeDisposable: CompositeDisposable){
    private val _networkState = MutableLiveData<NetworkState>();
    val networkState: LiveData<NetworkState>
        get() = _networkState

    private val _downloadedAnimeDetailsResponse = MutableLiveData<AnimeDetails>();
    val downloadedAnimeResponse: LiveData<AnimeDetails>
        get() = _downloadedAnimeDetailsResponse

    fun fetchAnimeDetails(animeId: Int){
        _networkState.postValue(NetworkState.LOADING)
        try {
            compositeDisposable.add(
                apiService.getAnimeDetails(animeId)
                    .subscribeOn(Schedulers.io())
                    .subscribe(
                        {
                            _downloadedAnimeDetailsResponse.postValue(it)
                            _networkState.postValue(NetworkState.LOADED)
                        },
                        {
                            _networkState.postValue(NetworkState.ERROR)
                            Log.e("AnimeDetailsDataSource",it.message)
                        }
                    )
            )
        }catch (e: Exception){
            Log.e("AnimeDetailsDataSource",e.message)
        }
    }
}