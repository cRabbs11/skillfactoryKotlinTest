package com.ekochkov.skillfactorykotlintest.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.ekochkov.skillfactorykotlintest.data.AppDataBase
import com.ekochkov.skillfactorykotlintest.data.entity.Film

@Dao
interface FilmDao {

    @Query("SELECT * FROM ${AppDataBase.CASHED_FILMS_TABLE_NAME}")
    fun getAllFilms(): List<Film>

    @Insert
    fun insertFilms(list: List<Film>)

    @Insert
    fun insertFilm(film: Film)
}