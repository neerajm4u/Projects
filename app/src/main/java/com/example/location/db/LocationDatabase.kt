package com.example.location.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [LocationMenuEntity::class] , version = 1)
abstract class  LocationDatabase :RoomDatabase(){
    abstract  fun locationMenuDao():LocationMenuDao
}