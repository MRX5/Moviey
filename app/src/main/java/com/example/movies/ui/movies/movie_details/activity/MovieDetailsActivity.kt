package com.example.movies.ui.movies.movie_details.activity

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Toast
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.movies.R
import com.example.movies.listener.MediaClickListener
import com.example.movies.databinding.ActivityMovieDetailsBinding
import com.example.movies.data.model.response.MovieDetailsResponse
import com.example.movies.adapter.CastsAdapter
import com.example.movies.data.local.entites.MediaEntity
import com.example.movies.ui.movies.movie_details.adapter.RelatedMoviesAdapter
import com.example.movies.ui.movies.movie_details.viewModel.MovieDetailsViewModel
import com.example.movies.utils.Constants
import com.example.movies.utils.DataState
import com.example.movies.utils.LinearSpacingItemDecoration
import com.example.movies.utils.MediaUtils
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

private const val TAG = "MovieDetailsmostafa"

@AndroidEntryPoint
class MovieDetailsActivity : AppCompatActivity(), MediaClickListener {

    private lateinit var binding: ActivityMovieDetailsBinding
    private val viewModel: MovieDetailsViewModel by viewModels()
    private lateinit var castsAdapter: CastsAdapter
    private lateinit var relatedMoviesAdapter: RelatedMoviesAdapter
    private var isFavourite = false
    private var movieID: Int = 0
    private lateinit var movie: MovieDetailsResponse

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_movie_details)
        movieID = intent.getIntExtra(Constants.MOVIE_ID, 0)
        checkFavouriteState()
        setupToolbar()
        setupCastsRecyclerView()
        setupRecommendationsRecyclerView()
        fetchMovieDetails(movieID)
    }

    private fun checkFavouriteState() {
        lifecycleScope.launchWhenCreated {
            viewModel.getFavouriteState(movieID)
            viewModel.isFavourite.collect {
                if (it != null) {
                    isFavourite = true
                }
            }
        }
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
        relatedMoviesAdapter = RelatedMoviesAdapter(this)
        binding.recommendationsRecyclerview.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            setHasFixedSize(true)
            addItemDecoration(LinearSpacingItemDecoration(context, LinearLayoutManager.HORIZONTAL))
            adapter = relatedMoviesAdapter
        }
    }

    private fun fetchMovieDetails(movieID: Int) {
        viewModel.getMovieDetails(movieID)
        lifecycleScope.launchWhenCreated {
            viewModel.movie.collect {
                when (it) {
                    is DataState.Loading -> {
                        binding.detailsActivityScrollView.visibility = GONE
                        binding.noInternetLayout.root.visibility=GONE
                        binding.progressBar.visibility = VISIBLE
                    }
                    is DataState.Success -> {
                        movie=it.data
                        populateViews(it.data)
                    }
                    is DataState.Error -> {
                        binding.progressBar.visibility = GONE
                        binding.noInternetLayout.root.visibility= VISIBLE
                    }
                }
            }
        }
    }

    private fun populateViews(moviesDetails: MovieDetailsResponse) {
        binding.progressBar.visibility = GONE
        binding.detailsActivityScrollView.visibility = VISIBLE
        binding.movie = moviesDetails
        binding.genres = moviesDetails.genres?.let { it -> MediaUtils.extractGenresNames(it) }
        moviesDetails.credits?.let { it -> castsAdapter.setData(it.casts) }
        moviesDetails.recommendations?.let { it -> relatedMoviesAdapter.setData(it.results) }
        val youtubeLink = MediaUtils.extractYoutubeLink(moviesDetails.videos)
        binding.detailsMovieTrailer.setOnClickListener {
            watchTrailer(youtubeLink)
        }
    }

    private fun watchTrailer(youtubeLink: String) {
        binding.detailsMovieTrailer.setOnClickListener {
            val webIntent = Intent(Intent.ACTION_VIEW, Uri.parse(youtubeLink))
            try {
                startActivity(webIntent)
            } catch (ex: ActivityNotFoundException) {
                Toast.makeText(this, ex.message.toString(), Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.favourite_menu, menu)
        if (isFavourite) {
            menu?.findItem(R.id.action_favourite)?.setIcon(R.drawable.icon_favourite_fill)
        }
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_favourite) {
            if (isFavourite) {
                isFavourite = false
                viewModel.removeFromFavourites(movieID)
                item.setIcon(R.drawable.icon_favourite_1)
            } else {
                isFavourite = true
                viewModel.addToFavourites(
                    MediaEntity(
                        movie.id,
                        movie.title,
                        movie.poster,
                        movie.year,
                        movie.vote,
                        Constants.MOVIE
                    )
                )
                item.setIcon(R.drawable.icon_favourite_fill)
            }
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onItemClick(mediaType: String, mediaID: Int) {
        val intent = Intent(this, MovieDetailsActivity::class.java).apply {
            putExtra(Constants.MOVIE_ID, mediaID)
        }
        startActivity(intent)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

}