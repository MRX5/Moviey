package com.example.movies.ui.details.activity

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Toast
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.movies.R
import com.example.movies.adapter.MovieClickListener
import com.example.movies.databinding.ActivityMovieDetailsBinding
import com.example.movies.model.entity.Movie
import com.example.movies.model.response.MovieDetailsResponse
import com.example.movies.ui.details.adapter.CastsAdapter
import com.example.movies.ui.details.adapter.RecommendationsAdapter
import com.example.movies.ui.details.viewModel.MovieDetailsViewModel
import com.example.movies.utils.Constants
import com.example.movies.utils.DataState
import com.example.movies.utils.LinearSpacingItemDecoration
import com.example.movies.utils.MediaUtils
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

private const val TAG = "MovieDetailsmostafa"

@AndroidEntryPoint
class MovieDetailsActivity : AppCompatActivity(), MovieClickListener {
    lateinit var binding: ActivityMovieDetailsBinding
    private val viewModel: MovieDetailsViewModel by viewModels()
    private lateinit var castsAdapter: CastsAdapter
    private lateinit var recommendationsAdapter: RecommendationsAdapter
    private var youtubeLink = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_movie_details)

        val movieID = intent.getIntExtra(Constants.MOVIE_ID, 0)
        setupToolbar()
        setupCastsRecyclerView()
        setupRecommendationsRecyclerView()
        fetchMovieDetails(movieID)
        watchTrailer()
    }

    private fun setupToolbar() {
        setSupportActionBar(binding.myToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)
    }

    private fun setupCastsRecyclerView() {
        castsAdapter = CastsAdapter()
        binding.detailsCastRecyclerview.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            setHasFixedSize(true)
            adapter = castsAdapter
        }
    }

    private fun setupRecommendationsRecyclerView() {
        recommendationsAdapter = RecommendationsAdapter(this)
        binding.recommendationsRecyclerview.apply {
            layoutManager=LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false)
            setHasFixedSize(true)
            addItemDecoration(LinearSpacingItemDecoration(context,LinearLayoutManager.HORIZONTAL))
            adapter=recommendationsAdapter
        }
    }

    private fun fetchMovieDetails(movieID: Int) {
        viewModel.getMovieDetails(movieID)
        lifecycleScope.launchWhenCreated {
            viewModel.movie.collect {
                when (it) {
                    is DataState.Loading -> {
                        binding.detailsActivityScrollView.visibility=GONE
                        binding.progressBar.visibility= VISIBLE
                    }
                    is DataState.Success -> {
                        populateViews(it.data)
                    }
                    is DataState.Error -> {
                        binding.progressBar.visibility = GONE
                        Toast.makeText(this@MovieDetailsActivity, it.exception, Toast.LENGTH_LONG).show()
                    }
                    else -> {
                    }
                }
            }
        }
    }
    private fun populateViews(moviesDetails: MovieDetailsResponse){
        binding.progressBar.visibility = GONE
        binding.detailsActivityScrollView.visibility = VISIBLE
        binding.movie = moviesDetails
        binding.genres = moviesDetails.genres?.let { it -> MediaUtils.extractGenresNames(it) }
        moviesDetails.credits?.let { it -> castsAdapter.setData(it.casts) }
        moviesDetails.recommendations?.let { it->recommendationsAdapter.setData(it.results) }
        youtubeLink = MediaUtils.extractYoutubeLink(moviesDetails.videos)
    }

    private fun watchTrailer() {
        binding.detailsMovieTrailer.setOnClickListener {
            val webIntent = Intent(Intent.ACTION_VIEW, Uri.parse(youtubeLink))
            try {
                startActivity(webIntent)
            } catch (ex: ActivityNotFoundException) {
                Toast.makeText(this, ex.message.toString(), Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onMovieClick(movie: Movie) {
        val intent=Intent(this, MovieDetailsActivity::class.java).apply {
            putExtra(Constants.MOVIE_ID,movie.id)
        }
        startActivity(intent)
    }


}