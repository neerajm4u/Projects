package com.example.location.Repository

import android.location.Location
import com.example.location.Model.LocationModel
import com.example.location.db.LocationDatabase
import com.example.location.db.LocationMenuEntity

class Repository (val locationModel: LocationModel , val db:LocationDatabase){
//    suspend fun locationProvider(): Location? {
//       // return locationModel.getCurrentLocation(l)
//    }


    fun currLocation():Location{
        return locationModel.currLocation
    }
    fun setCurrLocation(loc: Location){
        locationModel.currLocation = loc
    }

    fun currStoreLocation():Location{
        return locationModel.storeLocation
    }
    fun setCurrStoreLocation(loc: Location){
        locationModel.storeLocation = loc
    }

    fun currVeg():Double{
        return locationModel.veg
    }
    fun setCurrVeg(v: Double){
        locationModel.veg = v
    }
    fun currFlour():Double{
        return locationModel.flour
    }
    fun setCurrFlour(v: Double){
        locationModel.flour = v
    }
    fun currCheese():Double{
        return locationModel.cheese
    }
    fun setCurrCheese(v: Double){
        locationModel.cheese = v
    }

    suspend fun inserToDb(v: LocationMenuEntity){
        db.locationMenuDao().insert(v)
    }

    suspend fun getLastDbEntry(): LocationMenuEntity?{
       return  db.locationMenuDao().getLatestEntry()
    }



//    fun distanceProvider(loc: Location): Float? {
//        return locationModel.getDistance(loc)
//    }
}