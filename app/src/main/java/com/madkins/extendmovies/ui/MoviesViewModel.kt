package com.madkins.extendmovies.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.madkins.extendmovies.model.Movie
import com.madkins.extendmovies.model.PopularMovie
import com.madkins.extendmovies.repo.TMDBRepo
import kotlinx.coroutines.flow.Flow

class MoviesViewModel: ViewModel() {
    private val repo = TMDBRepo()
    var movieLiveData: LiveData<Movie> = MutableLiveData()

    fun fetchPopularMovies(): Flow<PagingData<PopularMovie>> {
        return repo.fetchPopularMovies().cachedIn(viewModelScope)
    }

    fun fetchMovieById(id: Int) {
        movieLiveData = repo.fetchMovieById(id)
    }
}