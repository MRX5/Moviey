package com.example.movies.ui.tv_details.activity

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
import com.example.movies.adapter.CastsAdapter
import com.example.movies.listener.MediaClickListener
import com.example.movies.databinding.ActivityTvDetailsBinding
import com.example.movies.data.model.response.TvDetailsResponse
import com.example.movies.ui.tv_details.adapter.RelatedTvShowAdapter
import com.example.movies.ui.tv_details.adapter.SeasonsAdapter
import com.example.movies.ui.tv_details.viewModel.TvDetailsViewModel
import com.example.movies.utils.Constants
import com.example.movies.utils.DataState
import com.example.movies.utils.LinearSpacingItemDecoration
import com.example.movies.utils.MediaUtils
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class TvDetailsActivity : AppCompatActivity(), MediaClickListener {
    private val viewModel: TvDetailsViewModel by viewModels()
    private lateinit var binding: ActivityTvDetailsBinding
    private val castsAdapter by lazy { CastsAdapter() }
    private val relatedTvShowsAdapter by lazy { RelatedTvShowAdapter(this) }
    private val seasonsAdapter by lazy { SeasonsAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tv_details)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_tv_details)
        val tvID = intent.getIntExtra(Constants.TV_ID, 0)
        setupToolbar()
        setupCastsRecycler()
        setupRelatedTvRecycler()
        setupSeasonsRecycler()
        fetchTvShowDetails(tvID)
    }

    private fun setupToolbar() {
        setSupportActionBar(binding.myToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)
    }

    private fun setupCastsRecycler() {
        binding.tvCastRecyclerview.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            setHasFixedSize(true)
            adapter = castsAdapter
        }
    }

    private fun setupSeasonsRecycler() {
        binding.seasonsRecyclerview.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            setHasFixedSize(true)
            addItemDecoration(LinearSpacingItemDecoration(context, LinearLayoutManager.HORIZONTAL))
            adapter = seasonsAdapter
        }
    }

    private fun setupRelatedTvRecycler() {
        binding.relatedTvShowRecyclerview.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            setHasFixedSize(true)
            addItemDecoration(LinearSpacingItemDecoration(context, LinearLayoutManager.HORIZONTAL))
            adapter = relatedTvShowsAdapter
        }
    }

    private fun fetchTvShowDetails(tvID: Int) {
        viewModel.getMovieDetails(tvID)
        lifecycleScope.launchWhenCreated {
            viewModel.tvShow.collect {
                when (it) {
                    is DataState.Loading -> {
                        binding.detailsActivityScrollView.visibility = GONE
                        binding.progressBar.visibility = VISIBLE
                    }
                    is DataState.Success -> {
                        binding.progressBar.visibility = GONE
                        binding.detailsActivityScrollView.visibility = VISIBLE
                        populateUi(it.data)
                    }
                    is DataState.Error -> {
                        binding.progressBar.visibility = GONE
                    }
                }
            }
        }
    }

    private fun populateUi(tvShowDetails: TvDetailsResponse) {
        binding.tvShow = tvShowDetails
        binding.genres = tvShowDetails.genres?.let { it -> MediaUtils.extractGenresNames(it) }
        tvShowDetails.credits?.let { castsAdapter.setData(it.casts) }
        tvShowDetails.recommendations?.let { relatedTvShowsAdapter.setData(it.results) }
        tvShowDetails.seasons.let { seasonsAdapter.setData(it) }
        val youtubeLink = MediaUtils.extractYoutubeLink(tvShowDetails.videos)
        binding.detailsTvTrailer.setOnClickListener {
            watchTrailer(youtubeLink)
        }
    }

    private fun watchTrailer(youtubeLink: String) {
        val webIntent = Intent(Intent.ACTION_VIEW, Uri.parse(youtubeLink))
        try {
            startActivity(webIntent)
        } catch (ex: ActivityNotFoundException) {
            Toast.makeText(this, ex.message.toString(), Toast.LENGTH_LONG).show()
        }
    }

    override fun onItemClick(mediaType: String, mediaID: Int) {
        val intent = Intent(this, TvDetailsActivity::class.java).apply {
            putExtra(Constants.TV_ID, mediaID)
        }
        startActivity(intent)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}