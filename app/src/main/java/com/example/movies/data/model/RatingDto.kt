package com.example.movies.data.model

import com.google.gson.annotations.SerializedName

data class RatingDto(
    @SerializedName("imdb") val imdb: Float
)