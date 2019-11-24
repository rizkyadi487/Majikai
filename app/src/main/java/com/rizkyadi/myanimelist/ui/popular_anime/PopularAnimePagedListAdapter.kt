package com.rizkyadi.myanimelist.ui.popular_anime

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.rizkyadi.myanimelist.R
import com.rizkyadi.myanimelist.data.repository.NetworkState
import com.rizkyadi.myanimelist.data.vo.Anime
import com.rizkyadi.myanimelist.ui.single_anime_details.SingleAnime
import kotlinx.android.synthetic.main.anime_list_item.view.*
import kotlinx.android.synthetic.main.network_state_item.view.*

class PopularAnimePagedListAdapter (public val context:Context) : PagedListAdapter<Anime, RecyclerView.ViewHolder>(AnimeDiffCallback()){

    val ANIME_VIEW_TYPE = 1
    val NETWORK_VIEW_TYPE = 2

    private var networkState: NetworkState? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view:View

        if(viewType == ANIME_VIEW_TYPE){
            view = layoutInflater.inflate(R.layout.anime_list_item, parent, false)
            return AnimeItemViewHolder(view)
        }else{
            view = layoutInflater.inflate(R.layout.network_state_item, parent,false)
            return NetworkStateViewHolder(view)
        }
    }

    private fun hasExtraRow():Boolean{
        return networkState != null && networkState != NetworkState.LOADED
    }

    override fun getItemCount(): Int {
        return super.getItemCount() + if(hasExtraRow()) 1 else 0
    }

    override fun getItemViewType(position: Int): Int {
        return if (hasExtraRow() && position == itemCount -1) {
            NETWORK_VIEW_TYPE
        }else{
            ANIME_VIEW_TYPE
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(getItemViewType(position) == ANIME_VIEW_TYPE){
            (holder as AnimeItemViewHolder).bind(getItem(position),context)
        }else{
            (holder as NetworkStateViewHolder).bind(networkState)
        }
    }

    class AnimeDiffCallback : DiffUtil.ItemCallback<Anime>(){
        override fun areItemsTheSame(oldItem: Anime, newItem: Anime): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Anime, newItem: Anime): Boolean {
            return oldItem == newItem
        }
    }

    class AnimeItemViewHolder (view: View) : RecyclerView.ViewHolder(view){
        fun bind(anime: Anime?, context:Context){
            itemView.cv_anime_title.text = anime?.title
            itemView.cv_anime_rank.text = anime?.score.toString()

            val animePosterURL = anime?.imageUrl
            Glide.with(itemView.context)
                .load(animePosterURL)
                .into(itemView.cv_iv_anime_poster);

            itemView.setOnClickListener{
                val intent = Intent(context, SingleAnime::class.java)
                intent.putExtra("id",anime?.id)
                context.startActivity(intent)
            }
        }
    }

    class NetworkStateViewHolder (view : View) : RecyclerView.ViewHolder(view){
        fun bind(networkState: NetworkState?){
            if(networkState != null && networkState == NetworkState.LOADING){
                itemView.progress_bar_item.visibility = View.VISIBLE;
            }else{
                itemView.progress_bar_item.visibility = View.GONE;
            }

            if (networkState != null && networkState == NetworkState.ERROR) {
                itemView.error_msg_item.visibility = View.VISIBLE;
                itemView.error_msg_item.text = networkState.msg;
            }
            else if (networkState != null && networkState == NetworkState.ENDOFLIST) {
                itemView.error_msg_item.visibility = View.VISIBLE;
                itemView.error_msg_item.text = networkState.msg;
            }
            else {
                itemView.error_msg_item.visibility = View.GONE;
            }
        }
    }

    fun setNetworkState(networkState: NetworkState){
        val previousState:NetworkState? = this.networkState
        val hadExtraRow:Boolean = hasExtraRow()
        this.networkState = networkState
        val hasExtraRow:Boolean = hasExtraRow()

        if(hadExtraRow != hasExtraRow){
            if(hadExtraRow){
                notifyItemRemoved(super.getItemCount())
            }else{
                notifyItemInserted(super.getItemCount())
            }
        }else if(hasExtraRow && previousState != networkState){
            notifyItemChanged(itemCount - 1)
        }
    }
}