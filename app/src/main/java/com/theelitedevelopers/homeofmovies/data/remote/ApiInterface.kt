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
    @Headers("accept: application/json")
    suspend fun fetchAllMovies() : GetMoviesResponse

    //fetch Popular Movies from API
    @GET("movie/popular?language=en-US&page=1")
    @Headers("accept: application/json")
    suspend fun fetchPopularMovies() : GetMoviesResponse

    //fetch Top Rated Movies from API
    @GET("movie/top_rated?language=en-US&page=1")
    @Headers("accept: application/json")
    suspend fun fetchTopRatedMovies() : GetMoviesResponse

    //fetch Upcoming Movies from API
    @GET("/movie/upcoming?language=en-US&page=1")
    @Headers("accept: application/json")
    suspend fun fetchUpcomingMovies() : GetMoviesResponse

    //Fetch Recommendations for other movies based on a movie.
    @GET("movie/{movie_id}/recommendations?language=en-US&page=1")
    @Headers("accept: application/json")
    suspend fun fetchRecommendations(
        @Path("{movie_id}") movieId: String?
    ): GetMoviesResponse
}