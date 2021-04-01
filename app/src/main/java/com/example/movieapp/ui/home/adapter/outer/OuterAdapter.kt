package com.example.movieapp.ui.home.adapter.outer

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.movieapp.R
import com.example.movieapp.databinding.HomeItemHeaderBinding
import com.example.movieapp.databinding.HomeItemListBinding
import com.example.movieapp.ui.home.adapter.HomeItem
import com.example.movieapp.ui.home.listeners.ItemClickListener
import com.example.movieapp.ui.home.transformer.SliderTransformer
import com.example.movieapp.until.MarginItemDecorator
import java.lang.IllegalStateException

class OuterAdapter(
        private val dataList:List<HomeItem>
        ):RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val viewPool = RecyclerView.RecycledViewPool()

    private var listener: ItemClickListener? = null

    fun setListener(l:ItemClickListener){
        this.listener = l
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType){
                HEADER_ITEM -> HeaderViewHolder.create(parent)
                ORDINARY_ITEM -> OrdinaryViewHolder.create(parent)
                TV_SHOW_ITEM -> TvShowViewHolder.create(parent)
                else -> throw IllegalStateException("UNKNOWN ViewType")
            }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder){
            is HeaderViewHolder -> holder.bind(dataList[position], listener)
            is TvShowViewHolder -> holder.bind(dataList[position], viewPool)
            is OrdinaryViewHolder -> holder.bind(dataList[position], viewPool)
        }
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == 0) HEADER_ITEM else if (position == 2) TV_SHOW_ITEM else ORDINARY_ITEM
    }

    class HeaderViewHolder(private val binding:HomeItemHeaderBinding):RecyclerView.ViewHolder(binding.root){

        fun bind(item:HomeItem?, listener: ItemClickListener?){
            item?.let { homeItem ->
                binding.itemHomeTrends.apply {
                    adapter = homeItem.adapter
                    setPageTransformer(SliderTransformer(3))
                    offscreenPageLimit = 3
                }
                binding.itemSearchBox.setOnClickListener {
                    listener?.onItemSelected(-1, ItemClickListener.SearchType)
                }
            }
        }

        companion object{
            fun create(parent: ViewGroup):HeaderViewHolder{
                val inflater = LayoutInflater.from(parent.context)
                val v = HomeItemHeaderBinding.inflate(inflater, parent, false)
                return HeaderViewHolder(v)
            }
        }
    }

    class OrdinaryViewHolder(private val binding:HomeItemListBinding):RecyclerView.ViewHolder(binding.root){

        fun bind(item:HomeItem?, viewPool: RecyclerView.RecycledViewPool){
            item?.let { homeItem ->
                binding.ordinaryItemList.apply {
                    adapter = homeItem.adapter
                    layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                    setHasFixedSize(true)
                    addItemDecoration(MarginItemDecorator(0, context.resources.getDimensionPixelSize(R.dimen.item_horizontal_margin), true))
//                    setRecycledViewPool(viewPool)
                }
                binding.ordinaryItemTitle.text = item.title
            }
        }

        companion object{
            fun create(parent: ViewGroup):OrdinaryViewHolder{
                val inflater = LayoutInflater.from(parent.context)
                val v = HomeItemListBinding.inflate(inflater, parent, false)
                return OrdinaryViewHolder(v)
            }
        }
    }

    class TvShowViewHolder(private val binding: HomeItemListBinding):RecyclerView.ViewHolder(binding.root){

        fun bind(item:HomeItem?, viewPool: RecyclerView.RecycledViewPool){
            item?.let { homeItem ->
                    binding.ordinaryItemList.apply {
                        adapter = homeItem.adapter
                        layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                        setHasFixedSize(true)
                        addItemDecoration(MarginItemDecorator(0, context.resources.getDimensionPixelSize(R.dimen.item_horizontal_margin), true))
//                        setRecycledViewPool(viewPool)
                    }
                binding.ordinaryItemTitle.text = item.title
            }
        }

        companion object{
            fun create(parent: ViewGroup):TvShowViewHolder{
                val inflater = LayoutInflater.from(parent.context)
                val v = HomeItemListBinding.inflate(inflater, parent, false)
                return TvShowViewHolder(v)
            }
        }
    }

    companion object{
        const val HEADER_ITEM = 1
        const val ORDINARY_ITEM = 2
        const val TV_SHOW_ITEM = 3
    }
}