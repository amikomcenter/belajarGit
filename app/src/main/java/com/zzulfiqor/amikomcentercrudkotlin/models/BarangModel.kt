package com.zzulfiqor.amikomcentercrudkotlin.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class BarangModel(
    var id: Int = 0,
    var nama: String? = null,
    var jumlah: Int? = null
) : Parcelable