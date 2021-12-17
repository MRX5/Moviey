package com.example.movies.ui.tvShows.tvShowsDashboard.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.movies.R
import com.example.movies.databinding.FragmentTvShowsDashboardBinding
import com.example.movies.listener.OnTvShowClickListener
import com.example.movies.listener.TvShowsSeeMoreClickListener
import com.example.movies.ui.tvShows.adapter.TvShowsAdapter
import com.example.movies.ui.tvShows.adapter.TvShowsSliderAdapter
import com.example.movies.ui.tvShows.tvShowsDashboard.viewModel.TvShowsDashboardViewModel
import com.example.movies.ui.tv_details.activity.TvDetailsActivity
import com.example.movies.utils.Constants
import com.example.movies.utils.DataState
import com.example.movies.utils.LinearSpacingItemDecoration
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType
import com.smarteist.autoimageslider.SliderAnimations
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class TvShowsDashboardFragment : Fragment() ,TvShowsSeeMoreClickListener,OnTvShowClickListener{
    lateinit var binding:FragmentTvShowsDashboardBinding
    private val dashboardViewModel: TvShowsDashboardViewModel by viewModels()
    private val trendingAdapter by lazy { TvShowsSliderAdapter(this) }
    private val popularAdapter by lazy { TvShowsAdapter(requireContext(),this) }
    private val onTheAirAdapter by lazy { TvShowsAdapter(requireContext(),this) }
    private val topRatedAdapter by lazy { TvShowsAdapter(requireContext(),this) }
    private val page=1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentTvShowsDashboardBinding.inflate(inflater, container, false)
        binding.handler=this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupSliderAdapter()
        setupRecyclerViews()

        fetchPopularTvShows()
        fetchTrendingTvShows()
        fetchOnTheAirTvShows()
        fetchTopRatedTvShows()
    }

    private fun setupSliderAdapter() {
        binding.imageSlider.apply {
            setIndicatorAnimation(IndicatorAnimationType.WORM)
            setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION)
            startAutoCycle()
        }
    }

    private fun setupRecyclerViews() {
        binding.popularRecycler.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            addItemDecoration(LinearSpacingItemDecoration(context, LinearLayoutManager.HORIZONTAL))
            setHasFixedSize(true)
            adapter = popularAdapter
        }

        binding.onTheAirRecycler.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            addItemDecoration(LinearSpacingItemDecoration(context, LinearLayoutManager.HORIZONTAL))
            setHasFixedSize(true)
            adapter = onTheAirAdapter
        }

        binding.topRatedRecycler.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            addItemDecoration(LinearSpacingItemDecoration(context, LinearLayoutManager.HORIZONTAL))
            setHasFixedSize(true)
            adapter = topRatedAdapter
        }
    }

    private fun fetchTrendingTvShows(){
        dashboardViewModel.fetchTrendingTvShows(page)
        lifecycleScope.launchWhenCreated {
            dashboardViewModel.trending.collect {
                when(it){
                    is DataState.Loading->{
                        binding.moviesScroll.visibility= View.GONE
                        binding.moviesProgressBar.visibility = View.VISIBLE
                    }
                    is DataState.Success -> {
                        binding.moviesProgressBar.visibility = View.GONE
                        binding.moviesScroll.visibility= View.VISIBLE
                        trendingAdapter.setData(it.data.results)
                        binding.imageSlider.setSliderAdapter(trendingAdapter)
                    }
                    is DataState.Error->{ }
                }
            }
        }
    }

    private fun fetchPopularTvShows(){
        dashboardViewModel.fetchPopularTvShows(page)
        lifecycleScope.launchWhenCreated {
            dashboardViewModel.popular.collect {
                when(it){

                    is DataState.Success -> {
                        binding.moviesProgressBar.visibility = View.GONE
                        binding.moviesScroll.visibility= View.VISIBLE
                        popularAdapter.setData(it.data.results)
                    }
                    is DataState.Error->{}
                }
            }
        }
    }

    private fun fetchOnTheAirTvShows(){
        dashboardViewModel.fetchOnTheAirTvShows(page)
        lifecycleScope.launchWhenCreated {
            dashboardViewModel.onTheAir.collect {
                when(it){
                    is DataState.Success -> {
                        binding.moviesProgressBar.visibility = View.GONE
                        binding.moviesScroll.visibility= View.VISIBLE
                        onTheAirAdapter.setData(it.data.results)
                    }
                    is DataState.Error->{ }
                }
            }
        }
    }

    private fun fetchTopRatedTvShows(){
        dashboardViewModel.fetchTopRatedTvShows(page)
        lifecycleScope.launchWhenCreated {
            dashboardViewModel.topRated.collect {
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

    override fun openTrendingFragment() {
        val args= bundleOf(Constants.MediaType to Constants.TRENDING)
        findNavController().navigate(R.id.action_tvShowsFragment_to_tvShowsContentFragment,args)
    }
    override fun openOnTheAirFragment() {
        val args= bundleOf(Constants.MediaType to Constants.ON_THE_AIR)
        findNavController().navigate(R.id.action_tvShowsFragment_to_tvShowsContentFragment,args)
    }

    override fun openPopularFragment() {
        val args= bundleOf(Constants.MediaType to Constants.POPULAR)
        findNavController().navigate(R.id.action_tvShowsFragment_to_tvShowsContentFragment,args)
    }

    override fun openTopRatedFragment() {
        val args= bundleOf(Constants.MediaType to Constants.TOP_RATED)
        findNavController().navigate(R.id.action_tvShowsFragment_to_tvShowsContentFragment,args)
    }

    override fun onTvShowClick(tvShowID: Int) {
        val intent= Intent(context, TvDetailsActivity::class.java).apply {
            putExtra(Constants.TV_ID,tvShowID)
        }
        startActivity(intent)
    }
}