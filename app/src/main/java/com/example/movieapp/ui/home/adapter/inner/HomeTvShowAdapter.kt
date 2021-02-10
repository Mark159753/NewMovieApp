package com.example.movieapp.ui.home.adapter.inner

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.movieapp.databinding.MovieItemBinding
import com.example.movieapp.domain.model.TvShowData
import com.example.movieapp.ui.home.listeners.ItemClickListener
import com.squareup.picasso.Picasso

class HomeTvShowAdapter:PagingDataAdapter<TvShowData, HomeTvShowAdapter.TvShowViewHolder>(COMPARATOR) {

    private var listener: ItemClickListener? = null

    fun setClickListener(l: ItemClickListener){
        this.listener = l
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TvShowViewHolder {
        return TvShowViewHolder.create(parent, listener)
    }

    override fun onBindViewHolder(holder: TvShowViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class TvShowViewHolder(
            private val binder:MovieItemBinding,
            private val listener: ItemClickListener?
    ):RecyclerView.ViewHolder(binder.root){

        fun bind(showItem:TvShowData?){
            showItem?.let { item ->
                Picasso.get()
                        .load("https://image.tmdb.org/t/p/w500${item.poster_path}")
                        .into(binder.movieItemImg)

                binder.movieItemTitle.text = item.name

                binder.root.setOnClickListener {
                    listener?.onItemSelected(item.id, ItemClickListener.TvShowType)
                }
            }
        }

        companion object{
            fun create(parent: ViewGroup, listener: ItemClickListener?):TvShowViewHolder{
                val inflater = LayoutInflater.from(parent.context)
                val v = MovieItemBinding.inflate(inflater, parent, false)
                return TvShowViewHolder(v, listener)
            }
        }
    }


    private companion object COMPARATOR:DiffUtil.ItemCallback<TvShowData>(){
        override fun areItemsTheSame(oldItem: TvShowData, newItem: TvShowData): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: TvShowData, newItem: TvShowData): Boolean {
            return oldItem == newItem
        }
    }
}