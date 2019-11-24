package com.rizkyadi.myanimelist.data.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.rizkyadi.myanimelist.data.api.FIRST_PAGE
import com.rizkyadi.myanimelist.data.api.TheJikanInterface
import com.rizkyadi.myanimelist.data.vo.Anime
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class AnimeDataSource (private val apiService:TheJikanInterface, private val compositeDisposable:CompositeDisposable) : PageKeyedDataSource<Int, Anime>(){

    private var page = FIRST_PAGE

    val networkState : MutableLiveData<NetworkState> = MutableLiveData()

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, Anime>) {
        networkState.postValue(NetworkState.LOADING)

        compositeDisposable.add(
            apiService.getPopularAnime(page)
                .subscribeOn(Schedulers.io())
                .subscribe(
                    {
                        callback.onResult(it.animeList,null,page+1)
                        networkState.postValue(NetworkState.LOADED)
                    },{
                        networkState.postValue(NetworkState.ERROR)
                        Log.e("AnimeDataSource",it.message)
                    }
                )
        )
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Anime>) {
        networkState.postValue(NetworkState.LOADING)

        compositeDisposable.add(
            apiService.getPopularAnime(params.key)
                .subscribeOn(Schedulers.io())
                .subscribe(
                    {
                        if(params.key <= 5){
                            callback.onResult(it.animeList,params.key+1)
                            networkState.postValue(NetworkState.LOADED)
                        }else{
                            networkState.postValue(NetworkState.ENDOFLIST)
                        }
                    },{
                        networkState.postValue(NetworkState.ERROR)
                        Log.e("AnimeDataSource",it.message)
                    }
                )
        )
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Anime>) {

    }

}