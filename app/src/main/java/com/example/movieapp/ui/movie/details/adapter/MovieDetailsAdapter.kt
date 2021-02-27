package com.example.movieapp.ui.movie.details.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.movieapp.R
import com.example.movieapp.data.model.movieDetails.MovieDetailsResponse
import com.example.movieapp.databinding.MovieDetailsCastItemBinding
import com.example.movieapp.databinding.MovieDetailsDescriptionItemBinding
import com.example.movieapp.databinding.MovieDetailsSimilarItemBinding
import com.example.movieapp.databinding.MovieDetailsheaderItemBinding
import com.example.movieapp.until.MarginItemDecorator
import java.lang.IllegalStateException

class MovieDetailsAdapter:RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var data:MovieDetailsData? = null
    private var listener:ItemClickListener? = null

    fun setListener(l:ItemClickListener){
        listener = l
    }

    fun setData(d:MovieDetailsData){
        this.data = d
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType){
            HEAD_SECTION -> HeaderViewHolder.create(parent)
            CAST_SECTION -> CastViewHolder.create(parent)
            DESCRIPTION_SECTION -> DescriptionViewHolder.create(parent)
            SIMILAR_MOVIES_SECTION -> SimilarMoviesViewHolder.create(parent)
            else -> throw IllegalStateException("Unknown ViewHolder ViewType -> $viewType")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        data?.let { d ->
            when(holder){
                is HeaderViewHolder -> { holder.bind(d.details, listener) }
                is CastViewHolder -> { holder.bind(d.castAdapter)}
                is DescriptionViewHolder -> { holder.bind(d.details.overview)}
                is SimilarMoviesViewHolder -> { holder.bind(d.similarAdapter)}
            }
        }
    }

    override fun getItemCount(): Int {
        return ITEM_SIZE
    }


    class SimilarMoviesViewHolder(
            private val binder:MovieDetailsSimilarItemBinding
    ):RecyclerView.ViewHolder(binder.root){

        fun bind(similarAdapter:SimilarMovieAdapter){
            binder.movieDetailsSimilarList.apply {
                adapter = similarAdapter
                setHasFixedSize(true)
                addItemDecoration(MarginItemDecorator(0, context.resources.getDimensionPixelSize(R.dimen.item_horizontal_margin), true))
            }
        }

        companion object{
            fun create(parent: ViewGroup):SimilarMoviesViewHolder{
                val inflater = LayoutInflater.from(parent.context)
                val b = MovieDetailsSimilarItemBinding.inflate(inflater, parent, false)
                return SimilarMoviesViewHolder(b)
            }
        }
    }

    class DescriptionViewHolder(
            private val binder:MovieDetailsDescriptionItemBinding
    ): RecyclerView.ViewHolder(binder.root){

        fun bind(description:String?){
            binder.movieDetailsStoryline.text = description
        }

        companion object{
            fun create(parent: ViewGroup):DescriptionViewHolder{
                val inflater = LayoutInflater.from(parent.context)
                val b = MovieDetailsDescriptionItemBinding.inflate(inflater, parent, false)
                return DescriptionViewHolder(b)
            }
        }
    }

    class CastViewHolder(
            private val binder:MovieDetailsCastItemBinding
    ):RecyclerView.ViewHolder(binder.root){

        fun bind(castAdapter:CastAdapter){
            binder.movieDetailsCastList.apply {
                adapter = castAdapter
                setHasFixedSize(true)
                addItemDecoration(MarginItemDecorator(0, resources.getDimensionPixelSize(R.dimen.item_horizontal_margin), true))
            }
        }

        companion object{
            fun create(parent: ViewGroup):CastViewHolder{
                val inflater = LayoutInflater.from(parent.context)
                val b = MovieDetailsCastItemBinding.inflate(inflater, parent, false)
                return CastViewHolder(b)
            }
        }
    }

    class HeaderViewHolder(
            private val binder:MovieDetailsheaderItemBinding
    ):RecyclerView.ViewHolder(binder.root){

        fun bind(data:MovieDetailsResponse, listener:ItemClickListener?){
            binder.movieDetails = data
            binder.movieDetailsPlayVideoBtn.setOnClickListener {
                listener?.itemClicked(ItemClickListener.VIDEO_TYPE, data.id)
            }
        }

        companion object{
            fun create(parent: ViewGroup):HeaderViewHolder{
                val inflater = LayoutInflater.from(parent.context)
                val b = MovieDetailsheaderItemBinding.inflate(inflater, parent, false)
                return HeaderViewHolder(b)
            }
        }
    }


    override fun getItemViewType(position: Int): Int {
        return when(position){
            0 -> HEAD_SECTION
            1 -> CAST_SECTION
            2 -> DESCRIPTION_SECTION
            3 -> SIMILAR_MOVIES_SECTION
            else -> super.getItemViewType(position)
        }
    }

    companion object{
        private const val HEAD_SECTION = 1
        private const val CAST_SECTION = 2
        private const val DESCRIPTION_SECTION = 3
        private const val SIMILAR_MOVIES_SECTION = 4

        private const val ITEM_SIZE = 4
    }
}

data class MovieDetailsData(
        val details:MovieDetailsResponse,
        val castAdapter: CastAdapter,
        val similarAdapter: SimilarMovieAdapter
)