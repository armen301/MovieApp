package com.movie.app.ui.paging

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.movie.app.BuildConfig
import com.movie.app.R
import com.movie.app.dto.list.Movie
import kotlinx.android.synthetic.main.item_movie.view.*

class MoviesAdapter(private val clickListener: ItemClickListener) :
    PagedListAdapter<Movie, MovieViewHolder>(
        DiffUtils()
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_movie, parent, false)
        return MovieViewHolder(view, clickListener)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class MovieViewHolder(itemView: View, private val clickListener: ItemClickListener) :
    RecyclerView.ViewHolder(itemView) {

    fun bind(movie: Movie?) {
        movie ?: return

        itemView.setOnClickListener {
            clickListener.onClick(movie.id, movie.title ?: "")
        }

        Glide.with(itemView.poster).load(BuildConfig.IMAGE_BASE_URL + "w185" + movie.posterPath)
            .placeholder(R.drawable.ic_launcher_foreground).into(itemView.poster)
        itemView.title.text = movie.title
        itemView.rating.apply {
            text = context.getString(R.string.rating, movie.popularity)
        }
        itemView.overview.text = movie.overview

        itemView.release.apply {
            text = context.getString(R.string.premiered_on, movie.releaseDate)
        }
    }
}

interface ItemClickListener {
    fun onClick(id: Int, title: String)
}

class DiffUtils : DiffUtil.ItemCallback<Movie>() {
    override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        // In a real app should be implements Movie.equals() or add comparision logic here.
        return oldItem == newItem
    }
}