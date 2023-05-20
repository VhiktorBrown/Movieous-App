package com.theelitedevelopers.homeofmovies.presentation.home.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.theelitedevelopers.homeofmovies.data.dtos.responses.GetMoviesResponse
import com.theelitedevelopers.homeofmovies.domain.repository.MovieRepository
import com.theelitedevelopers.homeofmovies.utils.NetworkManager
import com.theelitedevelopers.homeofmovies.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor (
    private val repository: MovieRepository,
    networkManager: NetworkManager
) : ViewModel() {
    private val networkObserver = networkManager.observeConnectionStatus

    private val _allMoviesLiveData = MutableLiveData<Resource<GetMoviesResponse>>()
    val allMoviesLiveData: LiveData<Resource<GetMoviesResponse>> = _allMoviesLiveData

    private val _popularMoviesLiveData = MutableLiveData<Resource<GetMoviesResponse>>()
    val popularMoviesLiveData: LiveData<Resource<GetMoviesResponse>> = _popularMoviesLiveData

    private val _upcomingMoviesLiveData = MutableLiveData<Resource<GetMoviesResponse>>()
    val upcomingMoviesLiveData: LiveData<Resource<GetMoviesResponse>> = _upcomingMoviesLiveData

    private val _topRatedMoviesLiveData = MutableLiveData<Resource<GetMoviesResponse>>()
    val topRatedLiveData: LiveData<Resource<GetMoviesResponse>> = _topRatedMoviesLiveData


    fun getAllMovies() = viewModelScope.launch(Dispatchers.IO) {
        _allMoviesLiveData.postValue(Resource.Loading())
        if (networkObserver.value == true) {
            try {
                val allMoviesResponse = repository.fetchAllMovies()
                if (allMoviesResponse.isSuccessful) {
                    allMoviesResponse.body()?.let {
                        _allMoviesLiveData.postValue(Resource.Success(it))
                    }
                }else  _allMoviesLiveData.postValue(
                    Resource.Error(allMoviesResponse.errorBody().toString())
                )
            } catch (e: Exception) {
                _allMoviesLiveData.postValue(Resource.Error(e.localizedMessage))
            }
        }else {
            _allMoviesLiveData.postValue(Resource.Error(
                "internet not available, check connection"
            ))
        }
    }

    fun getTopRatedMovies() = viewModelScope.launch(Dispatchers.IO) {
        _topRatedMoviesLiveData.postValue(Resource.Loading())
        if (networkObserver.value == true) {
            try {
                val topRatedMoviesResponse = repository.fetchTopRatedMovies()
                if (topRatedMoviesResponse.isSuccessful) {
                    topRatedMoviesResponse.body()?.let {
                        _topRatedMoviesLiveData.postValue(Resource.Success(it))
                    }
                }else  {
                    _topRatedMoviesLiveData.postValue(Resource.Error("Ooops!! something happened," +
                            " try again"))
                }
            } catch (e: Exception) {
                _topRatedMoviesLiveData.postValue(Resource.Error(e.localizedMessage))
            }
        }else {
            _topRatedMoviesLiveData.postValue(Resource.Error(
                "internet not available, check connection"
            ))
        }
    }


    fun getUpComingMovies() = viewModelScope.launch(Dispatchers.IO) {
        _upcomingMoviesLiveData.postValue(Resource.Loading())
        if (networkObserver.value == true) {
            try {
                val upcomingMoviesResponse = repository.fetchUpcomingMovies()
                if (upcomingMoviesResponse.isSuccessful) {
                    upcomingMoviesResponse.body()?.let {
                        _upcomingMoviesLiveData.postValue(Resource.Success(it))
                    }
                }else  {
                    _upcomingMoviesLiveData.postValue(Resource.Error("Ooops!! something happened," +
                            " try again"))
                }
            } catch (e: Exception) {
                _upcomingMoviesLiveData.postValue(Resource.Error(e.localizedMessage))
            }
        }else {
            _upcomingMoviesLiveData.postValue(Resource.Error(
                "internet not available, check connection"
            ))
        }
    }

    fun getPopularMovies() = viewModelScope.launch(Dispatchers.IO) {
        _popularMoviesLiveData.postValue(Resource.Loading())
        if (networkObserver.value == true) {
            try {
                val popularMoviesResponse = repository.fetchPopularMovies()
                if (popularMoviesResponse.isSuccessful) {
                    popularMoviesResponse.body()?.let {
                        _popularMoviesLiveData.postValue(Resource.Success(it))
                    }
                }else  _popularMoviesLiveData.postValue(
                    Resource.Error(popularMoviesResponse.errorBody().toString())
                )
            } catch (e: Exception) {
                _popularMoviesLiveData.postValue(Resource.Error(e.localizedMessage))
            }
        }else {
            _popularMoviesLiveData.postValue(Resource.Error(
                "internet not available, check connection"
            ))
        }
    }

}