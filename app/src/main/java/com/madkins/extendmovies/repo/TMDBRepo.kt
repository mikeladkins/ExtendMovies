package com.madkins.extendmovies.repo

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.madkins.extendmovies.api.TMDBService
import com.madkins.extendmovies.api.TMDBServiceProvider
import com.madkins.extendmovies.model.Movie
import com.madkins.extendmovies.model.PopularMovie
import kotlinx.coroutines.flow.Flow
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private const val TAG = "TMDBRepo"

class TMDBRepo {
    private val service = TMDBServiceProvider.getRetrofit().create(TMDBService::class.java)

    fun fetchPopularMovies(): Flow<PagingData<PopularMovie>> {
        return Pager(
            PagingConfig(pageSize = 20, enablePlaceholders = false)
        ){TMDBPagingSource(service)}.flow
    }

    fun fetchMovieById(id: Int): MutableLiveData<Movie> {
        val request = service.fetchMovieById(id)
        val responseLiveData: MutableLiveData<Movie> = MutableLiveData()

        request.enqueue(object: Callback<Movie> {
            override fun onResponse(call: Call<Movie>, response: Response<Movie>) {
                Log.i(TAG, "Movie response received: ${response.body()}")
                responseLiveData.value = response.body()
            }

            override fun onFailure(call: Call<Movie>, t: Throwable) {
                Log.e(TAG, "Failed to receive movie response.", t)
            }

        })
        return responseLiveData
    }

    companion object {
        const val IMAGE_URL_BASE = "https://image.tmdb.org/t/p/original"
    }
}