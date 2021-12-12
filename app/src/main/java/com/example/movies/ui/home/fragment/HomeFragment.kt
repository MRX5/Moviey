package com.example.movies.ui.home.fragment

import android.content.Intent
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
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.movies.R
import com.example.movies.adapter.MediaClickListener
import com.example.movies.databinding.FragmentHomeBinding
import com.example.movies.ui.home.adapter.SliderAdapter
import com.example.movies.ui.home.viewModel.HomeViewModel
import com.example.movies.ui.movies.adapter.MoviesAdapter
import com.example.movies.ui.tvShows.adapter.OnTvShowClickListener
import com.example.movies.ui.tvShows.adapter.TvShowsAdapter
import com.example.movies.ui.tv_details.activity.TvDetailsActivity
import com.example.movies.utils.Constants
import com.example.movies.utils.DataState
import com.example.movies.utils.LinearSpacingItemDecoration
import com.google.android.material.snackbar.Snackbar
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType
import com.smarteist.autoimageslider.SliderAnimations
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class HomeFragment : Fragment(),MediaClickListener,OnTvShowClickListener {
    lateinit var binding:FragmentHomeBinding
    private val sliderAdapter by lazy { SliderAdapter() }
    private val viewModel:HomeViewModel by viewModels()
    private val upcomingMoviesAdapter by lazy { MoviesAdapter(requireContext(),this) }
    private val popularMoviesAdapter by lazy { MoviesAdapter(requireContext(),this) }
    private val trendingTvAdapter by lazy { TvShowsAdapter(requireContext(),this) }
    private val onTheAirTvAdapter by lazy { TvShowsAdapter(requireContext(),this) }
    private val page=1

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
        fetchPopularMovies()
        fetchTrendingTvShows()
        fetchOnTheAirTvShows()
    }

    private fun setupSliderAdapter() {
        binding.imageSlider.apply {
            setIndicatorAnimation(IndicatorAnimationType.WORM)
            setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION)
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
            adapter = popularMoviesAdapter
        }

        binding.trendingTvShowsRecycler.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            addItemDecoration(LinearSpacingItemDecoration(context,LinearLayoutManager.HORIZONTAL))
            setHasFixedSize(true)
            adapter = trendingTvAdapter
        }

        binding.onTheAirTvShowsRecycler.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            addItemDecoration(LinearSpacingItemDecoration(context,LinearLayoutManager.HORIZONTAL))
            setHasFixedSize(true)
            adapter = onTheAirTvAdapter
        }
    }

    private fun fetchTrendingMovies() {
        if(viewModel.trendingMovies.value !is DataState.Success)
            viewModel.fetchTrendingMovies(page)

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
            viewModel.fetchUpcomingMovies(page)

        lifecycleScope.launchWhenCreated {
            viewModel.upcomingMovies.collect {
                when(it){
                    is DataState.Loading->{
                    }
                    is DataState.Success -> {
                        upcomingMoviesAdapter.setData(it.data.results)
                    }
                    is DataState.Error->{

                    }
                }
            }
        }
    }

    private fun fetchPopularMovies() {
        if(viewModel.popularMovies.value !is DataState.Success)
            viewModel.fetchPopularMovies(page)

        lifecycleScope.launchWhenCreated {
            viewModel.popularMovies.collect {
                when(it){
                    is DataState.Loading->{
                    }
                    is DataState.Success -> {
                        popularMoviesAdapter.setData(it.data.results)
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
        viewModel.fetchTrendingTvShows(page)
        lifecycleScope.launchWhenCreated {
            viewModel.trendingTvShows.collect {
                when(it){
                    is DataState.Loading->{
                    }
                    is DataState.Success -> {
                        trendingTvAdapter.setData(it.data.results)
                    }
                    is DataState.Error->{
                        //  binding.moviesProgressBar.visibility = View.GONE
                        // Toast.makeText(context, it.exception, Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }

    private fun fetchOnTheAirTvShows(){
        viewModel.fetchOnTheAirTvShows(page)
        lifecycleScope.launchWhenCreated {
            viewModel.onTheAirTvShows.collect {
                when(it){
                    is DataState.Loading->{
                    }
                    is DataState.Success -> {
                        onTheAirTvAdapter.setData(it.data.results)
                    }
                    is DataState.Error->{
                        //  binding.moviesProgressBar.visibility = View.GONE
                        // Toast.makeText(context, it.exception, Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }

    override fun onItemClick(mediaType: String, mediaID: Int) {
    }

    override fun onTvShowClick(tvShowID: Int) {
        val intent = Intent(context, TvDetailsActivity::class.java).apply {
            putExtra(Constants.TV_ID, tvShowID)
        }
        startActivity(intent)
    }
}
