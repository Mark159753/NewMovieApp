package com.example.movieapp.ui.comingSoon.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.movieapp.ui.comingSoon.ComingMoviesFragment
import com.example.movieapp.ui.comingSoon.ComingTvShowFragment
import java.lang.IllegalStateException

class ComingVpAdapter(fragment:Fragment):FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0 -> ComingMoviesFragment()
            1 -> ComingTvShowFragment()
            else -> throw IllegalStateException("Unknown Position -> $position")
        }
    }
}