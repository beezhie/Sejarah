package com.sejarah.apps.data

import android.content.Intent
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Menu(
    var nama: String,
    var gambar: Int,
    var background: Int,
    var posisi: Int,
    var navigate: Intent?,
    var source: Int = -1
) : Parcelable
