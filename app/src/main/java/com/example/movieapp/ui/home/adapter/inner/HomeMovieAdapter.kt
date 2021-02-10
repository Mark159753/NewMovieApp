package com.example.movieapp.ui.home.adapter.inner

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.movieapp.databinding.MovieItemBinding
import com.example.movieapp.domain.model.MovieData
import com.example.movieapp.ui.home.listeners.ItemClickListener
import com.squareup.picasso.Picasso

class HomeMovieAdapter:PagingDataAdapter<MovieData, HomeMovieAdapter.MovieViewHolder>(COMPARATOR) {

    private var listener:ItemClickListener? = null

    fun setListener(l:ItemClickListener){
        this.listener = l
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        return MovieViewHolder.create(parent, listener)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class MovieViewHolder(
            private val binding:MovieItemBinding,
            private val listener: ItemClickListener?
            ):RecyclerView.ViewHolder(binding.root){


        fun bind(item:MovieData?){
            item?.let { movie ->
                Picasso.get()
                        .load("https://image.tmdb.org/t/p/w500${movie.poster_path}")
                        .into(binding.movieItemImg)
                binding.movieItemTitle.text = movie.title

                binding.root.setOnClickListener {
                    listener?.onItemSelected(movie.id, ItemClickListener.MovieType)
                }
            }
        }

        companion object{
            fun create(parent:ViewGroup, listener: ItemClickListener?):MovieViewHolder{
                val inflater = LayoutInflater.from(parent.context)
                val v = MovieItemBinding.inflate(inflater, parent, false)
                return MovieViewHolder(v, listener)
            }
        }
    }

    private companion object COMPARATOR:DiffUtil.ItemCallback<MovieData>(){
        override fun areItemsTheSame(oldItem: MovieData, newItem: MovieData): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: MovieData, newItem: MovieData): Boolean {
            return oldItem == newItem
        }
    }
}