package com.ekochkov.skillfactorykotlintest.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ekochkov.skillfactorykotlintest.data.dao.FilmDao
import com.ekochkov.skillfactorykotlintest.data.entity.Film

@Database(entities = [Film::class], version = 1, exportSchema = true)
abstract class AppDataBase: RoomDatabase() {
    abstract fun filmDao(): FilmDao

    companion object {
        const val CASHED_FILMS_TABLE_NAME = "cashed_films_table"
    }
}