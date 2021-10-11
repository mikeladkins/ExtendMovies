package com.madkins.extendmovies.model

import com.google.gson.annotations.SerializedName

class Movie{
    var id: Int = 0
    var title: String = ""
    var overview: String = ""
    var revenue: Int = 0
    @SerializedName("release_date") var releaseDate: String = ""
    @SerializedName("poster_path") var posterUrl: String = ""
    @SerializedName("backdrop_path") var backdropUrl: String = ""
}