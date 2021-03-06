package com.example.movies.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.movies.R
import com.example.movies.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity(), NavController.OnDestinationChangedListener {

    lateinit var binding: ActivityMainBinding
    lateinit var appBarConfiguration: AppBarConfiguration
    lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        setupToolbar()
        setupNavigationDrawable()
    }

    private fun setupToolbar() {
        setSupportActionBar(binding.contentMain.mainToolbar)
        supportActionBar?.setDisplayShowTitleEnabled(true)
    }


    private fun setupNavigationDrawable() {
        navController = findNavController(R.id.nav_host_fragment)
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.homeFragment,
                R.id.moviesDashboardFragment,
                R.id.tvShowsFragment,
                R.id.favouritesFragment,
                R.id.searchActivity
            ), binding.drawableLayout
        )
        binding.navigationView.setupWithNavController(navController)
        navController.addOnDestinationChangedListener(this)
        setupActionBarWithNavController(navController, appBarConfiguration)
        binding.navigationView.setupWithNavController(navController)
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_search) {
            navController.navigate(R.id.searchActivity)
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun onDestinationChanged(
        controller: NavController,
        destination: NavDestination,
        arguments: Bundle?
    ) {
        when (destination.id) {
            R.id.splashFragment -> {
                binding.contentMain.mainToolbar.visibility = GONE
            }
            else -> {
                binding.contentMain.mainToolbar.visibility = VISIBLE
            }
        }
    }

    override fun onBackPressed() {
        if (binding.drawableLayout.isDrawerOpen(GravityCompat.START)) {
            binding.drawableLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }
}