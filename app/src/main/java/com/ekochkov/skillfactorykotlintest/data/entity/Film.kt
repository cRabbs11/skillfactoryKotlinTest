package com.ekochkov.skillfactorykotlintest.data.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

import kotlinx.android.parcel.Parcelize


@Parcelize
@Entity(tableName = "cashed_films_table")
data class Film(
        @PrimaryKey(autoGenerate = true) val id: Int = 0,
        @ColumnInfo(name = "title") val title: String,
        @ColumnInfo(name = "poster") val poster: String,
        @ColumnInfo(name = "descr") val descr: String,
        @ColumnInfo(name = "is_in_fav") var isInFav: Boolean = false,
        @ColumnInfo(name = "rating") var rating: Int = 0
) : Parcelable