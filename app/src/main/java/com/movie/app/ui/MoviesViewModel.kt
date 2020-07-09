package com.movie.app.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import androidx.paging.toLiveData
import com.movie.app.Event
import com.movie.app.data.remote.ErrorCallback
import com.movie.app.dto.ErrorResponse
import com.movie.app.dto.list.Movie
import com.movie.app.useCase.PopularMoviesUseCase
import io.reactivex.rxjava3.disposables.CompositeDisposable

class MoviesViewModel : ViewModel() {

    private val disposable = CompositeDisposable()
    private val moviesUseCase = PopularMoviesUseCase(disposable, object : ErrorCallback {
        override fun onError(error: ErrorResponse?) {
            errorResponse.postValue(Event.dataEvent(error))
        }
    })
    private val filter = MutableLiveData<Filter>()

    val movies: LiveData<PagedList<Movie>> = Transformations.switchMap(filter) {
        moviesUseCase.fetchPopularMovies(filter.value).toLiveData(pageSize = 15)
    }
    val errorResponse = MutableLiveData<Event<ErrorResponse>>()

    init {
        filter.value = Filter()
    }

    fun changeFilter(year: Int?) {
        filter.value = Filter(year)
    }

    override fun onCleared() {
        disposable.clear()
        super.onCleared()
    }
}

class Filter(
    val year: Int? = null
)