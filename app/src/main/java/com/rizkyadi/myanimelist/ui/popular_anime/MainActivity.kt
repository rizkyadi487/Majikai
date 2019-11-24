package com.rizkyadi.myanimelist.ui.popular_anime

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.rizkyadi.myanimelist.R
import com.rizkyadi.myanimelist.data.api.TheJikanClient
import com.rizkyadi.myanimelist.data.api.TheJikanInterface
import com.rizkyadi.myanimelist.data.repository.NetworkState
import com.rizkyadi.myanimelist.ui.single_anime_details.SingleAnime
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainActivityViewModel

    lateinit var animeRepository:AnimePagedListRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val apiService : TheJikanInterface = TheJikanClient.getClient()

        animeRepository = AnimePagedListRepository(apiService)

        viewModel = getViewModel()

        val animeAdapter = PopularAnimePagedListAdapter(this)

        val gridLayoutManager = GridLayoutManager(this, 3)

        gridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup(){
            override fun getSpanSize(position: Int): Int {
                val viewType:Int = animeAdapter.getItemViewType(position)
                if(viewType == animeAdapter.ANIME_VIEW_TYPE) return 1
                else return 3
            }
        };

        rv_anime_list.layoutManager = gridLayoutManager
        rv_anime_list.setHasFixedSize(true)
        rv_anime_list.adapter = animeAdapter

        viewModel.animePagedList.observe(this, Observer {
            animeAdapter.submitList(it)
        })

        viewModel.networkState.observe(this, Observer {
            progress_bar_popular.visibility = if(viewModel.listIsEmpty() && it == NetworkState.LOADING) View.VISIBLE else View.GONE
            txt_error_popular.visibility = if(viewModel.listIsEmpty() && it == NetworkState.ERROR) View.VISIBLE else View.GONE

            if(!viewModel.listIsEmpty()){
                animeAdapter.setNetworkState(it)
            }
        })
    }

    private fun getViewModel() : MainActivityViewModel{
        return ViewModelProviders.of(this, object : ViewModelProvider.Factory{
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return MainActivityViewModel(animeRepository) as T
            }
        })[MainActivityViewModel::class.java]
    }
}
