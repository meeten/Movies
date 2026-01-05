package com.example.movies.data.model

import com.google.gson.annotations.SerializedName

data class MoviePreviewDto(
    @SerializedName("id") val id: Int,
    @SerializedName("poster") val poster: PosterDto?,
    @SerializedName("rating") val rating: RatingDto,
)