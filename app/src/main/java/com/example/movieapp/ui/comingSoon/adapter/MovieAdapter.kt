package com.example.movieapp.ui.comingSoon.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.movieapp.data.local.entitys.genre.MovieGenre
import com.example.movieapp.databinding.ComingSoonItemBinding
import com.example.movieapp.domain.model.MovieData
import com.squareup.picasso.Picasso
import java.lang.IndexOutOfBoundsException

class MovieAdapter:PagingDataAdapter<MovieData, MovieAdapter.ComingMovieViewHolder>(COMPARATOR) {

    private var movieGenre:List<MovieGenre>? = null
    private var listener:ItemClickListener? = null

    fun setMovieGenreList(genres: List<MovieGenre>?) {
        movieGenre = genres
        notifyDataSetChanged()
    }

    fun setClickListener(l:ItemClickListener){
        this.listener = l
    }


    override fun onBindViewHolder(holder: ComingMovieViewHolder, position: Int) {
        holder.bind(getItem(position), movieGenre, listener)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ComingMovieViewHolder {
        return ComingMovieViewHolder.create(parent)
    }


    class ComingMovieViewHolder(private val binding:ComingSoonItemBinding):RecyclerView.ViewHolder(binding.root){

        fun bind(movieItem:MovieData?, movieGenre: List<MovieGenre>?, listener: ItemClickListener?){
            movieItem?.let { item ->
                Picasso.get()
                    .load("https://image.tmdb.org/t/p/w500${item.poster_path}")
                    .into(binding.comingSoonIcon)

                binding.apply {
                    comingItemTitle.text = item.title
                    comingItemDate.text = item.release_date
                    comingItemDescription.text = item.overview
                }
                movieGenre?.let { bindMovieGenre(item.genre_ids!!, it) }
                binding.root.setOnClickListener {
                    listener?.onItemClicked(item.id)
                }
            }
        }

       private fun bindMovieGenre(genresIds:List<Int>, genres:List<MovieGenre>){
           try {
               binding.itemGeneOne.apply {
                   text = findMovieGenreById(genresIds[0], genres)
                   visibility = View.VISIBLE
               }
               binding.itemGeneTwo.apply {
                   text = findMovieGenreById(genresIds[1], genres)
                   visibility = View.VISIBLE
               }
               binding.itemGeneThree.apply {
                   text = findMovieGenreById(genresIds[2], genres)
                   visibility = View.VISIBLE
               }
           }catch (e:IndexOutOfBoundsException){}
       }

        private fun findMovieGenreById(id:Int, list:List<MovieGenre>):String?{
            list.forEach {
                if (it.id == id)
                    return it.name
            }
            return null
        }


        companion object{
            fun create(parent: ViewGroup):ComingMovieViewHolder{
                val inflater = LayoutInflater.from(parent.context)
                val v = ComingSoonItemBinding.inflate(inflater, parent, false)
                return ComingMovieViewHolder(v)
            }
        }
    }

    private companion object COMPARATOR: DiffUtil.ItemCallback<MovieData>(){
        override fun areItemsTheSame(oldItem: MovieData, newItem: MovieData): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: MovieData, newItem: MovieData): Boolean {
            return oldItem == newItem
        }
    }
}