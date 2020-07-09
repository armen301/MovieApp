package com.movie.app.data.remote

import android.util.Log
import androidx.paging.ItemKeyedDataSource
import com.google.gson.Gson
import com.movie.app.App
import com.movie.app.BuildConfig
import com.movie.app.dto.ErrorResponse
import com.movie.app.dto.list.Movie
import com.movie.app.dto.list.MoviesResponse
import com.movie.app.ui.Filter
import io.reactivex.rxjava3.disposables.CompositeDisposable
import retrofit2.HttpException

class PagingDataSource(
    private val compositeDisposable: CompositeDisposable,
    private val filter: Filter?,
    private val errorCallback: ErrorCallback
) : ItemKeyedDataSource<Int, Movie>() {

    private val gson = Gson()
    private var pageNumber = 1

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Movie>) {
        val query = prepareQuery(pageNumber, filter)
        compositeDisposable.add(App.apiService.fetchPopularMovies(query).subscribe({
            callback.onResult(filterAdults(it) ?: return@subscribe)
        }, { throwable ->
            handleError(throwable)
        }))
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Movie>) {
        val query = prepareQuery(params.key, filter)
        compositeDisposable.add(App.apiService.fetchPopularMovies(query).subscribe({
            callback.onResult(filterAdults(it) ?: return@subscribe)
        }, { throwable ->
            handleError(throwable)
        }))
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Movie>) {}

    override fun getKey(item: Movie): Int {
        return pageNumber
    }

    private fun handleError(throwable: Throwable) {
        if (throwable is HttpException) {
            errorCallback.onError(
                gson.fromJson(
                    throwable.response()?.errorBody()?.string(),
                    ErrorResponse::class.java
                )
            )
        } else {
            Log.d(PagingDataSource::class.java.simpleName, throwable.message ?: "Unknown error")
        }
    }

    private fun filterAdults(response: MoviesResponse?): MutableList<Movie>? {
        return response?.results?.filter {
            it.adult == false
        }?.toMutableList()
    }

    private fun prepareQuery(page: Int, filter: Filter?): Map<String, String> {
        val map = mutableMapOf("api_key" to BuildConfig.API_KEY, "page" to page.toString())
        if (filter?.year != null) {
            map["year"] = filter.year.toString()
        }
        return map
    }
}

interface ErrorCallback {
    fun onError(error: ErrorResponse?)
}