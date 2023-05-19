package com.theelitedevelopers.homeofmovies.domain.repository

import com.theelitedevelopers.homeofmovies.data.dtos.responses.GetMoviesResponse
import retrofit2.Response

/**
 * @created 17/5/2023 - 6:53 PM
 * @project Movies app
 * @author Victor
 */

interface MovieRepository {
    //fetch All Movies
    suspend fun fetchAllMovies() : Response<GetMoviesResponse>
    //Popular
    suspend fun fetchPopularMovies() : Response<GetMoviesResponse>
    //Upcoming
    suspend fun fetchUpcomingMovies() : Response<GetMoviesResponse>
    //Top Rated
    suspend fun fetchTopRatedMovies() : Response<GetMoviesResponse>
    //Recommendations
    suspend fun fetchRecommendations(movieId: String) : Response<GetMoviesResponse>

}