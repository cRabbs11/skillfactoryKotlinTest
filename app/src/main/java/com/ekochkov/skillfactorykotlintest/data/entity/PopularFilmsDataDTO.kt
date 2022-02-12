package com.ekochkov.skillfactorykotlintest.data.entity

import com.google.gson.annotations.SerializedName

data class PopularFilmsDataDTO(
    @SerializedName("page")
    val page: Int,
    @SerializedName("results")
    val tmdbFilms: List<TmdbFilm>,
    @SerializedName("total_pages")
    val total_pages: Int,
    @SerializedName("total_results")
    val total_results: Int
)