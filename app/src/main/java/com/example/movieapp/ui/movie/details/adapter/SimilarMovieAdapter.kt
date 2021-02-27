package com.example.movieapp.ui.movie.details.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.movieapp.data.model.similarMovies.SimilarMovie
import com.example.movieapp.databinding.MovieItemBinding
import com.squareup.picasso.Picasso

class SimilarMovieAdapter:PagingDataAdapter<SimilarMovie, SimilarMovieAdapter.SimilarMovieViewHolder>(COMPARATOR) {

    private var listener:ItemClickListener? = null

    fun setListener(l:ItemClickListener){
        listener = l
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SimilarMovieViewHolder {
        return SimilarMovieViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: SimilarMovieViewHolder, position: Int) {
        holder.bind(getItem(position), listener)
    }

    class SimilarMovieViewHolder(private val binding:MovieItemBinding):RecyclerView.ViewHolder(binding.root){

        fun bind(similarItem:SimilarMovie?, listener:ItemClickListener?){
            similarItem?.let { item ->
                Picasso.get()
                        .load("https://image.tmdb.org/t/p/w500${item.poster_path}")
                        .into(binding.movieItemImg)
                binding.movieItemTitle.text = item.title
                binding.root.setOnClickListener {
                    listener?.itemClicked(ItemClickListener.MOVIE_TYPE, item.id!!)
                }
            }
        }

        companion object{
            fun create(parent: ViewGroup):SimilarMovieViewHolder{
                val inflater = LayoutInflater.from(parent.context)
                val b = MovieItemBinding.inflate(inflater, parent, false)
                return SimilarMovieViewHolder(b)
            }
        }
    }

    private companion object COMPARATOR:DiffUtil.ItemCallback<SimilarMovie>(){
        override fun areItemsTheSame(oldItem: SimilarMovie, newItem: SimilarMovie): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: SimilarMovie, newItem: SimilarMovie): Boolean {
            return oldItem == newItem
        }
    }
}