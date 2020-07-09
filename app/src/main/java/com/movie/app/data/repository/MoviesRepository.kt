package com.movie.app.data.repository

import androidx.paging.DataSource
import com.movie.app.data.remote.ErrorCallback
import com.movie.app.data.remote.PagingDataSource
import com.movie.app.dto.list.Movie
import com.movie.app.ui.Filter
import io.reactivex.rxjava3.disposables.CompositeDisposable

class MoviesRepository(
    private val compositeDisposable: CompositeDisposable,
    private val errorCallback: ErrorCallback
) {

    fun fetchPopularMovies(filter: Filter?): DataSource.Factory<Int, Movie> =
        object : DataSource.Factory<Int, Movie>() {
            override fun create(): DataSource<Int, Movie> {
                return PagingDataSource(compositeDisposable, filter, errorCallback)
            }
        }
}