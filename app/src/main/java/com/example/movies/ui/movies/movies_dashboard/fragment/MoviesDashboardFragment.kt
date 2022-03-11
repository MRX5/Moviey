package com.example.movies.ui.movies.movies_dashboard.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.movies.R
import com.example.movies.databinding.FragmentMoviesDashboardBinding
import com.example.movies.listener.OnMovieClickListener
import com.example.movies.ui.movies.adapter.MoviesAdapter
import com.example.movies.ui.movies.movies_dashboard.viewModel.MoviesViewModel
import com.example.movies.utils.DataState
import com.example.movies.utils.LinearSpacingItemDecoration
import com.example.movies.listener.MoviesSeeMoreClickListener
import com.example.movies.ui.movies.movie_details.activity.MovieDetailsActivity
import com.example.movies.ui.movies.adapter.MoviesSliderAdapter
import com.example.movies.utils.Constants
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType
import com.smarteist.autoimageslider.SliderAnimations
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class MoviesDashboardFragment : Fragment() , OnMovieClickListener, MoviesSeeMoreClickListener {
    lateinit var binding: FragmentMoviesDashboardBinding
    private val viewModel:MoviesViewModel by viewModels()
    private val inTheaterAdapter by lazy { MoviesSliderAdapter(this) }
    private val popularAdapter by lazy { MoviesAdapter(requireContext(),this) }
    private val trendingAdapter by lazy { MoviesAdapter(requireContext(),this) }
    private val upcomingAdapter by lazy { MoviesAdapter(requireContext(),this) }
    private val topRatedAdapter by lazy { MoviesAdapter(requireContext(),this) }
    private val page=1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentMoviesDashboardBinding.inflate(inflater, container, false)
        binding.handler=this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupSliderAdapter()
        setupRecyclerViews()

        fetchInTheaterMovies()
        fetchTrendingMovies()
        fetchPopularMovies()
        fetchUpcomingMovies()
        fetchTopRatedMovies()

    }

    private fun setupSliderAdapter() {
        binding.imageSlider.apply {
            setIndicatorAnimation(IndicatorAnimationType.WORM)
            setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION)
            startAutoCycle()
        }
    }

    private fun setupRecyclerViews() {
        binding.trendingRecycler.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            addItemDecoration(LinearSpacingItemDecoration(context, LinearLayoutManager.HORIZONTAL))
            setHasFixedSize(true)
            adapter = trendingAdapter
        }

        binding.popularRecycler.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            addItemDecoration(LinearSpacingItemDecoration(context, LinearLayoutManager.HORIZONTAL))
            setHasFixedSize(true)
            adapter = popularAdapter
        }

        binding.upcomingRecycler.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            addItemDecoration(LinearSpacingItemDecoration(context, LinearLayoutManager.HORIZONTAL))
            setHasFixedSize(true)
            adapter = upcomingAdapter
        }

        binding.topRatedRecycler.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            addItemDecoration(LinearSpacingItemDecoration(context, LinearLayoutManager.HORIZONTAL))
            setHasFixedSize(true)
            adapter = topRatedAdapter
        }
    }

    private fun fetchInTheaterMovies(){
        viewModel.fetchInTheaterMovies(page)

        lifecycleScope.launchWhenCreated {
            viewModel.inTheater.collect {
                when(it){
                    is DataState.Loading->{
                        binding.moviesScroll.visibility= GONE
                        binding.noInternetLayout.visibility=GONE
                        binding.moviesProgressBar.visibility = VISIBLE
                    }
                    is DataState.Success -> {
                        binding.moviesProgressBar.visibility = GONE
                        binding.moviesScroll.visibility= VISIBLE
                        inTheaterAdapter.setData(it.data.results)
                        binding.imageSlider.setSliderAdapter(inTheaterAdapter)
                    }
                    is DataState.Error->{
                        binding.moviesProgressBar.visibility = GONE
                        binding.noInternetLayout.visibility=VISIBLE
                        Toast.makeText(context, it.exception, Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }

    private fun fetchTrendingMovies(){
        viewModel.fetchTrendingMovies(page)

        lifecycleScope.launchWhenCreated {
            viewModel.trending.collect {
                when(it){
                    is DataState.Success -> {
                        binding.moviesProgressBar.visibility = GONE
                        binding.moviesScroll.visibility= VISIBLE
                        trendingAdapter.setData(it.data.results)
                    }
                    is DataState.Error->{
                    }
                }
            }
        }
    }

    private fun fetchPopularMovies(){
        viewModel.fetchPopularMovies(page)

        lifecycleScope.launchWhenCreated {
            viewModel.popular.collect {
                when(it){
                    is DataState.Success -> {
                        binding.moviesProgressBar.visibility = GONE
                        binding.moviesScroll.visibility= VISIBLE
                        popularAdapter.setData(it.data.results)
                    }
                    is DataState.Error->{
                    }
                }
            }
        }
    }

    private fun fetchUpcomingMovies(){
        viewModel.fetchUpcomingMovies(page)

        lifecycleScope.launchWhenCreated {
            viewModel.upcoming.collect {
                when(it){
                    is DataState.Success -> {
                        binding.moviesProgressBar.visibility = GONE
                        binding.moviesScroll.visibility= VISIBLE
                        upcomingAdapter.setData(it.data.results)
                    }
                    is DataState.Error->{
                    }
                }
            }
        }
    }

    private fun fetchTopRatedMovies(){
        viewModel.fetchTopRatedMovies(page)
        lifecycleScope.launchWhenCreated {
            viewModel.topRated.collect {
                when(it){
                    is DataState.Success -> {
                        binding.moviesProgressBar.visibility = GONE
                        binding.moviesScroll.visibility= VISIBLE
                        topRatedAdapter.setData(it.data.results)
                    }
                    is DataState.Error->{
                    }
                }
            }
        }
    }



    override fun openInTheaterFragment() {
        val args= bundleOf(Constants.MediaType to Constants.IN_THEATRES)
        findNavController().navigate(R.id.action_moviesDashboardFragment_to_moviesFragment,args)
    }

    override fun openTrendingFragment() {
        val args= bundleOf(Constants.MediaType to Constants.TRENDING)
        findNavController().navigate(R.id.action_moviesDashboardFragment_to_moviesFragment,args)
    }

    override fun openUpcomingFragment() {
        val args= bundleOf(Constants.MediaType to Constants.UPCOMING)
        findNavController().navigate(R.id.action_moviesDashboardFragment_to_moviesFragment,args)
    }

    override fun openPopularFragment() {
        val args= bundleOf(Constants.MediaType to Constants.POPULAR)
        findNavController().navigate(R.id.action_moviesDashboardFragment_to_moviesFragment,args)
    }

    override fun openTopRatedFragment() {
        val args= bundleOf(Constants.MediaType to Constants.TOP_RATED)
        findNavController().navigate(R.id.action_moviesDashboardFragment_to_moviesFragment,args)
    }

    override fun onMovieClick(movieID: Int) {
        val intent = Intent(context, MovieDetailsActivity::class.java).apply {
            putExtra(Constants.MOVIE_ID, movieID)
        }
        startActivity(intent)
    }


}