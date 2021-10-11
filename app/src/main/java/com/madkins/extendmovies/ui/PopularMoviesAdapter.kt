package com.madkins.extendmovies.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.madkins.extendmovies.R
import com.madkins.extendmovies.model.PopularMovie

class PopularMoviesAdapter(private val listener: PopularMoviesViewHolder.OnMovieClickListener):
    PagingDataAdapter<PopularMovie, PopularMoviesViewHolder>(diffCallbacks) {

    override fun onBindViewHolder(holder: PopularMoviesViewHolder, position: Int) {
        getItem(position)?.let { popularMovie ->
            holder.bind(popularMovie)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularMoviesViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_popular_movie, parent, false)
        return PopularMoviesViewHolder(view, listener)
    }

    companion object {
        private val diffCallbacks = object: DiffUtil.ItemCallback<PopularMovie>() {
            override fun areItemsTheSame(oldItem: PopularMovie, newItem: PopularMovie): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: PopularMovie, newItem: PopularMovie): Boolean =
                oldItem.id == newItem.id
                && oldItem.title == newItem.title
                && oldItem.overview == newItem.overview
                && oldItem.posterUrl == newItem.posterUrl
        }
    }
}