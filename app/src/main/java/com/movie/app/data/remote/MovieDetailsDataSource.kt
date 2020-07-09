package com.movie.app.data.remote

import com.movie.app.App
import com.movie.app.dto.details.MovieDetailsResponse
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

class MovieDetailsDataSource {

    fun fetchMovieDetails(id: Int): Single<MovieDetailsResponse> =
        App.apiService.fetchMovieDetails(id).subscribeOn(Schedulers.io()).observeOn(
            AndroidSchedulers.mainThread()
        )
}