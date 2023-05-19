package com.theelitedevelopers.homeofmovies.presentation.details.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.theelitedevelopers.homeofmovies.data.dtos.responses.GetMoviesResponse
import com.theelitedevelopers.homeofmovies.domain.models.Movie
import com.theelitedevelopers.homeofmovies.domain.repository.MovieRepository
import com.theelitedevelopers.homeofmovies.utils.NetworkManager
import com.theelitedevelopers.homeofmovies.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MovieDetailsViewModel @Inject constructor(
    private val repository: MovieRepository,
    private val networkManager: NetworkManager
): ViewModel() {
    private val networkObserver = networkManager.observeConnectionStatus

    private val _movieDetailsLiveData = MutableLiveData<Resource<Movie>>()
    val movieDetailsLiveData: LiveData<Resource<Movie>> = _movieDetailsLiveData
}