package com.ekochkov.skillfactorykotlintest.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.ekochkov.skillfactorykotlintest.data.entity.Film

@Dao
interface FilmDao {

    @Query("SELECT * FROM cashed_films_table")
    fun getAll() {}

    @Insert
    fun insertAll(list: List<Film>)
}