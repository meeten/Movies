package com.example.movies.data.model

import com.google.gson.annotations.SerializedName

data class MoviesDto(
    @SerializedName("docs") val docsDto: List<DocsDto>,
    @SerializedName("next") val next: String,
    @SerializedName("hasNext") val hasNext: Boolean,
)