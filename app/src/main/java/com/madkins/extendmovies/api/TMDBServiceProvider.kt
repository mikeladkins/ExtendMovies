package com.madkins.extendmovies.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

// Provides a singular retrofit object that we can use to implement our service to make API calls
class TMDBServiceProvider {
    companion object {
        private const val BASE_URL = "https://api.themoviedb.org"
        private var retrofit: Retrofit? = null

        fun getRetrofit(): Retrofit {
            when(retrofit) {
                null -> retrofit = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            }
            return retrofit as Retrofit
        }
    }
}