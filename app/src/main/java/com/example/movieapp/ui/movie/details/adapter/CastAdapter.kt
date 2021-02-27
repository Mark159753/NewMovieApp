package com.example.movieapp.ui.movie.details.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.movieapp.data.model.cast.Cast
import com.example.movieapp.databinding.CastItemBinding
import com.squareup.picasso.Picasso

class CastAdapter():RecyclerView.Adapter<CastAdapter.CastViewHolder>() {

    private var dataList:List<Cast> = emptyList()
    private var listener:ItemClickListener? = null

    fun setListener(l:ItemClickListener){
        listener = l
    }

    fun setDataList(l:List<Cast>){
        dataList = l
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CastViewHolder {
        return CastViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: CastViewHolder, position: Int) {
        holder.bind(dataList[position], listener)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    class CastViewHolder(private val binding:CastItemBinding):RecyclerView.ViewHolder(binding.root){

        fun bind(castItem:Cast, listener:ItemClickListener?){
            Picasso.get()
                    .load("https://image.tmdb.org/t/p/w500${castItem.profile_path}")
                    .into(binding.castItemImg)
            binding.castItemName.text = castItem.name
            binding.root.setOnClickListener {
                listener?.itemClicked(ItemClickListener.PERSON_TYPE, castItem.id!!)
            }
        }

        companion object{
            fun create(parent: ViewGroup):CastViewHolder{
                val inflater = LayoutInflater.from(parent.context)
                val v = CastItemBinding.inflate(inflater, parent, false)
                return CastViewHolder(v)
            }
        }
    }
}