package com.theelitedevelopers.homeofmovies.presentation.home.fragments

import android.media.Image
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager.widget.ViewPager.OnPageChangeListener
import com.google.android.material.snackbar.Snackbar
import com.theelitedevelopers.homeofmovies.R
import com.theelitedevelopers.homeofmovies.databinding.FragmentHomeBinding
import com.theelitedevelopers.homeofmovies.domain.models.Movie
import com.theelitedevelopers.homeofmovies.presentation.home.fragments.adapters.MovieAdapter
import com.theelitedevelopers.homeofmovies.presentation.home.fragments.adapters.SliderAdapter
import com.theelitedevelopers.homeofmovies.presentation.home.viewmodels.HomeViewModel
import com.theelitedevelopers.homeofmovies.utils.Constants
import com.theelitedevelopers.homeofmovies.utils.Resource
import com.theelitedevelopers.homeofmovies.utils.SpacesItemDecorator
import dagger.hilt.android.AndroidEntryPoint
import androidx.navigation.NavOptions




@AndroidEntryPoint
class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private val viewModel: HomeViewModel by activityViewModels()
    private lateinit var popularAdapter: MovieAdapter
    private lateinit var upcomingAdapter: MovieAdapter
    private lateinit var topRatedAdapter: MovieAdapter
    private var movieList: List<Movie>? = arrayListOf()
    var size = 0
    private lateinit var adapter: SliderAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        viewModel.apply {
            getAllMovies()
            getPopularMovies()
            getUpComingMovies()
            getTopRatedMovies()
        }

        initViews()
        observeMovieData()

        return binding.root
    }

    private fun initViews(){
        binding.viewPager.addOnPageChangeListener(viewListener)

        popularAdapter = MovieAdapter(requireActivity(), movieList)
        upcomingAdapter = MovieAdapter(requireActivity(), movieList)
        topRatedAdapter = MovieAdapter(requireActivity(), movieList)

        binding.searchEditText.setOnClickListener {
            val navOptions = NavOptions.Builder().setPopUpTo(R.id.homeFragment, true).build()
            findNavController().navigate(R.id.searchFragment, null, navOptions)
        }

        binding.popularRecyclerView.apply {
            adapter = popularAdapter
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false).also {
                hasFixedSize()
                addItemDecoration(SpacesItemDecorator(Constants.SPACE))
            }
        }

        binding.upcomingRecyclerView.apply {
            adapter = upcomingAdapter
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false).also {
                hasFixedSize()
                addItemDecoration(SpacesItemDecorator(Constants.SPACE))
            }
        }

        binding.topRatedRecyclerView.apply {
            adapter = topRatedAdapter
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false).also {
                hasFixedSize()
                addItemDecoration(SpacesItemDecorator(Constants.SPACE))
            }
        }
    }

    private fun observeMovieData() {
        viewModel.allMoviesLiveData.observe(viewLifecycleOwner) { state ->
            when(state) {
                is Resource.Success -> {
                    state.data?.let {
                        size = it.results.size
                        //set up the Viewpager at the top of the Homepage
                        adapter = SliderAdapter(requireActivity(), it.results.shuffled())
                        binding.viewPager.adapter = adapter
                    }
                }
                is Resource.Error -> {
                    Snackbar.make(binding.root, state.message!!, Snackbar.LENGTH_LONG).show()
                }
            }
        }

        viewModel.upcomingMoviesLiveData.observe(viewLifecycleOwner) { state ->
            when(state) {
                is Resource.Success -> {
                    hideProgressBar(1)
                    state.data?.let {
                        upcomingAdapter.setList(it.results.shuffled())
                    }
                }
                is Resource.Error -> {
                    hideProgressBar(1)
                    Snackbar.make(binding.root, state.message!!, Snackbar.LENGTH_LONG).show()
                }
                is Resource.Loading -> showProgressBar(1)
            }
        }

        viewModel.topRatedLiveData.observe(viewLifecycleOwner) { state ->
            when(state) {
                is Resource.Success -> {
                    hideProgressBar(2)
                    state.data?.let {
                        topRatedAdapter.setList(it.results.shuffled())
                    }
                }
                is Resource.Error -> {
                    hideProgressBar(2)
                    Snackbar.make(binding.root, state.message!!, Snackbar.LENGTH_LONG).show()
                }
                is Resource.Loading -> showProgressBar(2)
            }
        }

        viewModel.popularMoviesLiveData.observe(viewLifecycleOwner) { state ->
            when(state) {
                is Resource.Success -> {
                    hideProgressBar(0)
                    state.data?.let {
                        popularAdapter.setList(it.results.shuffled())
                    }
                }
                is Resource.Error -> {
                    hideProgressBar(0)
                    Snackbar.make(binding.root, state.message!!, Snackbar.LENGTH_LONG).show()
                }
                is Resource.Loading -> showProgressBar(0)
            }
        }
    }

    private fun showProgressBar(level: Int){
        when(level) {
            0 -> binding.popularProgressBar.visibility = View.VISIBLE
            1 -> binding.upcomingProgressBar.visibility = View.VISIBLE
            2 -> binding.topRatedProgressBar.visibility = View.VISIBLE
        }
    }

    private fun hideProgressBar(level: Int){
        when(level) {
            0 -> binding.popularProgressBar.visibility = View.GONE
            1 -> binding.upcomingProgressBar.visibility = View.GONE
            2 -> binding.topRatedProgressBar.visibility = View.GONE
        }
    }

    fun addDotsIndicator(position: Int, size: Int) {
        val mDots = arrayOfNulls<ImageView>(size)
        binding.dotsLayout.removeAllViews()
        if (isAdded) {
            for (i in mDots.indices) {
                mDots[i] = ImageView(requireActivity())
                if (i == position) mDots[i]!!
                    .setImageDrawable(resources.getDrawable(R.drawable.dot_onboard_view)) else {
                    mDots[i]!!
                        .setImageDrawable(resources.getDrawable(R.drawable.dot_onboard_unactive_view))
                }
                val params = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                )
                params.setMargins(4, 0, 4, 0)
                binding.dotsLayout.addView(mDots[i], params)
            }
        }
    }

    var viewListener: OnPageChangeListener = object : OnPageChangeListener {
        override fun onPageScrolled(
            position: Int,
            positionOffset: Float,
            positionOffsetPixels: Int
        ) {
        }

        override fun onPageSelected(position: Int) {
            if (size > 0) {
                addDotsIndicator(position, size)
            }
        }

        override fun onPageScrollStateChanged(state: Int) {}
    }

}