package com.movie.app.network

import com.movie.app.dto.details.MovieDetailsResponse
import com.movie.app.dto.list.MoviesResponse
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.QueryMap

interface APIInterface {

    @GET("movie/popular")
    fun fetchPopularMovies(@QueryMap query: Map<String, String>): Single<MoviesResponse>

    @GET("movie/{movieId}?api_key=5f3e903645b2f0e2f2a21db695104d6c")
    fun fetchMovieDetails(@Path("movieId") movieId: Int): Single<MovieDetailsResponse>
}