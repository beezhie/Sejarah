package com.sejarah.apps.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Soal(
    val id: Int = 0,
    val pertanyaan: String,
    val jawaban: String,
    val pilihan: List<String>
) : Parcelable