package com.example.movies.ui.tvShows.tvShows.fragment

import GridSpacingItemDecoration
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.example.movies.adapter.InfiniteScrollListener
import com.example.movies.databinding.FragmentTvShowsBinding
import com.example.movies.listener.OnTvShowClickListener
import com.example.movies.ui.tvShows.adapter.TvShowsAdapter
import com.example.movies.ui.tvShows.tvShows.viewModel.TvShowsViewModel
import com.example.movies.ui.tvShows.tv_details.activity.TvDetailsActivity
import com.example.movies.utils.Constants
import com.example.movies.utils.DataState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect


@AndroidEntryPoint
class TvShowsFragment : Fragment(),OnTvShowClickListener {
    lateinit var binding: FragmentTvShowsBinding
    private val viewModel:TvShowsViewModel by viewModels()
    private var tvShowType: String? = null
    private val tvShowsAdapter by lazy { TvShowsAdapter(requireContext(),this) }
    private var page=1
    private var totalPages=0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            tvShowType = it.getString(Constants.MediaType)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentTvShowsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        fetchData()
        observe()
    }
    private fun setupRecyclerView() {
        val listener = InfiniteScrollListener({
            page++
            if (page <= totalPages) {
                fetchData()
            }
        }, false)

        binding.TvShowsContent.recyclerView.apply {
            layoutManager = GridLayoutManager(context, 3)
            addItemDecoration(GridSpacingItemDecoration(16))
            setHasFixedSize(true)
            adapter = tvShowsAdapter
            addOnScrollListener(listener)
        }
    }

    private fun fetchData() {
        when(tvShowType){
            Constants.TRENDING -> {
                (requireActivity() as AppCompatActivity).supportActionBar?.title=Constants.TRENDING
                viewModel.fetchTrendingTvShows(page)
            }
            Constants.POPULAR->{
                (requireActivity() as AppCompatActivity).supportActionBar?.title=Constants.POPULAR
                viewModel.fetchPopularTvShows(page)
            }
            Constants.ON_THE_AIR->{
                (requireActivity() as AppCompatActivity).supportActionBar?.title=Constants.ON_THE_AIR
                viewModel.fetchOnTheAirTvShows(page)
            }
            Constants.TOP_RATED->{
                (requireActivity() as AppCompatActivity).supportActionBar?.title=Constants.TOP_RATED
                viewModel.fetchTopRatedTvShows(page)
            }
        }
    }

    private fun observe() {
        lifecycleScope.launchWhenCreated {
            viewModel.tvShows.collect {
                when(it){
                    is DataState.Loading->{
                        if (totalPages == 0) binding.TvShowsContent.progressBar.visibility = VISIBLE
                        binding.TvShowsContent.noInternetLayout.visibility=GONE
                    }
                    is DataState.Success->{
                        binding.TvShowsContent.progressBar.visibility = GONE
                        binding.TvShowsContent.noInternetLayout.visibility=GONE
                        totalPages = it.data.total_pages
                        tvShowsAdapter.setData(it.data.results)
                    }
                    is DataState.Error->{
                        binding.TvShowsContent.progressBar.visibility = GONE
                        binding.TvShowsContent.noInternetLayout.visibility= VISIBLE
                    }
                }
            }
        }
    }

    override fun onTvShowClick(tvShowID: Int) {
        val intent= Intent(context,TvDetailsActivity::class.java).apply {
            putExtra(Constants.TV_ID,tvShowID)
        }
        startActivity(intent)
    }

}