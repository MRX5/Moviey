package com.example.movies.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import com.example.movies.R
import com.example.movies.adapter.ViewPagerAdapter
import com.example.movies.databinding.ActivityMainBinding
import com.example.movies.utils.Constants
import com.example.movies.utils.MovieConverter
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

}