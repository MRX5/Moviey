package com.example.movies.ui.home.fragment

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.children
import androidx.core.view.get
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.movies.R
import com.example.movies.adapter.MediaClickListener
import com.example.movies.databinding.FragmentHomeBinding
import com.example.movies.ui.home.adapter.SliderAdapter
import com.example.movies.ui.home.viewModel.HomeViewModel
import com.example.movies.ui.movies.adapter.MoviesAdapter
import com.example.movies.utils.DataState
import com.example.movies.utils.LinearSpacingItemDecoration
import com.rd.animation.type.AnimationType
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType
import com.smarteist.autoimageslider.SliderAnimations
import com.smarteist.autoimageslider.SliderViewAdapter
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class HomeFragment : Fragment(),MediaClickListener {
    lateinit var binding:FragmentHomeBinding
    private val sliderAdapter by lazy { SliderAdapter() }
    private val viewModel:HomeViewModel by viewModels()
    private val upcomingMoviesAdapter by lazy { MoviesAdapter(requireContext(),this) }
    private val topRatedMoviesAdapter by lazy { MoviesAdapter(requireContext(),this) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupSliderAdapter()
        setupRecyclerViews()
        fetchTrendingMovies()
        fetchUpcomingMovies()
        fetchTopRatedMovies()
        fetchTrendingTvShows()
    }

    private fun setupSliderAdapter() {
        binding.imageSlider.apply {
            setIndicatorAnimation(IndicatorAnimationType.WORM)
            setSliderTransformAnimation(SliderAnimations.FADETRANSFORMATION)
            startAutoCycle()
        }
    }

    private fun setupRecyclerViews() {
        binding.upcomingMoviesRecycler.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            addItemDecoration(LinearSpacingItemDecoration(context,LinearLayoutManager.HORIZONTAL))
            setHasFixedSize(true)
            adapter = upcomingMoviesAdapter
        }

        binding.topRatedMoviesRecycler.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            addItemDecoration(LinearSpacingItemDecoration(context,LinearLayoutManager.HORIZONTAL))
            setHasFixedSize(true)
            adapter = topRatedMoviesAdapter
        }
    }

    private fun fetchTrendingMovies() {
        if(viewModel.trendingMovies.value !is DataState.Success)
            viewModel.fetchTrendingMovies(1)

        lifecycleScope.launchWhenCreated {
            viewModel.trendingMovies.collect {
                when(it){
                    is DataState.Loading->{
                        binding.homeContent.visibility=GONE
                        binding.moviesProgressBar.visibility = VISIBLE
                    }
                    is DataState.Success -> {
                        binding.moviesProgressBar.visibility = GONE
                        binding.homeContent.visibility= VISIBLE
                        sliderAdapter.setData(it.data.results)
                        binding.imageSlider.setSliderAdapter(sliderAdapter)
                    }
                    is DataState.Error->{
                        binding.moviesProgressBar.visibility = GONE
                        Toast.makeText(context, it.exception, Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }

    private fun fetchUpcomingMovies() {
        if(viewModel.upcomingMovies.value !is DataState.Success)
            viewModel.fetchUpcomingMovies(1)

        lifecycleScope.launchWhenCreated {
            viewModel.upcomingMovies.collect {
                when(it){
                    is DataState.Loading->{
                    }
                    is DataState.Success -> {
                        upcomingMoviesAdapter.setData(it.data.results)
                    }
                    is DataState.Error->{
                       // binding.moviesProgressBar.visibility = View.GONE
                      //  Toast.makeText(context, it.exception, Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }

    private fun fetchTopRatedMovies() {
        if(viewModel.topRatedMovies.value !is DataState.Success)
            viewModel.fetchTopRatedMovies(1)

        lifecycleScope.launchWhenCreated {
            viewModel.topRatedMovies.collect {
                when(it){
                    is DataState.Loading->{
                    }
                    is DataState.Success -> {
                        topRatedMoviesAdapter.setData(it.data.results)
                    }
                    is DataState.Error->{
                      //  binding.moviesProgressBar.visibility = View.GONE
                       // Toast.makeText(context, it.exception, Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }

    private fun fetchTrendingTvShows() {
    }


    override fun onItemClick(mediaType: String, mediaID: Int) {
    }
}