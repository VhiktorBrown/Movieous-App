package com.theelitedevelopers.homeofmovies.presentation.details.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.theelitedevelopers.homeofmovies.data.dtos.responses.GetCastsResponse
import com.theelitedevelopers.homeofmovies.data.dtos.responses.GetMoviesResponse
import com.theelitedevelopers.homeofmovies.domain.models.Movie
import com.theelitedevelopers.homeofmovies.domain.repository.MovieRepository
import com.theelitedevelopers.homeofmovies.utils.NetworkManager
import com.theelitedevelopers.homeofmovies.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailsViewModel @Inject constructor(
    private val repository: MovieRepository,
    private val networkManager: NetworkManager
): ViewModel() {
    private val networkObserver = networkManager.observeConnectionStatus

    private val _movieDetailsLiveData = MutableLiveData<Resource<Movie>>()
    val movieDetailsLiveData: LiveData<Resource<Movie>> = _movieDetailsLiveData

    private val _castsLiveData = MutableLiveData<Resource<GetCastsResponse>>()
    val castsLiveData: LiveData<Resource<GetCastsResponse>> = _castsLiveData

    private val _movieRecommendationsLiveData = MutableLiveData<Resource<GetMoviesResponse>>()
    val movieRecommendationsLiveData: LiveData<Resource<GetMoviesResponse>> = _movieRecommendationsLiveData


    fun getRecommendations(movieId: Int) = viewModelScope.launch(Dispatchers.IO) {
        _movieRecommendationsLiveData.postValue(Resource.Loading())
        if (networkObserver.value == true) {
            try {
                val recommendationsResponse = repository.fetchRecommendations(movieId)
                if (recommendationsResponse.isSuccessful) {
                    recommendationsResponse.body()?.let {
                        _movieRecommendationsLiveData.postValue(Resource.Success(it))
                    }
                }else  _movieRecommendationsLiveData.postValue(
                    Resource.Error(recommendationsResponse.errorBody().toString())
                )
            } catch (e: Exception) {
                _movieRecommendationsLiveData.postValue(Resource.Error(e.localizedMessage))
            }
        }else {
            _movieRecommendationsLiveData.postValue(Resource.Error(
                "internet not available, check connection"
            ))
        }
    }


    fun getCasts(movieId: Int) = viewModelScope.launch(Dispatchers.IO) {
        _castsLiveData.postValue(Resource.Loading())
        if (networkObserver.value == true) {
            try {
                val castsResponse = repository.fetchCasts(movieId)
                if (castsResponse.isSuccessful) {
                    castsResponse.body()?.let {
                        _castsLiveData.postValue(Resource.Success(it))
                    }
                }else  _castsLiveData.postValue(
                    Resource.Error(castsResponse.errorBody().toString())
                )
            } catch (e: Exception) {
                _castsLiveData.postValue(Resource.Error(e.localizedMessage))
            }
        }else {
            _castsLiveData.postValue(Resource.Error(
                "internet not available, check connection"
            ))
        }
    }

    fun getMovieDetails(movieId: Int) = viewModelScope.launch(Dispatchers.IO) {
        _movieDetailsLiveData.postValue(Resource.Loading())
        if (networkObserver.value == true) {
            try {
                val movieDetailsResponse = repository.fetchMovieDetails(movieId)
                if (movieDetailsResponse.isSuccessful) {
                    movieDetailsResponse.body()?.let {
                        _movieDetailsLiveData.postValue(Resource.Success(it))
                    }
                }else  _movieDetailsLiveData.postValue(
                    Resource.Error(movieDetailsResponse.errorBody().toString())
                )
            } catch (e: Exception) {
                _movieDetailsLiveData.postValue(Resource.Error(e.localizedMessage))
            }
        }else {
            _movieDetailsLiveData.postValue(Resource.Error(
                "internet not available, check connection"
            ))
        }
    }
}