package com.movie.app.useCase

import com.movie.app.data.remote.ErrorCallback
import com.movie.app.data.repository.MoviesRepository
import com.movie.app.ui.Filter
import io.reactivex.rxjava3.disposables.CompositeDisposable

class PopularMoviesUseCase(
    private val compositeDisposable: CompositeDisposable,
    private val errorCallback: ErrorCallback
) {
    fun fetchPopularMovies(filter: Filter?) =
        MoviesRepository(compositeDisposable, errorCallback).fetchPopularMovies(filter)
}