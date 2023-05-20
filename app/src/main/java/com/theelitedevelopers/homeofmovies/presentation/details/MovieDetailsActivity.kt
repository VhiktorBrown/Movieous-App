package com.theelitedevelopers.homeofmovies.presentation.details

import android.annotation.SuppressLint
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso
import com.theelitedevelopers.homeofmovies.databinding.ActivityMovieDetailsBinding
import com.theelitedevelopers.homeofmovies.domain.models.Cast
import com.theelitedevelopers.homeofmovies.domain.models.Movie
import com.theelitedevelopers.homeofmovies.presentation.details.adapters.CastAdapter
import com.theelitedevelopers.homeofmovies.presentation.details.viewmodels.MovieDetailsViewModel
import com.theelitedevelopers.homeofmovies.presentation.home.fragments.adapters.MovieAdapter
import com.theelitedevelopers.homeofmovies.utils.Constants
import com.theelitedevelopers.homeofmovies.utils.Resource
import com.theelitedevelopers.homeofmovies.utils.SpacesItemDecorator
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MovieDetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMovieDetailsBinding
    private val viewModel: MovieDetailsViewModel by viewModels()
    private lateinit var castAdapter: CastAdapter
    private lateinit var recommendedAdapter: MovieAdapter
    private lateinit var movie: Movie
    private var castList: List<Cast>? = arrayListOf()
    private var recommendationList: List<Movie>? = arrayListOf()
    private var movieId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
//        movie = if (Build.VERSION.SDK_INT >= 33) {
//            intent.getParcelableExtra(Constants.MOVIE_DETAIL, Movie::class.java)!!
//        } else {
//            intent.getParcelableExtra<Movie>(Constants.MOVIE_DETAIL)!!
//        }
        movieId = intent.getIntExtra(Constants.MOVIE_ID, 0)

        viewModel.apply {
            getMovieDetails(movieId)
            getCasts(movieId)
            getRecommendations(movieId)
        }

        initViews()
        observeMovieDetailsData()
    }

    private fun initViews(){
//        //set Movie image as background
//        Picasso.get()
//            .load(Constants.BASE_URL_IMAGE+movie.image)
//            .into(binding.movieImageBackground)


        castAdapter = CastAdapter(this, castList)
        recommendedAdapter = MovieAdapter(this, recommendationList)

        binding.castRecyclerView.apply {
            adapter = castAdapter
            layoutManager = LinearLayoutManager(this@MovieDetailsActivity, LinearLayoutManager.HORIZONTAL, false).also {
                hasFixedSize()
                addItemDecoration(SpacesItemDecorator(Constants.SPACE))
            }
        }

        binding.recommendationsRecyclerView.apply {
            adapter = recommendedAdapter
            layoutManager = LinearLayoutManager(this@MovieDetailsActivity, LinearLayoutManager.HORIZONTAL, false).also {
                hasFixedSize()
                addItemDecoration(SpacesItemDecorator(Constants.SPACE))
            }
        }

    }

    private fun observeMovieDetailsData() {
        viewModel.movieDetailsLiveData.observe(this) { state ->
            when(state){
                is Resource.Success -> {
                    showProgressBar(false)
                    state.data?.let {
                        //set up Movie details
                        setUpMovieData(it)
                    }
                }
                is Resource.Error -> {
                    showProgressBar(false)
                    Snackbar.make(binding.root, state.message!!, Snackbar.LENGTH_LONG).show()
                }
                is Resource.Loading -> {
                    //show Progress Bar
                    showProgressBar(true)
                }
            }
        }

        viewModel.castsLiveData.observe(this) { state ->
            when(state) {
                is Resource.Success -> {
                    showProgressBar(false)
                    state.data?.let {
                        if(it.casts.isNotEmpty()){
                            binding.recommendationsText.visibility = View.VISIBLE
                            binding.recommendationsRecyclerView.visibility = View.VISIBLE
                            castAdapter.setList(it.casts)
                        }else {
                            binding.recommendationsText.visibility = View.GONE
                            binding.recommendationsRecyclerView.visibility = View.GONE
                        }
                    }
                }
                is Resource.Error -> {
                    showProgressBar(false)
                    Snackbar.make(binding.root, state.message!!, Snackbar.LENGTH_LONG).show()
                }
                is Resource.Loading -> showProgressBar(true)
            }
        }

        viewModel.movieRecommendationsLiveData.observe(this) { state ->
            when(state) {
                is Resource.Success -> {
                    showProgressBar(false)
                    state.data?.let {
                        if(it.results.isNotEmpty()){
                            binding.recommendationsText.visibility = View.VISIBLE
                            binding.recommendationsRecyclerView.visibility = View.VISIBLE
                            recommendedAdapter.setList(it.results.shuffled())
                        }else {
                            binding.recommendationsText.visibility = View.GONE
                            binding.recommendationsRecyclerView.visibility = View.GONE
                        }
                    }
                }
                is Resource.Error -> {
                    showProgressBar(false)
                    Snackbar.make(binding.root, state.message!!, Snackbar.LENGTH_LONG).show()
                }
                is Resource.Loading -> showProgressBar(true)
            }
        }

    }

    @SuppressLint("SetTextI18n")
    private fun setUpMovieData(movie: Movie){
        binding.movieTitle.text = movie.title
        binding.movieDate.text = movie.releaseDate
        binding.movieRating.text = movie.rating.toString()
        binding.movieYear.text = movie.releaseDate!!.substring(0, 4)
        binding.movieDuration.text = "${movie.runtime} mins"
        binding.movieGenre.text = movie.genres[0].name
        binding.storyline.text = movie.description

        Picasso.get()
            .load(Constants.BASE_URL_IMAGE+movie.image)
            .into(binding.movieImage)
    }

    private fun showProgressBar(show: Boolean){
        when(show){
            true -> {
                binding.progressBar.visibility = View.VISIBLE
                binding.mainScrollView.visibility = View.GONE
            }
            false -> {
                binding.progressBar.visibility = View.GONE
                binding.mainScrollView.visibility = View.VISIBLE
            }
        }
    }
}