package com.example.movies.data.model

import com.google.gson.annotations.SerializedName

data class DocsDto(
    @SerializedName("movie") val movie: MoviePreviewDto
)