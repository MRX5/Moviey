package com.example.movies.ui.movies.framgent

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
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.example.movies.adapter.InfiniteScrollListener
import com.example.movies.adapter.MediaClickListener
import com.example.movies.ui.movies.adapter.MoviesAdapter
import com.example.movies.databinding.FragmentMoviesBinding
import com.example.movies.ui.movie_details.activity.MovieDetailsActivity
import com.example.movies.ui.movies.viewModel.MoviesViewModel
import com.example.movies.utils.Constants
import com.example.movies.utils.DataState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


private const val ARG_PARAM1 = "param1"

@AndroidEntryPoint
class MoviesFragment : Fragment(), MediaClickListener {
    private var tabName: String? = null
    private val viewModel: MoviesViewModel by viewModels()
    lateinit var binding: FragmentMoviesBinding
    var page: Int = 1
    var totalPages: Int = 0

    private val moviesAdapter: MoviesAdapter by lazy {
        MoviesAdapter(requireContext(), this)
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
        when (tabName) {
            Constants.POPULAR -> viewModel.fetchPopularMovies(page)
            Constants.UPCOMING -> viewModel.fetchUpcomingMovies(page)
            Constants.TOP_RATED -> viewModel.fetchTopRatedMovies(page)
        }

        lifecycleScope.launch {
            viewModel.movies.collect{
                when(it){
                    is DataState.Loading->{
                        if (totalPages == 0) binding.moviesProgressBar.visibility = VISIBLE
                    }
                    is DataState.Success->{
                        binding.moviesProgressBar.visibility = GONE
                        totalPages = it.data.total_pages
                        moviesAdapter.setData(it.data.results)
                    }
                    is DataState.Error->{
                        binding.moviesProgressBar.visibility = GONE
                        Toast.makeText(context, it.exception, Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
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

    override fun onItemClick(mediaType:String,mediaID: Int) {
        val intent = Intent(context, MovieDetailsActivity::class.java).apply {
            putExtra(Constants.MOVIE_ID, mediaID)
        }
        startActivity(intent)
    }
}