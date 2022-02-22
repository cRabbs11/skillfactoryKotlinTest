package com.ekochkov.skillfactorykotlintest.domain

import com.ekochkov.skillfactorykotlintest.data.FilmRepository

class Interactor(private val repository: FilmRepository) {
    fun getFilmsDB(): List<Film> = repository.filmList
}