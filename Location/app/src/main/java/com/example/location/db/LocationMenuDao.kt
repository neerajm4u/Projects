package com.example.location.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface LocationMenuDao {

    @Query("SELECT * FROM location_menu WHERE uid = (SELECT MAX(uid) FROM location_menu )")
    fun getLatestEntry(): LocationMenuEntity?

    @Insert
    fun insert(v : LocationMenuEntity)


}