package com.example.movies.data.model

import com.google.gson.annotations.SerializedName

data class VideosDto(
    @SerializedName("trailers") val trailers: List<TrailerDto>
)