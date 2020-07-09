package com.movie.app.ui

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.PopupMenu
import androidx.lifecycle.Observer
import com.movie.app.R
import com.movie.app.extensions.showDialog
import com.movie.app.ui.details.MovieDetailsActivity.Companion.startMovieDetailsActivity
import com.movie.app.ui.paging.ItemClickListener
import com.movie.app.ui.paging.MoviesAdapter
import kotlinx.android.synthetic.main.activity_movies.*

class MoviesActivity : AppCompatActivity() {

    private val viewModel by viewModels<MoviesViewModel>()
    private lateinit var adapter: MoviesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movies)

        adapter = MoviesAdapter(object : ItemClickListener {
            override fun onClick(id: Int, title: String) = startMovieDetailsActivity(id, title)
        })
        recyclerView.adapter = adapter

        viewModel.movies.observe(this, Observer {
            adapter.submitList(it)

            progressBar.visibility = View.GONE
        })
        viewModel.errorResponse.observe(this, Observer { event ->
            event?.let {
                it.getContentIfNotHandled()?.let {
                    showDialog(it.statusMessage ?: "", getString(R.string.ok))

                    progressBar.visibility = View.GONE
                }
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_filter, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.filter -> {
                PopupMenu(this, findViewById(R.id.filter)).apply {
                    menuInflater.inflate(R.menu.menu_years, menu)
                    setOnMenuItemClickListener {
                        when (it.itemId) {
                            R.id.year_1900 -> viewModel.changeFilter(1900)
                            R.id.year_2000 -> viewModel.changeFilter(2000)
                            R.id.year_2020 -> viewModel.changeFilter(2020)
                        }
                        return@setOnMenuItemClickListener true
                    }
                    show()
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }
}