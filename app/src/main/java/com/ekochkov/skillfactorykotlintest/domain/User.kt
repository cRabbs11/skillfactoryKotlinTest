package com.ekochkov.skillfactorykotlintest.domain

import android.os.Parcelable

import kotlinx.android.parcel.Parcelize


@Parcelize
data class User(val name: String, var cash: Int) : Parcelable