package com.theelitedevelopers.homeofmovies.presentation.details

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.theelitedevelopers.homeofmovies.databinding.ActivityMovieDetailsBinding

class MovieDetailsActivity : AppCompatActivity() {
    var binding: ActivityMovieDetailsBinding = TODO()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}