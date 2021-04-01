package com.example.movieapp.ui.search.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.movieapp.data.model.search.SearchItem
import com.example.movieapp.databinding.SearchItemBinding
import com.example.movieapp.ui.home.listeners.ItemClickListener
import com.example.movieapp.until.MediaTypes
import com.squareup.picasso.Picasso

class SearchAdapter(
        private val listener:ItemClickListener
):PagingDataAdapter<SearchItem, SearchAdapter.SearchViewHolder>(COMPARATOR) {


    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        holder.bind(getItem(position), listener)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        return SearchViewHolder.create(parent)
    }

    class SearchViewHolder(
            private val binder:SearchItemBinding
    ):RecyclerView.ViewHolder(binder.root){

        fun bind(searchItem: SearchItem?, listener: ItemClickListener){
            searchItem?.let { item ->
                Picasso.get()
                        .load("https://image.tmdb.org/t/p/w500${item.poster_path ?: item.profile_path}")
                        .into(binder.searchItemImg)
                binder.searchItemTitle.text = item.title ?: item.name
                binder.searchItemType.text = item.media_type
                binder.searchItemDescription.text = item.overview

                binder.root.setOnClickListener {
                    val type = when(item.media_type!!){
                        MediaTypes.Movie.type -> ItemClickListener.MovieType
                        MediaTypes.Person.type -> ItemClickListener.PersonType
                        MediaTypes.Tv.type -> ItemClickListener.TvShowType
                        else -> null
                    }
                    type?.let { listener.onItemSelected(item.id!!, it) }
                }
            }
        }

        companion object{
            fun create(parent: ViewGroup):SearchViewHolder{
                val inflater = LayoutInflater.from(parent.context)
                val b = SearchItemBinding.inflate(inflater, parent, false)
                return SearchViewHolder(b)
            }
        }
    }

    private companion object COMPARATOR:DiffUtil.ItemCallback<SearchItem>(){
        override fun areItemsTheSame(oldItem: SearchItem, newItem: SearchItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: SearchItem, newItem: SearchItem): Boolean {
            return oldItem == newItem
        }
    }
}