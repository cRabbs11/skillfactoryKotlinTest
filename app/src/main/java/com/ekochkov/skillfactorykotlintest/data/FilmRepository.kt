package com.ekochkov.skillfactorykotlintest.data

import androidx.lifecycle.LiveData
import com.ekochkov.skillfactorykotlintest.data.dao.FilmDao
import com.ekochkov.skillfactorykotlintest.data.entity.Film
import io.reactivex.rxjava3.core.Observable
import java.util.concurrent.Executors

class FilmRepository(private val filmDao: FilmDao) {

    fun putFilmsInDB(list: List<Film>) {
        Executors.newSingleThreadExecutor().execute {
            filmDao.insertFilms(list)
        }
    }

    fun putFilmInDB(film: Film) {
        Executors.newSingleThreadExecutor().execute {
            filmDao.insertFilm(film)
        }
    }

    fun getAllFilmsFromDB(): LiveData<List<Film>> {
        return filmDao.getAllFilms()
    }

    fun getAllFilmsFromBDAsObservable(): Observable<List<Film>> {
        return filmDao.getAllFilmsAsObservable()
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

    fun deleteAllFilmsInDB() {
        Executors.newSingleThreadExecutor().execute {
            filmDao.deleteAllFilms()
        }
    }
}