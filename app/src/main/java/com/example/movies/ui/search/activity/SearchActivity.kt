package com.example.movies.ui.search.activity

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
import com.example.movies.databinding.ActivitySearchBinding
import com.example.movies.ui.search.adapter.SearchAdapter
import com.example.movies.ui.search.viewModel.SearchViewModel
import com.example.movies.utils.DataState
import com.example.movies.utils.LinearSpacingItemDecoration
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.flow.collect

private const val TAG = "SearchActivitymostafa"
@AndroidEntryPoint
class SearchActivity : AppCompatActivity() {
    lateinit var binding: ActivitySearchBinding
    private val viewModel:SearchViewModel by viewModels()
    private lateinit var searchAdapter:SearchAdapter
    private var page=1
    private var totalPages=0
    private var queryString=""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_search)

        setupToolbar()
        setupRecyclerView()
        setupViewModel()
    }

    private fun setupRecyclerView() {

        val listener = InfiniteScrollListener({
            page++
            if (page <= totalPages) {
                viewModel.search(queryString, page)
            }
        }, true)

        searchAdapter= SearchAdapter()
        binding.searchRecyclerview.apply {
            layoutManager=LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)
            setHasFixedSize(true)
            addItemDecoration(LinearSpacingItemDecoration(context,LinearLayoutManager.VERTICAL))
            adapter=searchAdapter
            addOnScrollListener(listener)
        }
    }

    private fun setupViewModel() {
        lifecycleScope.launchWhenCreated {
            viewModel.media.collect {
                when(it){
                    is DataState.Loading -> {
                        binding.progressBar.visibility=VISIBLE
                    }

                    is DataState.Success->{
                        binding.progressBar.visibility=GONE
                        searchAdapter.setData(it.data.results)
                        totalPages=it.data.total_pages
                    }

                    is DataState.Error->{
                        binding.progressBar.visibility=GONE
                    }
                    else -> {}
                }
            }
        }
    }

    private fun setupToolbar() {
        setSupportActionBar(binding.searchToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.search_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_search) {
            val searchView = item.actionView as SearchView
            searchView.setOnQueryTextListener(queryListener())
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun queryListener()=object:SearchView.OnQueryTextListener {
        override fun onQueryTextSubmit(query: String?): Boolean {
            return true
        }

        override fun onQueryTextChange(query: String?): Boolean {
            lifecycleScope.launchWhenCreated {
                query?.run {
                    if(query.isNotEmpty()) {
                        searchAdapter.clearData()
                        viewModel.search(query,page)
                        queryString=query
                    }
                }
            }
            return true
        }
    }
}