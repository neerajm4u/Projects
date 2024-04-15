package com.example.location

import android.app.Application
import android.location.Location
import androidx.lifecycle.Observer
import com.example.location.DomainUtil.LocationUtil
import com.example.location.Model.LocationModel
import com.example.location.Repository.Repository

class MyApplication: Application() {

    lateinit var locModel: LocationModel
    lateinit var locationUtil: LocationUtil
    lateinit var repository: Repository
    lateinit var observer: Observer<Location>



    override fun onCreate() {
        super.onCreate()

//        locationUtil = LocationUtil(this)
//        try {
//            locationUtil.updateCurrentLocation()
//            appLocationUtilAddObserver()
//        observer = object: Observer<Location>{
//            override fun onChanged(it: Location) {
//                locModel = LocationModel(it , it , 0.0 ,0.0 , 0.0)
//                repository = Repository(locModel)
//            }
//
//                   }
//        }catch (e: Exception){
//
//
//        }
//
//
//
//
////        locationUtil.currLocation.observeForever {
////            locModel = LocationModel(it , it , 0.0 ,0.0 , 0.0)
////            repository = Repository(locModel)
////        }


    }

//    fun appLocationUtilAddObserver(){
//        locationUtil.currLocation.observeForever {
//            locModel = LocationModel(it , it , 0.0 ,0.0 , 0.0)
//            repository = Repository(locModel)
//        }
//    }
}