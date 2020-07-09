package com.movie.app.data.repository

import com.movie.app.data.remote.MovieDetailsDataSource

class MovieDetailsRepository {
    fun fetchMovieDetails(id: Int) = MovieDetailsDataSource().fetchMovieDetails(id)
}