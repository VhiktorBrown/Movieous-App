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
    private val networkManager: NetworkManager
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


    fun getTopRatedMovies() = viewModelScope.launch(Dispatchers.IO) {
        _topRatedMoviesLiveData.postValue(Resource.Loading())
        if (networkObserver.value == true) {
            try {
                val topRatedMoviesResponse = repository.fetchTopRatedMovies()
                if (topRatedMoviesResponse.isSuccessful) {
                    topRatedMoviesResponse.body()?.let { movieResponse ->
                        _topRatedMoviesLiveData.postValue(Resource.Success(movieResponse))
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


}