package com.sejarah.apps.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Nilai(
    val key: String,
    val nama: String,
    val nilai: String
) : Parcelable