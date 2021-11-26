package com.example.movies.ui.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.databinding.DataBindingUtil
import com.example.movies.R
import com.example.movies.adapter.ViewPagerAdapter
import com.example.movies.databinding.ActivityMainBinding
import com.example.movies.ui.search.activity.SearchActivity
import com.example.movies.utils.Constants
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    lateinit var actionBarDrawerToggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        setupToolbar()
        setupTabsWithViewPager()
        setupNavigationDrawable()
    }

    private fun setupToolbar() {
        setSupportActionBar(binding.appBar.mainToolbar)
        supportActionBar?.setDisplayShowTitleEnabled(true)
    }

    private fun setupTabsWithViewPager() {
        binding.moviesViewpager.adapter = ViewPagerAdapter(supportFragmentManager, lifecycle)
        TabLayoutMediator(
            binding.appBar.moviesTablayout,
            binding.moviesViewpager
        ) { tab, position ->
            tab.text = when (position) {
                0 -> Constants.POPULAR
                1 -> Constants.UPCOMING
                2 -> Constants.TOP_RATED
                else -> "Unknown"
            }
        }.attach()
    }

    private fun setupNavigationDrawable() {
        actionBarDrawerToggle = ActionBarDrawerToggle(
            this,
            binding.drawableLayout,
            binding.appBar.mainToolbar,
            R.string.nav_open,
            R.string.nav_close
        )
        actionBarDrawerToggle.syncState()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId==R.id.action_search){
            launchSearchActivity()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun launchSearchActivity() {
        val intent= Intent(this, SearchActivity::class.java)
        startActivity(intent)
    }
}