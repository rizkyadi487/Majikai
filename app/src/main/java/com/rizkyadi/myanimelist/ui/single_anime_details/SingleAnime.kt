package com.rizkyadi.myanimelist.ui.single_anime_details

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.rizkyadi.myanimelist.R
import com.rizkyadi.myanimelist.data.api.TheJikanClient
import com.rizkyadi.myanimelist.data.api.TheJikanInterface
import com.rizkyadi.myanimelist.data.repository.NetworkState
import com.rizkyadi.myanimelist.data.vo.AnimeDetails
import kotlinx.android.synthetic.main.activity_single_anime.*

class SingleAnime : AppCompatActivity() {

    private lateinit var viewModel: SingleAnimeViewModel
    private lateinit var animeRepository:AnimeDetailsRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_single_anime)

        val animeId: Int = intent.getIntExtra("id",1)

        val apiService : TheJikanInterface = TheJikanClient.getClient()
        animeRepository = AnimeDetailsRepository(apiService)

        viewModel = getViewModel(animeId)

        viewModel.animeDetails.observe(this, Observer {
            bindUI(it)
        })

        viewModel.networkState.observe(this, Observer {
            progress_bar.visibility = if(it == NetworkState.LOADING) View.VISIBLE else View.GONE
            txt_error.visibility = if(it == NetworkState.ERROR) View.VISIBLE else View.GONE
        })
    }

    fun bindUI(it:AnimeDetails){
        anime_title.text = it.title
        anime_overview.text = it.synopsis

        val animePosterURL = it.imageUrl
        Glide.with(this)
            .load(animePosterURL)
            .into(iv_anime_poster);

    }

    private fun getViewModel(animeId:Int): SingleAnimeViewModel{
        return ViewModelProviders.of(this,object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass:Class<T>):T{
                @Suppress("UNCHECKED_CAST")
                return SingleAnimeViewModel(animeRepository,animeId) as T
            }
        })[SingleAnimeViewModel::class.java]
    }
}
