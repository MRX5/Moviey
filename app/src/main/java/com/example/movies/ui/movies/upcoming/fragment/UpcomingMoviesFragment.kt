package com.example.movies.ui.movies.upcoming.fragment

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
import com.example.movies.adapter.InfiniteScrollListener
import com.example.movies.databinding.FragmentUpcomingMoviesBinding
import com.example.movies.listener.OnMovieClickListener
import com.example.movies.ui.movies.movie_details.activity.MovieDetailsActivity
import com.example.movies.ui.movies.adapter.MoviesAdapter
import com.example.movies.ui.movies.upcoming.viewModel.UpcomingViewModel
import com.example.movies.utils.Constants
import com.example.movies.utils.DataState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class UpcomingMoviesFragment : Fragment(), OnMovieClickListener {
     lateinit var binding: FragmentUpcomingMoviesBinding
    private val viewModel: UpcomingViewModel by viewModels()
    private val moviesAdapter by lazy { MoviesAdapter(requireContext(), this) }
    private var page: Int = 1
    private var totalPages: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentUpcomingMoviesBinding.inflate(inflater, container, false)
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
        viewModel.fetchUpcomingMovies(page)

        lifecycleScope.launch {
            viewModel.movies.collect {
                when(it){
                    is DataState.Loading->{
                        binding.noInternetLayout.visibility = View.GONE
                        if (totalPages == 0) binding.moviesProgressBar.visibility = View.VISIBLE
                    }
                    is DataState.Success->{
                        binding.moviesProgressBar.visibility = View.GONE
                        totalPages = it.data.total_pages
                        moviesAdapter.setData(it.data.results)
                    }
                    is DataState.Error->{
                        binding.moviesProgressBar.visibility = View.GONE
                        binding.noInternetLayout.visibility = View.VISIBLE
                    }
                }
            }
        }
    }

    override fun onMovieClick(movieID: Int) {
        val intent=Intent(context,MovieDetailsActivity::class.java).apply {
            putExtra(Constants.MOVIE_ID,movieID)
        }
        startActivity(intent)
    }
}