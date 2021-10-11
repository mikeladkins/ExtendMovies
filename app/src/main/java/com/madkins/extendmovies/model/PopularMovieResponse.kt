package com.madkins.extendmovies.model

class PopularMovieResponse {
    var page: Int = 0
    lateinit var results: List<PopularMovie>
}