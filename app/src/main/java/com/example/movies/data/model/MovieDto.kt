package com.example.movies.data.model

import com.google.gson.annotations.SerializedName

data class MovieDto(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String?,
    @SerializedName("alternativeName") val alternativeName: String?,
    @SerializedName("year") val year: Int,
    @SerializedName("poster") val poster: PosterDto?,
    @SerializedName("rating") val rating: RatingDto,
)