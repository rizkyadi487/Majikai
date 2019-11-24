package com.rizkyadi.myanimelist.ui.popular_anime

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.rizkyadi.myanimelist.data.api.POST_PER_PAGE
import com.rizkyadi.myanimelist.data.api.TheJikanInterface
import com.rizkyadi.myanimelist.data.repository.AnimeDataSource
import com.rizkyadi.myanimelist.data.repository.AnimeDataSourceFactory
import com.rizkyadi.myanimelist.data.repository.NetworkState
import com.rizkyadi.myanimelist.data.vo.Anime
import io.reactivex.disposables.CompositeDisposable

class AnimePagedListRepository (private val apiService : TheJikanInterface) {
    lateinit var animePagedList : LiveData<PagedList<Anime>>
    lateinit var animesDataSourceFactory : AnimeDataSourceFactory

    fun fetchLiveAnimePagedList (compositeDisposable: CompositeDisposable) : LiveData<PagedList<Anime>>{
        animesDataSourceFactory = AnimeDataSourceFactory(apiService,compositeDisposable)

        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPageSize(POST_PER_PAGE)
            .build()

        animePagedList = LivePagedListBuilder(animesDataSourceFactory, config).build()

        return animePagedList
    }

    fun getNetworkState(): LiveData<NetworkState>{
        return Transformations.switchMap<AnimeDataSource,NetworkState>(
            animesDataSourceFactory.animesLiveDataSource, AnimeDataSource::networkState
        )
    }
}