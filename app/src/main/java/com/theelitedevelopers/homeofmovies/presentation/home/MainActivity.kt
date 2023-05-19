package com.theelitedevelopers.homeofmovies.presentation.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.theelitedevelopers.homeofmovies.R
import com.theelitedevelopers.homeofmovies.databinding.ActivityMainBinding
import okhttp3.OkHttpClient

class MainActivity : AppCompatActivity() {
    lateinit var navController : NavController;
    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        /**
         * Here, we set up configuration for Navigation Component
         * We tell our Nav Controller how to find our host fragment
         * and then it intuitively handles navigation through the
         * different fragments because we've defined those in our
         * navigation XML file
         */
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment_activity_dashboard) as NavHostFragment
        navController = navHostFragment.navController
        findViewById<BottomNavigationView>(R.id.navigation).setupWithNavController(navController)
    }
}