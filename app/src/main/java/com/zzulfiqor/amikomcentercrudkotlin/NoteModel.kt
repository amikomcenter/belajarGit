package com.zzulfiqor.amikomcentercrudkotlin

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "notes")
@Parcelize
data class NoteModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    var name: String = "",
    var desc: String = ""
): Parcelable