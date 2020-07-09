package com.movie.app.ui.details

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View.GONE
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.movie.app.BuildConfig
import com.movie.app.R
import com.movie.app.extensions.showDialog
import kotlinx.android.synthetic.main.activity_movie_details.*

private const val ID = "ID"
private const val TITLE = "TITLE"

class MovieDetailsActivity : AppCompatActivity() {

    companion object {
        fun Activity.startMovieDetailsActivity(movieId: Int, title: String) {
            startActivity(Intent(this, MovieDetailsActivity::class.java).apply {
                putExtra(ID, movieId)
                putExtra(TITLE, title)
            })
        }
    }

    private val viewModel by viewModels<MovieDetailsViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_details)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        title = intent.getStringExtra(TITLE)

        viewModel.error.observe(this, Observer { event ->
            event?.let {
                it.getContentIfNotHandled()?.let {
                    showDialog(it.statusMessage ?: "", getString(R.string.ok))

                    progressBar.visibility = GONE
                }
            }
        })
        viewModel.details.observe(this, Observer { response ->
            response?.let {
                Glide.with(poster).load(BuildConfig.IMAGE_BASE_URL + "w342" + it.backdropPath)
                    .placeholder(R.drawable.ic_launcher_foreground).into(poster)

                movieTitle.text = it.title
                originLanguage.text = it.originalLanguage
                rating.text = getString(R.string.rating, it.popularity)
                release.text = getString(R.string.premiered_on, it.releaseDate)
                overview.text = it.overview
                budget.text = getString(R.string.budget, it.budget)

                progressBar.visibility = GONE
            }
        })

        viewModel.fetchDetails(intent.getIntExtra(ID, 0))
    }
}