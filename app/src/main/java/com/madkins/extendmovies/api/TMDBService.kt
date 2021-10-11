package com.madkins.extendmovies.api

import com.madkins.extendmovies.model.Movie
import com.madkins.extendmovies.model.PopularMovieResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TMDBService {
    @GET("3/movie/popular" +
            "?api_key=852d31c4e01491e5e64a0350edd1a29b")
    suspend fun fetchPopularMovies(@Query(value = "page") pageNumber: Int): Response<PopularMovieResponse>

    @GET("3/movie/{movie_id}" +
            "?api_key=852d31c4e01491e5e64a0350edd1a29b")
    fun fetchMovieById(@Path(value = "movie_id", encoded = false) id: Int): Call<Movie>
}