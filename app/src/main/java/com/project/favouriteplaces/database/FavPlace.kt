package com.project.favouriteplaces.database

import android.os.Parcel
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "place")
data class FavPlace(
    @PrimaryKey(autoGenerate = true) var placeId: Long?,
    @ColumnInfo(name = "placetitle") var placeTitle: String,
    @ColumnInfo(name = "placedescription") var placeDescription: String,
    @ColumnInfo(name = "placelocation") var placeLocation: String,
    @ColumnInfo(name = "placeimage") var placeImage: String,
    @ColumnInfo(name = "placelatitude") var placeLatitude: Double,
    @ColumnInfo(name = "placelongitude") var placeLongitude: Double
) : Serializable

