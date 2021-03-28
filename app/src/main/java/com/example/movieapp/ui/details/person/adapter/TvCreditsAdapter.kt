package com.example.movieapp.ui.details.person.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.movieapp.data.local.entitys.TvShow
import com.example.movieapp.databinding.MovieItemBinding
import com.squareup.picasso.Picasso

class TvCreditsAdapter(
        private val listener:PeopleItemClickListener
):RecyclerView.Adapter<TvCreditsAdapter.TvCreditViewHolder>() {

    var dataList:List<TvShow> = emptyList()
    set(value) {
        field = value
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TvCreditViewHolder {
        return TvCreditViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: TvCreditViewHolder, position: Int) {
        holder.bind(dataList[position], listener)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    class TvCreditViewHolder(private val binder:MovieItemBinding):RecyclerView.ViewHolder(binder.root){

        fun bind(tvItem:TvShow, listener: PeopleItemClickListener){
            Picasso.get()
                    .load("https://image.tmdb.org/t/p/w500${tvItem.poster_path}")
                    .into(binder.movieItemImg)
            binder.movieItemTitle.text = tvItem.name
            binder.movieItemTitle.setTextColor(Color.WHITE)

            binder.root.setOnClickListener {
                listener.onItemClicked(PeopleItemClickListener.ItemType.Tv, tvItem.id)
            }
        }

        companion object{
            fun create(parent: ViewGroup):TvCreditViewHolder{
                val inflater = LayoutInflater.from(parent.context)
                val b = MovieItemBinding.inflate(inflater, parent, false)
                return TvCreditViewHolder(b)
            }
        }
    }
}