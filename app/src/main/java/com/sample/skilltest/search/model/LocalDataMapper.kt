package com.sample.skilltest.search.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class LocalDataMapper(
    val id: String,
    val imageUrl: String,
    val imageHeight: Int,
    val imageWidth: Int
) : Parcelable