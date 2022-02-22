package com.ekochkov.skillfactorykotlintest.domain

import android.os.Parcelable

import kotlinx.android.parcel.Parcelize


@Parcelize
data class Film(
        val title: String,
        val poster: String,
        val descr: String,
        var isInFav: Boolean = false,
        var rating: Int = 0
) : Parcelable