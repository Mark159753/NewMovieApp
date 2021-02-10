package com.example.movieapp.ui.home.adapter.inner

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.movieapp.R
import com.example.movieapp.domain.model.TrendsData
import com.example.movieapp.ui.home.listeners.ItemClickListener
import com.squareup.picasso.Picasso

class HomeTrendAdapter:PagingDataAdapter<TrendsData, HomeTrendAdapter.TrendsViewHolder>(COMPARATOR) {

    private var listener:ItemClickListener? = null

    fun setClickListener(l:ItemClickListener){
        this.listener = l
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrendsViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.home_trend_item, parent, false)
        return TrendsViewHolder(v, listener)
    }

    override fun onBindViewHolder(holder: TrendsViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class TrendsViewHolder(
            private val view:View,
            private val listener: ItemClickListener?
            ):RecyclerView.ViewHolder(view){

        private val img = view.findViewById<ImageView>(R.id.home_trend_item_img)

        fun bind(item:TrendsData?){
            Picasso.get()
                    .load("https://image.tmdb.org/t/p/w500${item?.poster_path}")
                    .into(img)
            item?.let { trend ->
                view.setOnClickListener {
                    val type = when (trend.media_type){
                        "movie" -> ItemClickListener.MovieType
                        "tv" -> ItemClickListener.TvShowType
                        "person" -> ItemClickListener.PersonType
                        else -> -1
                    }
                    if (type != -1) listener?.onItemSelected(trend.id, type)
                }
            }
        }
    }

    private companion object COMPARATOR:DiffUtil.ItemCallback<TrendsData>(){
        override fun areItemsTheSame(oldItem: TrendsData, newItem: TrendsData): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: TrendsData, newItem: TrendsData): Boolean {
            return oldItem == newItem
        }
    }
}