package com.madkins.extendmovies.ui

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.madkins.extendmovies.R
import com.madkins.extendmovies.model.Movie
import com.madkins.extendmovies.model.PopularMovie
import com.madkins.extendmovies.repo.TMDBRepo
import com.squareup.picasso.Picasso

class PopularMoviesViewHolder(itemView: View, private val listener: OnMovieClickListener): RecyclerView.ViewHolder(itemView), View.OnClickListener {
    interface OnMovieClickListener {
        fun onMovieClick(id: Int)
    }

    private val title: TextView = itemView.findViewById(R.id.popular_movie_title)
    private val overview: TextView = itemView.findViewById(R.id.popular_movie_overview)
    private val poster: ImageView = itemView.findViewById(R.id.popular_movie_poster)
    private lateinit var movie: PopularMovie

    init {
        itemView.setOnClickListener(this)
    }

    fun bind(movie: PopularMovie) {
        this.movie = movie
        title.text = movie.title
        overview.text = movie.overview
        val url = TMDBRepo.IMAGE_URL_BASE + movie.posterUrl
        Picasso.get()
            .load(url)
            .into(poster)
    }

    override fun onClick(view: View) {
        listener.onMovieClick(movie.id)
    }
}