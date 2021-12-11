package com.example.movies.ui.movies.trending.fragment

import GridSpacingItemDecoration
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.example.movies.R
import com.example.movies.adapter.InfiniteScrollListener
import com.example.movies.adapter.MediaClickListener
import com.example.movies.databinding.FragmentTrendingMoviesBinding
import com.example.movies.ui.movie_details.activity.MovieDetailsActivity
import com.example.movies.ui.movies.adapter.MoviesAdapter
import com.example.movies.ui.movies.trending.viewModel.TrendingViewModel
import com.example.movies.utils.Constants
import com.example.movies.utils.DataState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class TrendingMoviesFragment : Fragment(),MediaClickListener {

    lateinit var binding:FragmentTrendingMoviesBinding
    private val viewModel:TrendingViewModel by viewModels()
    private val moviesAdapter by lazy { MoviesAdapter(requireContext(),this) }
    private var page: Int = 1
    private var totalPages: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentTrendingMoviesBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        fetchMovies()
    }

    private fun setupRecyclerView() {
        val listener = InfiniteScrollListener({
            page++
            if (page <= totalPages) {
                fetchMovies()
            }
        }, false)

        binding.moviesRecyclerview.apply {
            layoutManager = GridLayoutManager(context, 3)
            addItemDecoration(GridSpacingItemDecoration(16))
            setHasFixedSize(true)
            adapter = moviesAdapter
            addOnScrollListener(listener)
        }
    }

    private fun fetchMovies() {
        viewModel.fetchTrendingMovies(page)
        lifecycleScope.launch {
            viewModel.movies.collect {
                when(it){
                    is DataState.Loading->{
                        if (totalPages == 0) binding.moviesProgressBar.visibility = View.VISIBLE
                    }
                    is DataState.Success->{
                        binding.moviesProgressBar.visibility = View.GONE
                        totalPages = it.data.total_pages
                        moviesAdapter.setData(it.data.results)
                    }
                    is DataState.Error->{
                        binding.moviesProgressBar.visibility = View.GONE
                        Toast.makeText(context, it.exception, Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }

    override fun onItemClick(mediaType: String, mediaID: Int) {
        val intent= Intent(context, MovieDetailsActivity::class.java).apply {
            putExtra(Constants.MOVIE_ID,mediaID)
        }
        startActivity(intent)
    }
}