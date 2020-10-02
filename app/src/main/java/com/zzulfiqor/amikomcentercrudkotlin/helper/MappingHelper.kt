package com.zzulfiqor.amikomcentercrudkotlin.helper

import android.database.Cursor
import com.zzulfiqor.amikomcentercrudkotlin.db.DatabaseContract
import com.zzulfiqor.amikomcentercrudkotlin.models.BarangModel

object MappingHelper {

    fun mapCursorToArrayList(barangCursor: Cursor?): ArrayList<BarangModel> {
        val barangList = ArrayList<BarangModel>()
        barangCursor?.apply {
            while (moveToNext()) {
                val id = getInt(getColumnIndexOrThrow(DatabaseContract.BarangColumns._ID))
                val namaBarang = getString(getColumnIndexOrThrow(DatabaseContract.BarangColumns.NAMA))
                val jumlahBarang = getInt(getColumnIndexOrThrow(DatabaseContract.BarangColumns.JUMLAH))
                barangList.add(BarangModel(id, namaBarang, jumlahBarang))
            }
        }
        return barangList
    }

}