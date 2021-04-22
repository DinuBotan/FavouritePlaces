package com.project.favouriteplaces.models

import android.os.Parcel
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.PrimaryKey
import com.project.favouriteplaces.database.FavPlace

data class FavPlaceModel (
        var placeId: Long?,
        var placeTitle: String?,
        var placeDescription: String?,
        var placeLocation: String?,
        var placeImage: String?,
        var placeLatitude: Double,
        var placeLongitude: Double
) : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readValue(Long::class.java.classLoader) as? Long,
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readDouble(),
            parcel.readDouble()) {
    }

    override fun writeToParcel(p0: Parcel?, p1: Int) {
        TODO("Not yet implemented")
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<FavPlaceModel> {
        override fun createFromParcel(parcel: Parcel): FavPlaceModel {
            return FavPlaceModel(parcel)
        }

        override fun newArray(size: Int): Array<FavPlaceModel?> {
            return arrayOfNulls(size)
        }
    }
}

