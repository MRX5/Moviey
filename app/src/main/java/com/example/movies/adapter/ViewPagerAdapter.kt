package com.example.movies.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.movies.ui.movies.framgent.MoviesFragment
import com.example.movies.utils.Constants

class ViewPagerAdapter(manager:FragmentManager,lifecycle: Lifecycle): FragmentStateAdapter(manager,lifecycle) {
    override fun getItemCount(): Int =3

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0-> MoviesFragment.newInstance(Constants.POPULAR)
            1-> MoviesFragment.newInstance(Constants.UPCOMING)
            else->MoviesFragment.newInstance(Constants.TOP_RATED)
        }
    }
}