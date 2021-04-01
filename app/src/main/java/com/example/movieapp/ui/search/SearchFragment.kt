package com.example.movieapp.ui.search

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import com.example.movieapp.MovieApp
import com.example.movieapp.R
import com.example.movieapp.databinding.SearchFragmentBinding
import com.example.movieapp.ui.MainActivity
import com.example.movieapp.ui.home.listeners.ItemClickListener
import com.example.movieapp.ui.search.adapter.SearchAdapter
import com.example.movieapp.until.getQueryTextChangeStateFlow
import com.example.movieapp.until.showKeyboard
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

class SearchFragment : Fragment(), ItemClickListener {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel: SearchViewModel by viewModels{ viewModelFactory }

    private var binder:SearchFragmentBinding? = null

    private val searchAdapter = SearchAdapter(this)

    private var searchJob: Job? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binder = SearchFragmentBinding.inflate(inflater, container, false)
        return binder!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        injectMe()
        backPressBtn()

        initSearchKeyboardActionListener()
        initSearchList()
        observeInputQueryText()
        initLoadStateListener()
    }


    override fun onResume() {
        super.onResume()
        if (binder!!.searchInputText.isFocused)
            binder!!.searchInputText.showKeyboard()
    }


    private fun backPressBtn(){
        binder!!.searchBackBtn.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun initSearchKeyboardActionListener(){
        binder!!.searchInputText.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH){
                (activity as MainActivity).softHideKeyboard(requireContext())
                binder!!.searchList.requestFocus()
                makeSearch(binder!!.searchInputText.text.toString())
                return@setOnEditorActionListener true
            }
            return@setOnEditorActionListener false
        }
    }

    private fun initSearchList(){
        binder!!.searchList.apply {
            setHasFixedSize(true)
            adapter = searchAdapter
        }
    }

    private fun initLoadStateListener(){
        searchAdapter.addLoadStateListener { loadState ->
            binder!!.searchList.isVisible = loadState.source.refresh is LoadState.NotLoading
            binder!!.searchLoading.isVisible = loadState.source.refresh is LoadState.Loading

            val errorState = loadState.source.append as? LoadState.Error
                    ?: loadState.source.prepend as? LoadState.Error
                    ?: loadState.append as? LoadState.Error
                    ?: loadState.prepend as? LoadState.Error
            errorState?.let {
                Toast.makeText(
                        requireContext(),
                        "\uD83D\uDE28 Wooops ${it.error}",
                        Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    private fun observeInputQueryText(){
        lifecycleScope.launchWhenStarted {
            binder!!.searchInputText.getQueryTextChangeStateFlow()
                    .debounce(400)
                    .filter { query ->
                        return@filter query.isNotEmpty()
                    }
                    .distinctUntilChanged()
                    .flowOn(Dispatchers.Default)
                    .collectLatest { q ->
                        makeSearch(q)
                    }
        }
    }

    private fun makeSearch(query:String){
        searchJob?.cancel()
        searchJob = lifecycleScope.launch {
            viewModel.searchQuery(query)
                    .collectLatest {
                        searchAdapter.submitData(it)
                    }
        }
    }

    override fun onItemSelected(id: Int, contentType: Int) {
        var action:NavDirections? = null
        when(contentType){
            ItemClickListener.MovieType -> {
                action = SearchFragmentDirections.actionSearchFragmentToMovieDetailsFragment(id)
            }
            ItemClickListener.TvShowType -> {
                action = SearchFragmentDirections.actionSearchFragmentToTvDetailsFragment(id)
            }
            ItemClickListener.PersonType -> {
                action = SearchFragmentDirections.actionSearchFragmentToPersonDetailsFragment(id)
            }
        }
        action?.let { findNavController().navigate(action) }
    }

    private fun injectMe(){
        (activity?.application as MovieApp).getFragmentComponent()
                .create()
                .inject(this)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binder = null
        searchJob?.cancel()
    }

}