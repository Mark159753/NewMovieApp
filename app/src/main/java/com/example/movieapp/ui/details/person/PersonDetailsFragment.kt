package com.example.movieapp.ui.details.person

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.movieapp.MovieApp
import com.example.movieapp.databinding.PersonDetailsFragmentBinding
import com.example.movieapp.ui.details.person.adapter.MovieCreditsAdapter
import com.example.movieapp.ui.details.person.adapter.PeopleDetailsAdapter
import com.example.movieapp.ui.details.person.adapter.PeopleItemClickListener
import com.example.movieapp.ui.details.person.adapter.TvCreditsAdapter
import com.example.movieapp.ui.details.person.state.Action
import com.example.movieapp.ui.details.person.state.State
import com.example.movieapp.until.ConnectionLiveData
import com.example.movieapp.until.isInternetAvailable
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

class PersonDetailsFragment : Fragment(), PeopleItemClickListener {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel: PersonDetailsViewModel by viewModels{ viewModelFactory }

    private var binding:PersonDetailsFragmentBinding? = null

    private val movieCreditAdapter = MovieCreditsAdapter(this)
    private val tvCreditsAdapter = TvCreditsAdapter(this)
    private val peopleDetailsAdapter = PeopleDetailsAdapter(movieCreditAdapter, tvCreditsAdapter)

    private lateinit var connectivityLiveData:ConnectionLiveData

    val args:PersonDetailsFragmentArgs by navArgs()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        binding = PersonDetailsFragmentBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        injectMe()

        connectivityLiveData = ConnectionLiveData(requireContext())

        initBackPressBtn()
        observeInternetConnection()

        initPeopleDetailsRecyclerView()
        handleState()

        if (isInternetAvailable(requireContext())){
            viewModel.setAction(Action.Load(args.personId))
        }else{
            viewModel.setAction(Action.NoInternetConnection)
        }
    }

    private fun initBackPressBtn(){
        binding?.peopleDetailsBackBtn?.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun handleState(){
        lifecycleScope.launchWhenStarted {
            viewModel.state.collect { state ->
                when(state){
                    is State.DataState -> {
                        state.data.movieCredits?.let { movieCreditAdapter.dataList = it }
                        state.data.tvCredits?.let { tvCreditsAdapter.dataList = it }
                        state.data.details?.let { peopleDetailsAdapter.setDetails(it) }
                        showHideLoading(false)
                    }
                    State.LoadingState -> {
                        showHideLoading(true)
                    }
                    State.NoInternetConnection -> {
                        showHideNoInternetConnectionMsg(false)
                    }
                }
            }
        }
    }

    private fun showHideLoading(loading:Boolean){
        if (loading) {
            binding!!.apply {
                peopleDetailsRecycleView.visibility = View.GONE
                peopleDetailsLoading.visibility = View.VISIBLE
                peopleDetailsNoInternetConnection.visibility = View.GONE
            }
        }else{
            binding!!.apply {
                peopleDetailsRecycleView.visibility = View.VISIBLE
                peopleDetailsLoading.visibility = View.GONE
                peopleDetailsNoInternetConnection.visibility = View.GONE
            }
        }
    }

    private fun showHideNoInternetConnectionMsg(connected:Boolean){
        if (connected){
            binding!!.apply {
                peopleDetailsRecycleView.visibility = View.VISIBLE
                peopleDetailsLoading.visibility = View.GONE
                peopleDetailsNoInternetConnection.visibility = View.GONE
            }
        }else{
            binding!!.apply {
                peopleDetailsRecycleView.visibility = View.GONE
                peopleDetailsLoading.visibility = View.GONE
                peopleDetailsNoInternetConnection.visibility = View.VISIBLE
            }
        }
    }

    private fun initPeopleDetailsRecyclerView(){
        binding!!.peopleDetailsRecycleView.apply {
            adapter = peopleDetailsAdapter
            setHasFixedSize(true)
        }
    }

    override fun onItemClicked(type: PeopleItemClickListener.ItemType, id: Int) {
        when(type){
            PeopleItemClickListener.ItemType.Movie -> {
                val action = PersonDetailsFragmentDirections.actionPersonDetailsFragmentToMovieDetailsFragment(id)
                findNavController().navigate(action)
            }
            PeopleItemClickListener.ItemType.Tv -> {
                val action = PersonDetailsFragmentDirections.actionPersonDetailsFragmentToTvDetailsFragment(id)
                findNavController().navigate(action)
            }
        }
    }

    private fun observeInternetConnection(){
        connectivityLiveData.observe(viewLifecycleOwner){
            if (it){
                viewModel.setAction(Action.Load(args.personId))
            }else{
                viewModel.setAction(Action.NoInternetConnection)
            }
        }
    }

    private fun injectMe(){
        (activity?.application as MovieApp).getFragmentComponent()
                .create()
                .inject(this)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

}