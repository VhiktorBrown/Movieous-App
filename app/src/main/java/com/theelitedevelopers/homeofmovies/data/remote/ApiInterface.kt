package com.theelitedevelopers.homeofmovies.data.remote

import com.theelitedevelopers.homeofmovies.data.dtos.responses.GetMoviesResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.HeaderMap
import retrofit2.http.Headers
import retrofit2.http.Path

interface ApiInterface {

    //fetch Movies from API
    @GET("discover/movie?include_adult=false&include_video=true&language=en-US&page=1&sort_by=popularity.desc")
    suspend fun fetchAllMovies() : Response<GetMoviesResponse>

    //fetch Popular Movies from API
    @GET("movie/popular?language=en-US&page=1")
    suspend fun fetchPopularMovies() : Response<GetMoviesResponse>

    //fetch Top Rated Movies from API
    @GET("movie/top_rated?language=en-US&page=1")
    suspend fun fetchTopRatedMovies() : Response<GetMoviesResponse>

    //fetch Upcoming Movies from API
    @GET("/movie/upcoming?language=en-US&page=1")
    suspend fun fetchUpcomingMovies() : Response<GetMoviesResponse>

    //Fetch Recommendations for other movies based on a movie.
    @GET("movie/{movie_id}/recommendations?language=en-US&page=1")
    suspend fun fetchRecommendations(
        @Path("{movie_id}") movieId: String?
    ): Response<GetMoviesResponse>
}