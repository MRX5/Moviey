package com.example.movies.ui.movies.movies_dashboard.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.movies.R
import com.example.movies.adapter.MediaClickListener
import com.example.movies.databinding.FragmentMoviesBinding
import com.example.movies.ui.home.adapter.SliderAdapter
import com.example.movies.ui.movies.adapter.MoviesAdapter
import com.example.movies.ui.movies.movies_dashboard.viewModel.MoviesViewModel
import com.example.movies.utils.DataState
import com.example.movies.utils.LinearSpacingItemDecoration
import com.example.movies.utils.SeeMoreClickListener
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType
import com.smarteist.autoimageslider.SliderAnimations
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class MoviesFragment : Fragment() , MediaClickListener,SeeMoreClickListener{
    lateinit var binding:FragmentMoviesBinding
    private val viewModel:MoviesViewModel by viewModels()
    private val inTheaterAdapter by lazy { SliderAdapter() }
    private val popularAdapter by lazy { MoviesAdapter(requireContext(),this) }
    private val trendingAdapter by lazy { MoviesAdapter(requireContext(),this) }
    private val upcomingAdapter by lazy { MoviesAdapter(requireContext(),this) }
    private val topRatedAdapter by lazy { MoviesAdapter(requireContext(),this) }
    private val page=1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentMoviesBinding.inflate(inflater, container, false)
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
                        binding.moviesScroll.visibility= View.GONE
                        binding.moviesProgressBar.visibility = View.VISIBLE
                    }
                    is DataState.Success -> {
                        binding.moviesProgressBar.visibility = View.GONE
                        binding.moviesScroll.visibility= View.VISIBLE
                        inTheaterAdapter.setData(it.data.results)
                        binding.imageSlider.setSliderAdapter(inTheaterAdapter)
                    }
                    is DataState.Error->{
                        binding.moviesProgressBar.visibility = View.GONE
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
                        binding.moviesProgressBar.visibility = View.GONE
                        binding.moviesScroll.visibility= View.VISIBLE
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
                        binding.moviesProgressBar.visibility = View.GONE
                        binding.moviesScroll.visibility= View.VISIBLE
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
                        binding.moviesProgressBar.visibility = View.GONE
                        binding.moviesScroll.visibility= View.VISIBLE
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
                        binding.moviesProgressBar.visibility = View.GONE
                        binding.moviesScroll.visibility= View.VISIBLE
                        topRatedAdapter.setData(it.data.results)
                    }
                    is DataState.Error->{
                    }
                }
            }
        }
    }
    override fun onItemClick(mediaType: String, mediaID: Int) {
        
    }

    override fun openInTheaterFragment() {
        findNavController().navigate(R.id.action_moviesFragment_to_inTheaterMoviesFragment)
    }

    override fun openTrendingFragment() {
        findNavController().navigate(R.id.action_moviesFragment_to_trendingMoviesFragment)
    }

    override fun openUpcomingFragment() {
        findNavController().navigate(R.id.action_moviesFragment_to_upcomingMoviesFragment)
    }

    override fun openPopularFragment() {
        findNavController().navigate(R.id.action_moviesFragment_to_popularMoviesFragment)
    }

    override fun openTopRatedFragment() {
        findNavController().navigate(R.id.action_moviesFragment_to_topRatedMoviesFragment)

    }


}