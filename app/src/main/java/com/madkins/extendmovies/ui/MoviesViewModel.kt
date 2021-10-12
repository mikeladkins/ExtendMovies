package com.madkins.extendmovies.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagingData
import com.madkins.extendmovies.model.Movie
import com.madkins.extendmovies.model.PopularMovie
import com.madkins.extendmovies.repo.TMDBRepo
import kotlinx.coroutines.flow.Flow

class MoviesViewModel: ViewModel() {
    private val repo = TMDBRepo()
    var movieLiveData: MutableLiveData<Movie> = MutableLiveData()
    var favoriteMovies: MutableList<Int> = mutableListOf()

    fun fetchPopularMovies(): Flow<PagingData<PopularMovie>> {
        return repo.fetchPopularMovies()
    }

    fun fetchMovieById(id: Int) {
        //movieLiveData = repo.fetchMovieById(id)
        val movieFromApi = repo.fetchMovieById(id)
        if (favoriteMovies.contains(movieFromApi.value?.id)) {
            movieFromApi.value?.favorited = true
        }
        movieLiveData = movieFromApi
    }

    fun movieFavoriteButtonClicked() {
        val movieId = movieLiveData.value?.id
        if(movieId != null) {
            if(favoriteMovies.contains(movieId)) {
                favoriteMovies.remove(movieId)
            } else {
                favoriteMovies.add(movieId)
            }
        }
        movieLiveData.value = movieLiveData.value?.apply {
            favorited = !favoriteMovies.contains(movieId)
        }
    }
}