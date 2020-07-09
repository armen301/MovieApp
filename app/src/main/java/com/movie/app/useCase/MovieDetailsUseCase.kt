package com.movie.app.useCase

import com.movie.app.data.repository.MovieDetailsRepository

class MovieDetailsUseCase {

    fun fetchMovieDetails(id: Int) = MovieDetailsRepository().fetchMovieDetails(id)
}