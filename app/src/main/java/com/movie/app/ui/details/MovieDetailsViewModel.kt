package com.movie.app.ui.details

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.movie.app.Event
import com.movie.app.dto.ErrorResponse
import com.movie.app.dto.details.MovieDetailsResponse
import com.movie.app.useCase.MovieDetailsUseCase
import io.reactivex.rxjava3.disposables.CompositeDisposable
import retrofit2.HttpException

class MovieDetailsViewModel : ViewModel() {

    private val useCase = MovieDetailsUseCase()
    private val disposable = CompositeDisposable()

    val details = MutableLiveData<MovieDetailsResponse>()
    val error = MutableLiveData<Event<ErrorResponse>>()

    fun fetchDetails(id: Int) {
        disposable.add(useCase.fetchMovieDetails(id).subscribe({
            details.value = it
        }, {
            if (it is HttpException) {
                error.value = Event.dataEvent(
                    Gson().fromJson(
                        it.response()?.errorBody()?.string(),
                        ErrorResponse::class.java
                    )
                )
            } else {
                Log.d(MovieDetailsViewModel::class.java.simpleName, it.message ?: "Unknown error")
            }
        }))
    }

    override fun onCleared() {
        disposable.clear()
        super.onCleared()
    }
}