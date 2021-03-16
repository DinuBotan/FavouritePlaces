package com.project.favouriteplaces.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "place")
data class Place(
    @PrimaryKey(autoGenerate = true) var placeId: Long?,
    @ColumnInfo(name = "placetitle") var placeTitle: String,
    @ColumnInfo(name = "placedescription") var placeDescription: String,
    @ColumnInfo(name = "placelocation") var placeLocation: String
)
