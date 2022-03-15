package com.ekochkov.skillfactorykotlintest.data

import com.ekochkov.skillfactorykotlintest.data.dao.FilmDao
import com.ekochkov.skillfactorykotlintest.data.entity.Film
import java.util.concurrent.Executors

class FilmRepository(private val filmDao: FilmDao) {

    fun putFilmInDB(film: Film) {
        Executors.newSingleThreadExecutor().execute {
            filmDao.insertFilm(film)
        }
    }

    fun getAllFilmsFromDB(): List<Film> {
        return filmDao.getAllFilms()
    }

    fun updateInFavFilmInDB(film: Film) {
        Executors.newSingleThreadExecutor().execute {
            filmDao.updateFilm(film)
        }
    }

    fun deleteFilmFromDB(film: Film) {
        Executors.newSingleThreadExecutor().execute {
            filmDao.deleteFilm(film)
        }
    }

    fun getInFavFilmsFromDB(): List<Film> {
        return filmDao.getFilmsInFav(true)
    }
}