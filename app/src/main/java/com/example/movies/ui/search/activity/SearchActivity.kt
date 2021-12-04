package com.example.movies.ui.search.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.activity.viewModels
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.movies.R
import com.example.movies.adapter.InfiniteScrollListener
import com.example.movies.adapter.MediaClickListener
import com.example.movies.databinding.ActivitySearchBinding
import com.example.movies.ui.movie_details.activity.MovieDetailsActivity
import com.example.movies.ui.search.adapter.SearchAdapter
import com.example.movies.ui.search.viewModel.SearchViewModel
import com.example.movies.ui.tv_details.activity.TvDetailsActivity
import com.example.movies.utils.Constants
import com.example.movies.utils.DataState
import com.example.movies.utils.LinearSpacingItemDecoration
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class SearchActivity : AppCompatActivity(), MediaClickListener {

    lateinit var binding: ActivitySearchBinding
    private val viewModel: SearchViewModel by viewModels()
    private val searchAdapter by lazy { SearchAdapter(this) }
    private var page = 1
    private var totalPages = 0
    private var queryString = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_search)

        setupToolbar()
        setupRecyclerView()
        setupViewModel()
    }

    private fun setupToolbar() {
        setSupportActionBar(binding.searchToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun setupRecyclerView() {

        val listener = InfiniteScrollListener({
            page++
            if (page <= totalPages) {
                viewModel.search(queryString, page)
            }
        }, true)

        binding.searchRecyclerview.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            setHasFixedSize(true)
            addItemDecoration(LinearSpacingItemDecoration(context, LinearLayoutManager.VERTICAL))
            adapter = searchAdapter
            addOnScrollListener(listener)
        }
    }

    private fun setupViewModel() {
        lifecycleScope.launchWhenCreated {
            viewModel.media.collect {
                when (it) {
                    is DataState.Loading -> {
                        binding.progressBar.visibility = VISIBLE
                    }

                    is DataState.Success -> {
                        binding.progressBar.visibility = GONE
                        searchAdapter.setData(it.data.results)
                        totalPages = it.data.total_pages
                    }

                    is DataState.Error -> {
                        binding.progressBar.visibility = GONE
                    }
                    else -> {
                    }
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.search_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_search) {
            val searchView = item.actionView as SearchView
            searchView.setOnQueryTextListener(queryListener())
            searchView.setIconifiedByDefault(false)
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun queryListener() = object : SearchView.OnQueryTextListener {
        override fun onQueryTextSubmit(query: String?): Boolean {
            return true
        }

        override fun onQueryTextChange(query: String?): Boolean {
            lifecycleScope.launchWhenCreated {
                query?.run {
                    if (query.isNotEmpty()) {
                        searchAdapter.clearData()
                        viewModel.search(query, page)
                        queryString = query
                    }
                }
            }
            return true
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onItemClick(mediaType: String, mediaID: Int) {
        val intent: Intent
        if (mediaType == Constants.MOVIE) {
            intent = Intent(this, MovieDetailsActivity::class.java).apply {
                putExtra(Constants.MOVIE_ID, mediaID)
            }
        } else {
            intent = Intent(this, TvDetailsActivity::class.java).apply {
                putExtra(Constants.TV_ID, mediaID)
            }
        }
        startActivity(intent)
    }
}