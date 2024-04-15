package com.example.location.Model

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.util.Log
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
val TAG = "Neeraj"
data class LocationModel(

     var currLocation: Location,
     var storeLocation:Location,

    // var fusedLocationClient: FusedLocationProviderClient
    var flour :Double = 0.0,
    var veg:Double = 0.0,
    var cheese : Double = 0.0)


//    ){
//
//    suspend fun getCurrentLocation(): Location? {
//        //location manager
//        val locationManager =   context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
//        var currLoca:Location? = null
//
////        //location listener
////        val locationListener = object : LocationListener {
////            @RequiresApi(Build.VERSION_CODES.S)
////            override fun onLocationChanged(location: Location) {
////
////                var disTravelled:Float = 0F
////                currLoca = location
////
////                if(prevLocation!=null)
////                    disTravelled = location.distanceTo (prevLocation!!)
////                else{
////                    //lastLat =  location.latitude
////                   // lastLong = location.longitude
////                    prevLocation = location
////                }
////
////                if(!location.isMock())
////                    Log.d(TAG ,   disTravelled.toString() + " dis "+location.longitude.toString() +"===== " +location.latitude.toString()+"--" +location.provider)
////                else Log.d(TAG ,  location.altitude.toString() +"==+=== " +location.latitude.toString())
////
////
////            }
////
////        }
////
////        //request location
////        if (ActivityCompat.checkSelfPermission(
////                context,
////                Manifest.permission.ACCESS_FINE_LOCATION
////            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
////                context,
////                Manifest.permission.ACCESS_COARSE_LOCATION
////            ) != PackageManager.PERMISSION_GRANTED
////        ) {
////            // TODO: Consider calling
////            //    ActivityCompat#requestPermissions
////            ActivityCompat.requestPermissions(
////                context as Activity,
////                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
////                1);
////        }
////
////
////            locationManager.requestLocationUpdates(
////                LocationManager.NETWORK_PROVIDER,
////                500, 0F,
////                locationListener)
////            //delay(2000)
//
//
//
//
//        fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
//
//        if (ActivityCompat.checkSelfPermission(
//                context,
//                Manifest.permission.ACCESS_FINE_LOCATION
//            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
//                context,
//                Manifest.permission.ACCESS_COARSE_LOCATION
//            ) != PackageManager.PERMISSION_GRANTED
//        ) {
//            ActivityCompat.requestPermissions(
//                context as Activity,
//                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
//                1);
//        }
//        var newLoc:Location? = null
//        val v =  GlobalScope.async {
//
//            fusedLocationClient.lastLocation
//               .addOnSuccessListener()
//               { location : Location? ->
//                    if (location != null) {
//                        newLoc = location
//                        Log.d(TAG ,  location.longitude.toString() +" f===== " +location.latitude.toString()+"--" +location.provider)
//                    }
//
//                }
//
//        }
//      // wait()
//       // v.await()
//
//
//
//       // return currLoca
//       return newLoc
//    }
//    fun getDistance(loc: Location): Float? {
//        return  currLocation?.distanceTo (loc!!)
//    }
//
//}
