package com.example.movieapp.ui.trends.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.movieapp.databinding.TrendItemBinding
import com.example.movieapp.domain.model.TrendsData
import com.squareup.picasso.Picasso

class TrendListAdapter:PagingDataAdapter<TrendsData, TrendListAdapter.TrendViewHolder>(COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrendViewHolder {
        return TrendViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: TrendViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class TrendViewHolder(val binding:TrendItemBinding):RecyclerView.ViewHolder(binding.root){

        fun bind(trendItem:TrendsData?){
            trendItem?.let { item ->
                Picasso.get()
                        .load("https://image.tmdb.org/t/p/w500${item.poster_path ?: item.profile_path}")
                        .into(binding.trendItemImg)
                binding.trendItemTitle.text = item.title ?: item.name
                binding.trendItemDescription.text = item.overview ?: ""
                binding.trendItemRatingText.text = (item.vote_average ?: 0).toString()
                binding.trendRatingBar.apply {
                    rating = item.vote_average?.toFloat() ?: 0f
                }
            }
        }

        companion object{
            fun create(parent: ViewGroup):TrendViewHolder{
                val inflater = LayoutInflater.from(parent.context)
                val v = TrendItemBinding.inflate(inflater, parent, false)
                return TrendViewHolder(v)
            }
        }
    }


    private companion object COMPARATOR: DiffUtil.ItemCallback<TrendsData>(){
        override fun areItemsTheSame(oldItem: TrendsData, newItem: TrendsData): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: TrendsData, newItem: TrendsData): Boolean {
            return oldItem == newItem
        }
    }
}