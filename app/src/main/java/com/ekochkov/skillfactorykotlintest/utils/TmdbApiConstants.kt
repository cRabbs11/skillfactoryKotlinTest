package com.ekochkov.skillfactorykotlintest.utils

object TmdbApiConstants {
    const val BASE_URL = "https://api.themoviedb.org/"
    const val IMAGES_URL = "https://image.tmdb.org/t/p/"

    //для PopularFilmsDataDTO
    const val SERIALIZED_NAME_PAGE = "page"
    const val SERIALIZED_NAME_RESULTS = "results"
    const val SERIALIZED_NAME_TOTAL_PAGES = "total_pages"
    const val SERIALIZED_NAME_TOTAL_RESULTS = "total_results"

    //для TmdbFilm
    const val SERIALIZED_NAME_ADULT = "adult"
    const val SERIALIZED_NAME_BACKDROP_PATH = "backdrop_path"
    const val SERIALIZED_NAME_GENRE_IDS = "genre_ids"
    const val SERIALIZED_NAME_ID = "id"
    const val SERIALIZED_NAME_ORIGINAL_LANGUAGE = "original_language"
    const val SERIALIZED_NAME_ORIGINAL_TITLE = "original_title"
    const val SERIALIZED_NAME_OVERVIEW = "overview"
    const val SERIALIZED_NAME_POPULARITY = "popularity"
    const val SERIALIZED_NAME_POSTER_PATH = "poster_path"
    const val SERIALIZED_NAME_RELEASE_DATE = "release_date"
    const val SERIALIZED_NAME_TITLE = "title"
    const val SERIALIZED_NAME_VIDEO = "video"
    const val SERIALIZED_NAME_VOTE_AVERAGE = "vote_average"
    const val SERIALIZED_NAME_VOTE_COUNT = "vote_count"

    //path для запроса getFilms
    const val FILM_LIST_TYPE_TOP_RATED = "top_rated"
    const val FILM_LIST_TYPE_UPCOMING = "upcoming"
    const val FILM_LIST_TYPE_NOW_PLAYING = "now_playing"
    const val FILM_LIST_TYPE_LATEST = "latest"
    const val FILM_LIST_TYPE_POPULAR = "popular"

}