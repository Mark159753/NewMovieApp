package com.example.movieapp.ui.trends

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.PopupMenu
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.PagingData
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.movieapp.MovieApp
import com.example.movieapp.R
import com.example.movieapp.databinding.TrendsFragmentBinding
import com.example.movieapp.di.InjectingSavedStateViewModelFactory
import com.example.movieapp.domain.model.TrendsData
import com.example.movieapp.ui.trends.adapter.TrendListAdapter
import com.example.movieapp.ui.trends.state.TrendAction
import com.example.movieapp.ui.trends.state.TrendUiState
import com.example.movieapp.until.MarginItemDecorator
import com.example.movieapp.until.MediaTypes
import com.example.movieapp.until.TimeWindow
import com.google.android.material.tabs.TabLayout
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

class TrendsFragment : Fragment() {

    @Inject
    lateinit var abstractFactory: dagger.Lazy<InjectingSavedStateViewModelFactory>
    private val viewModel: TrendsViewModel by viewModels{ abstractFactory.get().create(this) }
    private var binder:TrendsFragmentBinding? = null

    private val trendListAdapter = TrendListAdapter()
    private var trendListJob:Job? = null

    private var stateJob:Job? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binder = TrendsFragmentBinding.inflate(inflater, container, false)
        return binder!!.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        injectMe()
        savedInstanceState?.let {
            restoreTimeWindowTab(it.getInt(TIME_WINDOW_TAB))
        }

        initContentTypeBtn()
        initTabLayout()

        initTrendList()
    }

    override fun onStart() {
        super.onStart()
        stateJob = lifecycleScope.launch {
            viewModel
                    .state
                    .collect {
                        handleState(it)
                    }
        }
    }


    private fun handleState(state:TrendUiState){
        state.pagingData?.let { submitTrendAdapter(it) }
    }

    private fun submitTrendAdapter(paging: Flow<PagingData<TrendsData>>){
        trendListJob?.cancel()
        trendListJob = lifecycleScope.launch {
            paging.collectLatest {
                trendListAdapter.submitData(it)
            }
        }
    }

    private fun initTrendList(){
        binder!!.trendsList.apply {
            adapter = trendListAdapter
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            setHasFixedSize(true)
            addItemDecoration(MarginItemDecorator(resources.getDimensionPixelSize(R.dimen.item_horizontal_margin), resources.getDimensionPixelSize(R.dimen.horizontal_margin)))
        }
    }


    private fun initTabLayout(){
        binder!!.trendsTabs.addOnTabSelectedListener(object :TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                tab?.let {
                    when (it.position) {
                        0 -> {
                            viewModel.setAction(TrendAction.TrendTimeWindow(TimeWindow.Day, "en-us*"))
                        }
                        1 -> {
                            viewModel.setAction(TrendAction.TrendTimeWindow(TimeWindow.Week, "en-us*"))
                        }
                        -1 -> {
                            Log.e("TabInvalid", "Invalid Position")
                        }
                        else -> {
                            Log.e("TabInvalid", "Unknown Position")
                        }
                    }
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })
    }

    private fun initContentTypeBtn(){
        binder!!.trendTypeSelectorBtn.setOnClickListener {
            showPopupMenu(it)
        }
    }

    private fun showPopupMenu(view:View){
        val popupMenu = PopupMenu(requireContext(), view)
        popupMenu.inflate(R.menu.trends_content_type_menu)
        popupMenu.setOnMenuItemClickListener {
            when(it.itemId){
                R.id.trend_type_all -> {
                    viewModel.setAction(TrendAction.TrendContentType(MediaTypes.All, "en-us*"))
                }
                R.id.trend_type_movie -> {
                    viewModel.setAction(TrendAction.TrendContentType(MediaTypes.Movie, "en-us*"))
                }
                R.id.trend_type_tv_show -> {
                    viewModel.setAction(TrendAction.TrendContentType(MediaTypes.Tv, "en-us*"))
                }
                R.id.trend_type_person -> {
                    viewModel.setAction(TrendAction.TrendContentType(MediaTypes.Person, "en-us*"))
                }
            }
            false
        }
        popupMenu.show()
    }

    private fun restoreTimeWindowTab(pos:Int){
        binder!!.trendsTabs.apply {
            selectTab(getTabAt(pos))
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(TIME_WINDOW_TAB, binder!!.trendsTabs.selectedTabPosition)
    }


    private fun injectMe(){
        (activity?.application as MovieApp).getFragmentComponent()
                .create()
                .inject(this)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binder = null
    }

    override fun onStop() {
        super.onStop()
        stateJob?.cancel()
    }

    companion object{
        const val TIME_WINDOW_TAB = "com.example.movieapp.ui.trends.TIME_WINDOW_TAB"
    }

}