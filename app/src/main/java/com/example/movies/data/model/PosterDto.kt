package com.example.movies.data.model

import com.google.gson.annotations.SerializedName

data class PosterDto(
    @SerializedName("url") val url: String,
    @SerializedName("previewUrl") val previewUrl: String,
)