package com.theelitedevelopers.homeofmovies.domain.repository
import com.theelitedevelopers.homeofmovies.data.dtos.responses.GetMoviesResponse
import com.theelitedevelopers.homeofmovies.data.remote.ApiInterface
import retrofit2.Response
import javax.inject.Inject

/**
 * @created 17/5/2023 - 6:53 PM
 * @project Movies app
 * @author Victor
 */

/**
 * This has been configured with Hilt
 * which means that Hilt helps us create
 * an instance of this class behind the scenes
 * so that we don't have to initialize it ourselves.
 *
 * Same goes for the API INTERFACE class that we inject.
 */

class MovieRepositoryImpl @Inject constructor(
    private val apiInterface: ApiInterface
) : MovieRepository{

    override suspend fun fetchAllMovies(): Response<GetMoviesResponse> {
        return apiInterface.fetchAllMovies()
    }

    override suspend fun fetchPopularMovies(): Response<GetMoviesResponse> {
        return apiInterface.fetchPopularMovies()
    }

    override suspend fun fetchUpcomingMovies(): Response<GetMoviesResponse> {
        return apiInterface.fetchUpcomingMovies()
    }

    override suspend fun fetchTopRatedMovies(): Response<GetMoviesResponse> {
        return apiInterface.fetchTopRatedMovies()
    }

    override suspend fun fetchRecommendations(movieId: String): Response<GetMoviesResponse> {
        return apiInterface.fetchRecommendations(movieId)
    }
}