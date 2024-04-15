package com.example.location.DomainUtil

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Build
import android.os.Process
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat.requestPermissions
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData

class LocationUtil (val context: Context){

      var  currLocation:MutableLiveData<Location> = MutableLiveData<Location>()
    lateinit var locationManager: LocationManager
    lateinit var locationListener:LocationListener

    fun updateCurrentLocation()  {


        //location manager
       locationManager =   context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        //var currLoca:Location? = null

        //location listener
          locationListener = object : LocationListener {
            @RequiresApi(Build.VERSION_CODES.S)
            override fun onLocationChanged(location: Location) {
                    currLocation.postValue(location)
                   // locationManager.removeUpdates(this)

//                if(!location.isMock())
//                    Log.d(TAG ,   disTravelled.toString() + " dis "+location.longitude.toString() +"===== " +location.latitude.toString()+"--" +location.provider)
//                else Log.d(TAG ,  location.altitude.toString() +"==+=== " +location.latitude.toString())


            }

        }

        //request location
        if (context.checkSelfPermission(

                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && context.checkSelfPermission(

                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            (context as Activity).let{
                requestPermissions(
                    it,
                    arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                    1);
            }

        }
    removeListener()
    locationManager.requestLocationUpdates(
        LocationManager.FUSED_PROVIDER,
        10, 0F,
        locationListener)

    }

    fun removeListener(){
        locationManager.removeUpdates(locationListener)
    }

    fun removeLiveDataObserver(context:Context) {
        currLocation.removeObservers(context as LifecycleOwner)

    }

//    fun getDistanceBetweenLocations(a:Location , b:Location):Double{
//        return TODO("Provide the return value")
//    }

}