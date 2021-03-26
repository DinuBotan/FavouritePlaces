package com.project.favouriteplaces.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface PlaceDao {
    @Query("SELECT * FROM place")
    fun getPlaces(): List<FavPlace>

    @Query("SELECT * FROM place WHERE placetitle = :title")
    fun getPlaceByTitle(title: String): List<FavPlace>

    @Insert
    fun insertPlace(vararg favPlaces: FavPlace)

    @Delete
    fun deletePlace(favPlace: FavPlace)

    @Query("DELETE FROM place")
    fun deleteAllPlaces()
}