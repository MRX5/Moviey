package com.example.movies.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        setupToolbar()
    }

    private fun setupToolbar() {
        setSupportActionBar(binding.appBar.mainToolbar)
        supportActionBar?.setDisplayShowTitleEnabled(true)
        binding.moviesViewpager.adapter = ViewPagerAdapter(supportFragmentManager, lifecycle)
        TabLayoutMediator(binding.appBar.moviesTablayout, binding.moviesViewpager) { tab, position ->
            tab.text = when (position) {
                0 -> Constants.POPULAR
                1 -> Constants.UPCOMING
                2 -> Constants.TOP_RATED
                else -> "Unknown"
            }
        }.attach()
    }


}