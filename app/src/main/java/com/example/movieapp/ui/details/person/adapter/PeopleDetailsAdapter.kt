package com.example.movieapp.ui.details.person.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.movieapp.R
import com.example.movieapp.data.model.people.PeopleDetails
import com.example.movieapp.databinding.PeopleDetailsHeaderBinding
import com.example.movieapp.until.MarginItemDecorator

class PeopleDetailsAdapter(
        private val movieAdapter: MovieCreditsAdapter,
        private val tvAdapter: TvCreditsAdapter
):RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var details:PeopleDetails? = null

    fun setDetails(details: PeopleDetails?){
        this.details = details
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when(viewType){
            HEADER_TYPE -> HeaderViewHolder.create(parent, inflater)
            CREDITS_TYPE -> CreditsViewHolder(inflater.inflate(R.layout.people_details_credits_item, parent, false))
            else -> throw IllegalStateException("Unknown ViewType")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder){
            is HeaderViewHolder -> holder.bind(details)
            is CreditsViewHolder -> holder.bind(movieAdapter, tvAdapter)
        }
    }

    override fun getItemCount(): Int {
        return 2
    }

    override fun getItemViewType(position: Int): Int {
        return when(position){
            0 -> HEADER_TYPE
            1 -> CREDITS_TYPE
            else -> super.getItemViewType(position)
        }
    }

    class HeaderViewHolder(private val binder:PeopleDetailsHeaderBinding):RecyclerView.ViewHolder(binder.root){

        fun bind(peopleDetails:PeopleDetails?){
            peopleDetails?.let { details ->
                binder.details = details
            }
        }

        companion object{
            fun create(parent:ViewGroup, inflater: LayoutInflater):HeaderViewHolder{
                val b = PeopleDetailsHeaderBinding.inflate(inflater, parent, false)
                return HeaderViewHolder(b)
            }
        }
    }

    class CreditsViewHolder(private val view:View):RecyclerView.ViewHolder(view){

        private val movieCredits = view.findViewById<RecyclerView>(R.id.people_details_movie_credits)
        private val tvCredits = view.findViewById<RecyclerView>(R.id.people_details_tv_credits)

        fun bind(movieAdapter:MovieCreditsAdapter, tvAdapter:TvCreditsAdapter){
            val decoration = MarginItemDecorator(0, view.context.resources.getDimensionPixelSize(R.dimen.item_horizontal_margin), true)
            movieCredits.apply {
                addItemDecoration(decoration)
                adapter = movieAdapter
                setHasFixedSize(true)
            }
            tvCredits.apply {
                adapter = tvAdapter
                addItemDecoration(decoration)
                setHasFixedSize(true)
            }
        }
    }

    companion object{
        private const val HEADER_TYPE = 1
        private const val CREDITS_TYPE = 2
    }
}