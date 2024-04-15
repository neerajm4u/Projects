package com.example.location.db

import android.location.Location
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName =  "location_menu")
data class LocationMenuEntity (

    @ColumnInfo(name = "veg") val veg:Double ,
    @ColumnInfo(name = "cheese")  val cheese:Double ,
    @ColumnInfo(name = "flour") val flour:Double
){

    @PrimaryKey(autoGenerate = true) var uid:Int=0
}