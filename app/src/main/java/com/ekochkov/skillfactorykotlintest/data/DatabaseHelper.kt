package com.ekochkov.skillfactorykotlintest.data

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL("CREATE TABLE $TABLE_NAME (" +
                "$COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "$COLUMN_TITLE TEXT, " +
                "$COLUMN_DESCR TEXT, " +
                "$COLUMN_POSTER TEXT, " +
                "$COLUMN_RATING INTEGER, " +
                "$COLUMN_IN_FAV INTEGER);")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }

    companion object {
        private const val DATABASE_NAME = "films.db"
        private const val DATABASE_VERSION = 1

        const val TABLE_NAME = "films"
        const val COLUMN_ID = "_id"
        const val COLUMN_TITLE = "title"
        const val COLUMN_DESCR = "descr"
        const val COLUMN_POSTER = "poster"
        const val COLUMN_RATING = "rating"
        const val COLUMN_IN_FAV = "favorites"
    }

}