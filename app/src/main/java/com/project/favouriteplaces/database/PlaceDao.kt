package com.project.favouriteplaces.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface PlaceDao {
    @Query("SELECT * FROM place")
    fun getPlaces(): List<Place>

    @Query("SELECT * FROM place WHERE placetitle = :title")
    fun getPlaceByTitle(title: String): List<Place>

    @Insert
    fun insertPlace(vararg places: Place)

    @Delete
    fun deletePlace(place: Place)

    @Query("DELETE FROM place")
    fun deleteAllPlaces()
}