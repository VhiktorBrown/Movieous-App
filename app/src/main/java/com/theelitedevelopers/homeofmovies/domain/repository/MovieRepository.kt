package com.theelitedevelopers.homeofmovies.domain.repository

import com.theelitedevelopers.homeofmovies.data.dtos.responses.GetMoviesResponse

/**
 * @created 17/5/2023 - 6:53 PM
 * @project Movies app
 * @author Victor
 */

interface MovieRepository {
    //fetch All Movies
    suspend fun fetchAllMovies() : GetMoviesResponse
    //Popular
    suspend fun fetchPopularMovies() : GetMoviesResponse
    //Upcoming
    suspend fun fetchUpcomingMovies() : GetMoviesResponse
    //Top Rated
    suspend fun fetchTopRatedMovies() : GetMoviesResponse
    //Recommendations
    suspend fun fetchRecommendations() : GetMoviesResponse

}