package com.example.movies.ui.movies.framgent

import GridSpacingItemDecoration
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.databinding.BindingAdapter
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.example.movies.R
import com.example.movies.adapter.InfiniteScrollListener
import com.example.movies.adapter.MovieClickListener
import com.example.movies.adapter.MoviesAdapter
import com.example.movies.databinding.FragmentMoviesBinding
import com.example.movies.model.Movie
import com.example.movies.ui.details.MovieDetailsActivity
import com.example.movies.ui.movies.viewModel.MoviesViewModel
import com.example.movies.utils.Constants
import com.example.movies.utils.MovieConverter
import com.example.movies.utils.Status
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


private const val ARG_PARAM1 = "param1"

@AndroidEntryPoint
class MoviesFragment : Fragment(),MovieClickListener {
    private var tabName: String? = null
    private val viewModel: MoviesViewModel by viewModels()
    lateinit var binding: FragmentMoviesBinding
    var page: Int = 1
    var totalPages: Int = 0
    private val moviesAdapter: MoviesAdapter by lazy {
        MoviesAdapter(requireContext(),this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            tabName = it.getString(ARG_PARAM1)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMoviesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        fetchMovies()
    }

    private fun setupRecyclerView() {
        val listener = InfiniteScrollListener {
            page++
            if (page <= totalPages) {
                fetchMovies()
            }
        }
        binding.moviesRecyclerview.apply {
            layoutManager = GridLayoutManager(context, 3)
            addItemDecoration(GridSpacingItemDecoration(16))
            setHasFixedSize(true)
            adapter = moviesAdapter
            addOnScrollListener(listener)
        }
    }

    private fun fetchMovies() {
        when (tabName) {
            Constants.POPULAR -> viewModel.fetchPopularMovies(page)
            Constants.UPCOMING -> viewModel.fetchUpcomingMovies(page)
            Constants.TOP_RATED -> viewModel.fetchTopRatedMovies(page)
        }
        viewModel.movies.observe(viewLifecycleOwner, {
            when (it.status) {
                Status.LOADING -> {
                    Toast.makeText(context, "Loading", Toast.LENGTH_SHORT).show()
                }
                Status.SUCCESS -> {
                    it.data?.let { response ->
                        totalPages = response.total_pages
                        moviesAdapter.setData(response.results)
                    }
                }
                Status.ERROR -> {
                    Toast.makeText(context, it.message, Toast.LENGTH_LONG).show()
                }
            }
        })
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String) =
            MoviesFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                }
            }
    }

    override fun onMovieClick(movie: Movie) {
        val intent=Intent(context,MovieDetailsActivity::class.java).apply {
            putExtra(Constants.MOVIE_ID,movie.id)
        }
        startActivity(intent)
    }
}