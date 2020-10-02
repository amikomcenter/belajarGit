package com.zzulfiqor.amikomcentercrudkotlin.db

import android.provider.BaseColumns

internal class DatabaseContract {

    internal class BarangColumns : BaseColumns {
        companion object {
            const val TABLE_NAME = "barang"
            const val _ID = "_id"
            const val NAMA = "nama"
            const val JUMLAH = "stok"
        }
    }

}