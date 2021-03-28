package com.example.movieapp.ui.details.person.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.movieapp.data.local.entitys.Movie
import com.example.movieapp.databinding.MovieItemBinding
import com.squareup.picasso.Picasso

class MovieCreditsAdapter(
        private val listener: PeopleItemClickListener
):RecyclerView.Adapter<MovieCreditsAdapter.MovieCreditViewHolder>() {

    var dataList:List<Movie> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieCreditViewHolder {
        return MovieCreditViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: MovieCreditViewHolder, position: Int) {
        holder.bind(dataList[position], listener)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    class MovieCreditViewHolder(private val binder: MovieItemBinding): RecyclerView.ViewHolder(binder.root){

        fun bind(movieItem:Movie, listener: PeopleItemClickListener){
            Picasso.get()
                    .load("https://image.tmdb.org/t/p/w500${movieItem.poster_path}")
                    .into(binder.movieItemImg)
            binder.movieItemTitle.text = movieItem.title
            binder.movieItemTitle.setTextColor(Color.WHITE)

            binder.root.setOnClickListener {
                listener.onItemClicked(PeopleItemClickListener.ItemType.Movie, movieItem.id)
            }
        }

        companion object{
            fun create(parent: ViewGroup):MovieCreditViewHolder{
                val inflater = LayoutInflater.from(parent.context)
                val b = MovieItemBinding.inflate(inflater, parent, false)
                return MovieCreditViewHolder(b)
            }
        }
    }
}