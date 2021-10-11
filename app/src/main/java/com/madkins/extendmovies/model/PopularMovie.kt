package com.madkins.extendmovies.model

import com.google.gson.annotations.SerializedName

class PopularMovie {
    var title: String = ""
    var overview: String = ""
    var id: Int = 0
    @SerializedName("poster_path") var posterUrl: String = ""
}