package com.zzulfiqor.amikomcentercrudkotlin.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.zzulfiqor.amikomcentercrudkotlin.db.DatabaseContract.BarangColumns.Companion.TABLE_NAME

internal class DatabaseHelper (context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "dbamc"
        private const val DATABASE_VERSION = 2

        //table zuhair
        private val SQL_CREATE_TABLE_BARANG = "CREATE TABLE $TABLE_NAME" +
                " (${DatabaseContract.BarangColumns._ID} INTEGER PRIMARY KEY AUTOINCREMENT," +
                " ${DatabaseContract.BarangColumns.NAMA} TEXT NOT NULL," +
                " ${DatabaseContract.BarangColumns.JUMLAH} INTEGER NOT NULL)"
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(SQL_CREATE_TABLE_BARANG)
    }
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

}