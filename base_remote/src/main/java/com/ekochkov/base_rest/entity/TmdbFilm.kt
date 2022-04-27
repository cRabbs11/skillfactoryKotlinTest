package com.ekochkov.base_rest.entity

import com.ekochkov.base_rest.TmdbApiConstants.SERIALIZED_NAME_ADULT
import com.ekochkov.base_rest.TmdbApiConstants.SERIALIZED_NAME_BACKDROP_PATH
import com.ekochkov.base_rest.TmdbApiConstants.SERIALIZED_NAME_GENRE_IDS
import com.ekochkov.base_rest.TmdbApiConstants.SERIALIZED_NAME_ID
import com.ekochkov.base_rest.TmdbApiConstants.SERIALIZED_NAME_ORIGINAL_LANGUAGE
import com.ekochkov.base_rest.TmdbApiConstants.SERIALIZED_NAME_ORIGINAL_TITLE
import com.ekochkov.base_rest.TmdbApiConstants.SERIALIZED_NAME_OVERVIEW
import com.ekochkov.base_rest.TmdbApiConstants.SERIALIZED_NAME_POPULARITY
import com.ekochkov.base_rest.TmdbApiConstants.SERIALIZED_NAME_POSTER_PATH
import com.ekochkov.base_rest.TmdbApiConstants.SERIALIZED_NAME_RELEASE_DATE
import com.ekochkov.base_rest.TmdbApiConstants.SERIALIZED_NAME_TITLE
import com.ekochkov.base_rest.TmdbApiConstants.SERIALIZED_NAME_VIDEO
import com.ekochkov.base_rest.TmdbApiConstants.SERIALIZED_NAME_VOTE_AVERAGE
import com.ekochkov.base_rest.TmdbApiConstants.SERIALIZED_NAME_VOTE_COUNT
import com.google.gson.annotations.SerializedName

data class TmdbFilm(
    @SerializedName(SERIALIZED_NAME_ADULT)
    val adult: Boolean,
    @SerializedName(SERIALIZED_NAME_BACKDROP_PATH)
    val backdrop_path: String,
    @SerializedName(SERIALIZED_NAME_GENRE_IDS)
    val genre_ids: List<Int>,
    @SerializedName(SERIALIZED_NAME_ID)
    val id: Int,
    @SerializedName(SERIALIZED_NAME_ORIGINAL_LANGUAGE)
    val original_language: String,
    @SerializedName(SERIALIZED_NAME_ORIGINAL_TITLE)
    val original_title: String,
    @SerializedName(SERIALIZED_NAME_OVERVIEW)
    val overview: String,
    @SerializedName(SERIALIZED_NAME_POPULARITY)
    val popularity: Double,
    @SerializedName(SERIALIZED_NAME_POSTER_PATH)
    val poster_path: String?,
    @SerializedName(SERIALIZED_NAME_RELEASE_DATE)
    val release_date: String,
    @SerializedName(SERIALIZED_NAME_TITLE)
    val title: String,
    @SerializedName(SERIALIZED_NAME_VIDEO)
    val video: Boolean,
    @SerializedName(SERIALIZED_NAME_VOTE_AVERAGE)
    val vote_average: Double,
    @SerializedName(SERIALIZED_NAME_VOTE_COUNT)
    val vote_count: Int
)