package com.ekochkov.skillfactorykotlintest.data

import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import com.ekochkov.skillfactorykotlintest.domain.Film

class FilmRepository(dbHelper: DatabaseHelper) {

    private val sqlDb: SQLiteDatabase = dbHelper.writableDatabase

    fun putFilmInDB(film: Film) {
        if (!isFilmInBD(film)) {
            val cv = convertToCVfromFilm(film)
            sqlDb.insert(DatabaseHelper.TABLE_NAME, null, cv)
        }
    }

    fun getAllFilmsFromBD(): List<Film> {
        val films = arrayListOf<Film>()

        val cursor = sqlDb.rawQuery("SELECT * FROM ${DatabaseHelper.TABLE_NAME}", null)
        cursor.use {
            if (it.moveToFirst()) {
                do {
                    films.add(convertToFilmfromCursor(it))
                } while (it.moveToNext())
            }
        }

        return films
    }

    fun isFilmInBD(film: Film): Boolean {
        val cursor = sqlDb.rawQuery("SELECT * FROM ${DatabaseHelper.TABLE_NAME} WHERE ${DatabaseHelper.COLUMN_TITLE} = ?", arrayOf(film.title))
        var value: Boolean
        cursor.use {
            value = it.moveToFirst()
        }
        return value
    }

    private fun convertToCVfromFilm(film: Film): ContentValues {
        val cv = ContentValues()
        cv.put(DatabaseHelper.COLUMN_TITLE, film.title)
        cv.put(DatabaseHelper.COLUMN_DESCR, film.descr)
        cv.put(DatabaseHelper.COLUMN_POSTER, film.poster)
        cv.put(DatabaseHelper.COLUMN_RATING, film.rating)
        val isInFav = if (film.isInFav) { 1 } else { 0 }
        cv.put(DatabaseHelper.COLUMN_IN_FAV, isInFav)
        return cv
    }

    private fun convertToFilmfromCursor(cursor: Cursor): Film {
        val title = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_TITLE))
        val descr = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_DESCR))
        val poster = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_POSTER))
        val rating = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_RATING))
        val isInFav = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_IN_FAV))==1

        return Film(title = title,
                poster = poster,
                descr = descr,
                isInFav = isInFav,
                rating = rating)
    }
}