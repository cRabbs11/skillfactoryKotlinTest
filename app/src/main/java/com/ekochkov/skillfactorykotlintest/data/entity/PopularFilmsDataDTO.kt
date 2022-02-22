package com.ekochkov.skillfactorykotlintest.data.entity

import com.ekochkov.skillfactorykotlintest.utils.TmdbApiConstants.SERIALIZED_NAME_PAGE
import com.ekochkov.skillfactorykotlintest.utils.TmdbApiConstants.SERIALIZED_NAME_RESULTS
import com.ekochkov.skillfactorykotlintest.utils.TmdbApiConstants.SERIALIZED_NAME_TOTAL_PAGES
import com.ekochkov.skillfactorykotlintest.utils.TmdbApiConstants.SERIALIZED_NAME_TOTAL_RESULTS
import com.google.gson.annotations.SerializedName

data class PopularFilmsDataDTO(
    @SerializedName(SERIALIZED_NAME_PAGE)
    val page: Int,
    @SerializedName(SERIALIZED_NAME_RESULTS)
    val tmdbFilms: List<TmdbFilm>,
    @SerializedName(SERIALIZED_NAME_TOTAL_PAGES)
    val total_pages: Int,
    @SerializedName(SERIALIZED_NAME_TOTAL_RESULTS)
    val total_results: Int
)