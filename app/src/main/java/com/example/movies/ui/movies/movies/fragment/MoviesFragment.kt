package com.example.movies.ui.movies.movies.fragment

import GridSpacingItemDecoration
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.example.movies.adapter.InfiniteScrollListener
import com.example.movies.databinding.FragmentMoviesBinding
import com.example.movies.listener.OnMovieClickListener
import com.example.movies.ui.movies.movie_details.activity.MovieDetailsActivity
import com.example.movies.ui.movies.adapter.MoviesAdapter
import com.example.movies.ui.movies.movies.viewModel.MoviesViewModel
import com.example.movies.utils.Constants
import com.example.movies.utils.DataState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

private const val TAG = "PopularMoviesFragmentmostafa"

@AndroidEntryPoint
class MoviesFragment : Fragment(), OnMovieClickListener {
    lateinit var binding: FragmentMoviesBinding
    private val viewModel: MoviesViewModel by viewModels()
    private var moviesType: String? = null
    private val moviesAdapter by lazy { MoviesAdapter(requireContext(), this) }
    private var page: Int = 1
    private var totalPages: Int = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            moviesType = it.getString(Constants.MediaType)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMoviesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        fetchMovies()
        observe()
    }

    private fun setupRecyclerView() {
        val listener = InfiniteScrollListener({
            page++
            if (page <= totalPages) {
                fetchMovies()
            }
        }, false)

        binding.MoviesContent.recyclerView.apply {
            layoutManager = GridLayoutManager(context, 3)
            addItemDecoration(GridSpacingItemDecoration(16))
            setHasFixedSize(true)
            adapter = moviesAdapter
            addOnScrollListener(listener)
        }
    }


    private fun fetchMovies() {
        when (moviesType) {
            Constants.IN_THEATRES -> {
                (requireActivity() as AppCompatActivity).supportActionBar?.title = Constants.IN_THEATRES
                viewModel.fetchInTheatresMovies(page)
            }
            Constants.TRENDING -> {
                (requireActivity() as AppCompatActivity).supportActionBar?.title = Constants.TRENDING
                viewModel.fetchTrendingMovies(page)
            }
            Constants.POPULAR -> {
                (requireActivity() as AppCompatActivity).supportActionBar?.title = Constants.POPULAR
                viewModel.fetchPopularMovies(page)
            }
            Constants.UPCOMING -> {
                (requireActivity() as AppCompatActivity).supportActionBar?.title = Constants.UPCOMING
                viewModel.fetchUpcomingMovies(page)
            }
            Constants.TOP_RATED -> {
                (requireActivity() as AppCompatActivity).supportActionBar?.title = Constants.TOP_RATED
                viewModel.fetchTopRatedMovies(page)
            }
        }
    }

    private fun observe() {
        lifecycleScope.launch {
            viewModel.movies.collect {
                when (it) {
                    is DataState.Loading -> {
                        binding.MoviesContent.noInternetLayout.visibility = GONE
                        if (totalPages == 0) binding.MoviesContent.progressBar.visibility = VISIBLE
                    }
                    is DataState.Success -> {
                        binding.MoviesContent.progressBar.visibility = GONE
                        totalPages = it.data.total_pages
                        moviesAdapter.setData(it.data.results)
                    }
                    is DataState.Error -> {
                        binding.MoviesContent.progressBar.visibility = GONE
                        binding.MoviesContent.noInternetLayout.visibility = VISIBLE
                    }
                }
            }
        }
    }

    override fun onMovieClick(movieID: Int) {
        val intent = Intent(context, MovieDetailsActivity::class.java).apply {
            putExtra(Constants.MOVIE_ID, movieID)
        }
        startActivity(intent)
    }
}