package com.ekochkov.skillfactorykotlintest.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.ekochkov.skillfactorykotlintest.data.AppDataBase
import com.ekochkov.skillfactorykotlintest.data.entity.Film

@Dao
interface FilmDao {

    @Query("SELECT * FROM ${AppDataBase.CASHED_FILMS_TABLE_NAME}")
    fun getAllFilms(): LiveData<List<Film>>

    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun insertFilms(list: List<Film>)

    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun insertFilm(film: Film)

    @Query("SELECT * FROM ${AppDataBase.CASHED_FILMS_TABLE_NAME} WHERE title LIKE:title")
    fun getFilmByTitle(title: String): Film?

    @Query("SELECT * FROM ${AppDataBase.CASHED_FILMS_TABLE_NAME} WHERE is_in_fav LIKE:inFavValue")
    fun getFilmsInFav(inFavValue: Boolean): List<Film>

    @Update
    fun updateFilm(film: Film)

    @Delete
    fun deleteFilm(film: Film)
}