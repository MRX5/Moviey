package com.example.movies.ui.favourites.fragment

import GridSpacingItemDecoration
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.example.movies.R
import com.example.movies.base.BaseFragment
import com.example.movies.databinding.FragmentFavouritesBinding
import com.example.movies.listener.OnMovieClickListener
import com.example.movies.listener.OnTvShowClickListener
import com.example.movies.ui.favourites.adapter.FavouritesAdapter
import com.example.movies.ui.favourites.viewModel.FavouritesViewModel
import com.example.movies.ui.movies.movie_details.activity.MovieDetailsActivity
import com.example.movies.ui.tvShows.tv_details.activity.TvDetailsActivity
import com.example.movies.utils.Constants
import com.example.movies.utils.DataState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class FavouritesFragment : BaseFragment<FragmentFavouritesBinding>(R.layout.fragment_favourites),
OnMovieClickListener,OnTvShowClickListener{
    private val favouritesAdapter by lazy { FavouritesAdapter(this,this) }
    private val viewModel: FavouritesViewModel by viewModels()

    override fun setupRecyclerView() {
        binding.Content.recyclerView.apply {
            layoutManager = GridLayoutManager(context, 3)
            addItemDecoration(GridSpacingItemDecoration(16))
            setHasFixedSize(true)
            adapter = favouritesAdapter
            setHasFixedSize(true)
        }
    }

    override fun fetchData() {
        viewModel.fetchFavourites()
        lifecycleScope.launchWhenCreated {
        viewModel.favourites.collect {
            when(it){
                is DataState.Loading->{
                    binding.Content.progressBar.visibility=VISIBLE
                    binding.Content.noInternetLayout.visibility= GONE

                }
                is DataState.Success->{
                    binding.Content.progressBar.visibility=GONE
                    if(it.data.isEmpty()){
                        binding.emptyListTextview.visibility= VISIBLE
                    }else{
                    favouritesAdapter.setData(it.data)
                    }
                }
            }
        }}
    }

    override fun onMovieClick(movieID: Int) {
        val intent=Intent(context,MovieDetailsActivity::class.java).apply {
            putExtra(Constants.MOVIE_ID,movieID)
        }
        startActivity(intent)
    }

    override fun onTvShowClick(tvShowID: Int) {
        val intent=Intent(context,TvDetailsActivity::class.java).apply {
            putExtra(Constants.TV_ID,tvShowID)
        }
        startActivity(intent)
    }

}