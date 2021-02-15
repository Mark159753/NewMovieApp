package com.example.movieapp.ui.comingSoon

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.movieapp.R
import com.example.movieapp.databinding.ComingSoonFragmentBinding
import com.example.movieapp.ui.comingSoon.adapter.ComingVpAdapter
import com.google.android.material.tabs.TabLayoutMediator

class ComingSoonFragment : Fragment() {

    private var binder:ComingSoonFragmentBinding? = null

    private lateinit var vpAdapter: ComingVpAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binder = ComingSoonFragmentBinding.inflate(inflater, container, false)
        return binder!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        vpAdapter = ComingVpAdapter(this)

        initViewPager()
        initTabs()
    }


    private fun initViewPager() {
        binder!!.comingViewpager.apply {
            adapter = vpAdapter
        }
    }

    private fun initTabs(){
        TabLayoutMediator(binder!!.comingTabs, binder!!.comingViewpager){tab, position ->
            when(position){
                0 -> tab.text = getString(R.string.movie_tab)
                1 -> tab.text = getString(R.string.tv_show_tab)
            }
        }.attach()
    }



    override fun onDestroyView() {
        binder = null
        super.onDestroyView()
    }
}