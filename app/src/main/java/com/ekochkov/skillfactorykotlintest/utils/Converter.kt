package com.ekochkov.skillfactorykotlintest.utils

import com.ekochkov.skillfactorykotlintest.data.entity.TmdbFilm
import com.ekochkov.skillfactorykotlintest.domain.Film

object Converter {

    fun convertTmdbListToDTOList(list: List<TmdbFilm>?) : List<Film> {
        var result = arrayListOf<Film>()
        list?.forEach {
            result.add(Film(
                    title = it.title,
                    poster = it.poster_path,
                    descr = it.overview,
                    isInFav = false,
                    rating = (it.vote_average*10).toInt()))
        }
        return result
    }
}