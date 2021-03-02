package com.example.movieapp.ui.comingSoon.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.movieapp.data.local.entitys.genre.TvGenre
import com.example.movieapp.databinding.ComingSoonItemBinding
import com.example.movieapp.domain.model.TvShowData
import com.squareup.picasso.Picasso
import java.lang.IndexOutOfBoundsException

class TvAdapter:PagingDataAdapter<TvShowData, TvAdapter.AiringTvViewHolder>(COMPARATOR) {

    private var tvGenre:List<TvGenre>? = null
    private var listener:ItemClickListener? = null

    fun setTvGenreList(genres: List<TvGenre>?) {
        tvGenre = genres
        notifyDataSetChanged()
    }

    fun setClickListener(l:ItemClickListener){
        this.listener = l
    }

    override fun onBindViewHolder(holder: AiringTvViewHolder, position: Int) {
        holder.bind(getItem(position), tvGenre,  listener)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AiringTvViewHolder {
        return AiringTvViewHolder.create(parent)
    }

    class AiringTvViewHolder(
        private val binding: ComingSoonItemBinding
    ):RecyclerView.ViewHolder(binding.root){

        fun bind(tvItem:TvShowData?, tvGenre:List<TvGenre>?,  listener:ItemClickListener?){
            tvItem?.let { item ->
                Picasso.get()
                    .load("https://image.tmdb.org/t/p/w500${item.poster_path}")
                    .into(binding.comingSoonIcon)

                binding.apply {
                    comingItemTitle.text = item.name
                    comingItemDate.text = item.first_air_date
                    comingItemDescription.text = item.overview
                }

                tvGenre?.let { bindTvGenre(item.genre_ids!!, it) }
                binding.root.setOnClickListener {
                    listener?.onItemClicked(item.id)
                }
            }
        }

        private fun bindTvGenre(genresIds:List<Int>, genres:List<TvGenre>){
            try {
                binding.itemGeneOne.apply {
                    text = findTvGenreById(genresIds[0], genres)
                    visibility = View.VISIBLE
                }
                binding.itemGeneTwo.apply {
                    text = findTvGenreById(genresIds[1], genres)
                    visibility = View.VISIBLE
                }
                binding.itemGeneThree.apply {
                    text = findTvGenreById(genresIds[2], genres)
                    visibility = View.VISIBLE
                }
            }catch (e: IndexOutOfBoundsException){}
        }

        private fun findTvGenreById(id:Int, list:List<TvGenre>):String?{
            list.forEach {
                if (it.id == id) return it.name
            }
            return null
        }

        companion object{
            fun create(parent: ViewGroup): AiringTvViewHolder {
                val inflater = LayoutInflater.from(parent.context)
                val v = ComingSoonItemBinding.inflate(inflater, parent, false)
                return AiringTvViewHolder(v)
            }
        }
    }

    private companion object COMPARATOR: DiffUtil.ItemCallback<TvShowData>(){
        override fun areItemsTheSame(oldItem: TvShowData, newItem: TvShowData): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: TvShowData, newItem: TvShowData): Boolean {
            return oldItem == newItem
        }
    }
}