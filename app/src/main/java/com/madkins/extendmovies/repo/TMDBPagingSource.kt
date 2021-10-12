package com.madkins.extendmovies.repo

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.madkins.extendmovies.api.TMDBService
import com.madkins.extendmovies.model.PopularMovie
import com.madkins.extendmovies.model.PopularMovieResponse
import retrofit2.HttpException
import java.io.IOException

// Index of the starting page
private const val TMDB_STARTING_PAGE = 1

class TMDBPagingSource(private val service: TMDBService) : PagingSource<Int, PopularMovie>() {

    override fun getRefreshKey(state: PagingState<Int, PopularMovie>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PopularMovie> {
        // Check for existing page number, otherwise use starting page
        val position = params.key ?: TMDB_STARTING_PAGE
        return try {
            // Get a response from the API
            val response = service.fetchPopularMovies(position)
            // Get the popular movies from the response
            val movies = response.body()?.results
            val page = response.body()?.page
            // Set the next key (i.e. next page value)
            // If current page is null, we're at the end
            // Otherwise, increment the page
            val nextKey = when(page) {
                null -> null
                else -> page + 1
            }
            LoadResult.Page(
                data = movies ?: listOf(),
                // If we're on the starting page, don't go backwards
                prevKey = if(position == TMDB_STARTING_PAGE) null else position -1,
                nextKey = nextKey
            )
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }
}