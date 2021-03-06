package com.ekochkov.skillfactorykotlintest

import android.os.Parcelable

import kotlinx.android.parcel.Parcelize


@Parcelize
data class Film(val title: String, val poster: Int, val descr: String) : Parcelable