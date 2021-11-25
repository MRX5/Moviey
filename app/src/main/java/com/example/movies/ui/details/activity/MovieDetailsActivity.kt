package com.example.movies.ui.details.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.movies.R
import com.example.movies.databinding.ActivityMovieDetailsBinding
import com.example.movies.ui.details.adapter.CastsAdapter
import com.example.movies.ui.details.viewModel.MovieDetailsViewModel
import com.example.movies.utils.Constants
import com.example.movies.utils.DataState
import com.example.movies.utils.MovieConverter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

private const val TAG = "MovieDetailsmostafa"
@AndroidEntryPoint
class MovieDetailsActivity : AppCompatActivity() {
    lateinit var binding: ActivityMovieDetailsBinding
    private val viewModel:MovieDetailsViewModel by viewModels()
    private lateinit var castsAdapter: CastsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=DataBindingUtil.setContentView(this,R.layout.activity_movie_details)

        val movieID= intent.getIntExtra(Constants.MOVIE_ID,0)
        setupToolbar()
        setupRecyclerView()
        fetchMovieDetails(movieID)
    }

    private fun setupRecyclerView() {
        castsAdapter= CastsAdapter()
        binding.detailsCastRecyclerview.apply {
            layoutManager=LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false)
            setHasFixedSize(true)
            adapter=castsAdapter
        }
    }

    private fun fetchMovieDetails(movieID: Int) {
        viewModel.getMovieDetails(movieID)
        lifecycleScope.launchWhenCreated {
            viewModel.movie.collect {
                when(it){
                    is DataState.Loading -> {
                        Log.d(TAG, "loading")
                    }
                    is DataState.Success -> {
                        binding.movie = it.data
                        binding.genres = MovieConverter.extractGenresNames(it.data.genres)
                        castsAdapter.setData(it.data.credits.casts)
                    }
                    is DataState.Error->{
                        Toast.makeText(this@MovieDetailsActivity,it.exception,Toast.LENGTH_LONG).show()
                    }
                    else->{}
                }
            }
        }
    }
    private fun setupToolbar() {
        setSupportActionBar(binding.myToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)
    }
}