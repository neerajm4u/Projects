package com.example.location

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.FrameLayout
import android.widget.Switch
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import androidx.room.Room
import com.example.location.DomainUtil.LocationUtil
import com.example.location.Model.LocationModel
import com.example.location.Repository.Repository
import com.example.location.viewModel.LocationViewModel
import com.example.location.databinding.ActivityMainBinding
import com.example.location.db.LocationDatabase
import com.example.location.db.LocationMenuEntity
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    val TAG = "Neeraj"

    // private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var viewModel: LocationViewModel
    private lateinit var repository: Repository
    private lateinit var locModel: LocationModel
    lateinit var locationUtil: LocationUtil
    lateinit var binding: ActivityMainBinding
    lateinit var app: MyApplication
    lateinit var db:LocationDatabase

   var flagInitialized:Boolean = false
//
//    val progressBar: FrameLayout
//        get() = findViewById(R.id.progressBar)
//    val latitudeText: TextView
//        get() = findViewById(R.id.latitude)
//    val altitudeText: TextView
//        get() = findViewById(R.id.longitude)
//    val currDistanceText: TextView
//        get() = findViewById(R.id.distance)
//    val currLocSwitch: Switch
//        get() = findViewById(R.id.currentSwitch)
//    val delhiLocSwitch: Switch
//        get() = findViewById(R.id.delhiSwitch)
//    val vegTextInputEditText: TextInputEditText
//        get() = findViewById(R.id.vegText)
//    val cheeseTextInputEditText: TextInputEditText
//        get() = findViewById(R.id.cheeseText)
//    val flourTextInputEditText: TextInputEditText
//        get() = findViewById(R.id.flourText)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()


        binding =
            DataBindingUtil.setContentView(this, R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        binding.lifecycleOwner = this

        app = (application as MyApplication)


        if (this.checkSelfPermission(

                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && this.checkSelfPermission(

                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            ActivityCompat.
            requestPermissions(
                this,
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                1);

            //app.appLocationUtilAddObserver()

        }

        locationUtil = LocationUtil(this)
        locationUtil.updateCurrentLocation()

       binding.progressBar.visibility = View.VISIBLE
        getWindow().setFlags(
            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
        )


        locationUtil.currLocation.observe(this){
            app.locModel = LocationModel(it , it , 0.0 ,0.0 , 0.0)
            db = Room.databaseBuilder(
                applicationContext,
                LocationDatabase::class.java, "database-name"
            ).build()

            app.repository =  Repository(app.locModel , db)
            val v: LocationViewModel by viewModels { LocationViewModel.Factory }
            viewModel = v
            viewModel.currLocation.value = it
            viewModel.currDistanceFromStore.value =
                viewModel.storeLocation.value?.let { it1 -> it.distanceTo(it1).toDouble() }
            if(viewModel.currDistanceFromStore.value!! > 5){
                binding.cardDistance .setCardBackgroundColor(Color.RED)
                binding.distance.setTextColor(Color.BLACK)
            }else{
                binding.cardDistance .setCardBackgroundColor(Color.GREEN)
                binding.distance.setTextColor(Color.BLACK)
            }


            binding.progressBar.visibility = View.GONE
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
           // Log.d(TAG, app.locationUtil.currLocation.value?.latitude.toString())

            binding.viewmodel = v as LocationViewModel
            if(!flagInitialized){
                initProcess()

            }

            flagInitialized = true
        }


    }

    private fun initProcess() {
        Log.d(TAG , "init Process calleds")
       switchLogic()
       submitLogic()
    }

    private fun submitLogic() {
        binding.submit.setOnClickListener{
//            locationUtil.updateCurrentLocation()
//
//            binding.progressBar.visibility = View.VISIBLE
//            getWindow().setFlags(
//                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
//                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
//
//            )

//            locationUtil.currLocation.observe(this){
                if(viewModel.currDistanceFromStore.value!! <5) {

                    if(binding.vegText.text.toString() != "" &&
                        binding.cheeseText.text.toString() != "" &&
                        binding.flourText.text.toString() != "" ){
                        Log.d(TAG , "enterreeddd   " + binding.vegText.text.toString())

                        GlobalScope.launch {

                            viewModel.inserToDb(LocationMenuEntity(
                                binding.vegText.text.toString().toDouble(),
                                binding.cheeseText.text.toString().toDouble(),
                                binding.flourText.text.toString().toDouble()
                            ))
                        }
                    }else

                    binding.cardDistance.setCardBackgroundColor(Color.GREEN)

                    Log.d(TAG , "chese - ${binding.cheeseText.text}")

                    Log.d(TAG , "fl0ur - ${ binding.flourText.text}")

                    Log.d(TAG , "veg - ${binding.vegText.text}")
                    binding.cheeseText.setText(null)
                    binding.flourText.setText(null)
                    binding.vegText.setText(null)


                }else{
                    binding.cardDistance.setCardBackgroundColor(Color.RED)
                }
//                binding.progressBar.visibility = View.GONE
//                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
//                locationUtil.removeListener()
//                locationUtil.removeLiveDataObserver(this)
//            }

        }
    }

    private fun switchLogic() {
        binding.currentSwitch.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                //fix store location
                //  binding.progressBar.visibility = View.VISIBLE
                // getWindow().setFlags(
                //    WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                //  WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
                //  )
                if(binding.delhiSwitch.isChecked  ){
                    binding.delhiSwitch.isChecked = false
//                    locationUtil.currLocation.removeObservers(this) //no need as else of delhi will be called
//                    locationUtil.removeListener()
                }
                Log.d(TAG , "delhi switched off ")
               // binding.delhiSwitch.isChecked = false
    //before call to update location and adding update callback remove all listener from location update and live data
    //observer is added later below
                locationUtil.removeListener()
                locationUtil.currLocation.removeObservers(this)//removed all
                locationUtil.updateCurrentLocation()


                GlobalScope.launch{

                withContext(Dispatchers.Main){
                    binding.progressBar.visibility = View.VISIBLE
                    getWindow().setFlags(
                        WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                        WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
                    )
                }

                //delay(1000)
                }


              //  viewModel.storeLocation.value =   locationUtil.currLocation.value
                locationUtil.currLocation.value?.let { viewModel.setCurrStoreLocation(it) }
                //locationUtil.currLocation.removeObservers(this) ///removed all called already
                locationUtil.currLocation.observe(this) {

                    // viewModel.storeLocation.value = it
//                    viewModel.currDistanceFromStore.value =
//                        viewModel.storeLocation.value?.let { it1 -> it.distanceTo(it1).toDouble() }
                    viewModel.storeLocation.value?.let { it1 -> it.distanceTo(it1).toDouble() }
                        ?.let { it2 -> viewModel.updateCurrentDistanceFromStore(it2) }
                    if(viewModel.currDistanceFromStore.value!! > 5){
                        binding.cardDistance .setCardBackgroundColor(Color.RED)
                        binding.distance.setTextColor(Color.BLACK)
                    }else{
                        binding.cardDistance .setCardBackgroundColor(Color.GREEN)
                        binding.distance.setTextColor(Color.BLACK)
                    }


                    binding.progressBar.visibility = View.GONE
                    getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                    Log.d(TAG, "from curr observer"+locationUtil.currLocation.value?.latitude.toString())

                }

            }else{
                Log.d(TAG,   "current  observer removed")
    // removed location update callback and live data listener
                locationUtil.removeListener()
                locationUtil.currLocation.removeObservers(this)

            }
        }


        binding.delhiSwitch.setOnCheckedChangeListener { buttonView, isChecked ->
            if(isChecked){
                Log.d(TAG , "curr  switched off ")
                if(binding.currentSwitch.isChecked  ){
                    binding.currentSwitch.isChecked = false
                    locationUtil.currLocation.removeObservers(this)
                    locationUtil.removeListener()

                }

    //before call to update location and adding update callback remove all listener from location update and live datqa
                locationUtil.removeListener()
                locationUtil.currLocation.removeObservers(this)//removed all
                locationUtil.updateCurrentLocation()

                GlobalScope.launch{

                    withContext(Dispatchers.Main){
                        binding.progressBar.visibility = View.VISIBLE
                        getWindow().setFlags(
                            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
                        )
                    }

                    //delay(1000)
                }

        //setting current location to random after updating current lcoation instance
                var l = locationUtil.currLocation.value
                l?.latitude   = 30.0
                l?.longitude = 20.0
                if (l != null) {
                    viewModel.setCurrStoreLocation(l)
                }

                 // locationUtil.currLocation.removeObservers(this)//removed all
                 //  locationUtil.removeListener()
                locationUtil.currLocation.observe(this) {


        // observer to update current distance from store location fixed as random
                    // viewModel.storeLocation.value = it
                    //viewModel.currDistanceFromStore.value =
                    //   viewModel.storeLocation.value?.let { it1 -> it.distanceTo(it1).toDouble() }
                    viewModel.storeLocation.value?.let { it1 -> it.distanceTo(it1).toDouble() }
                        ?.let { it2 -> viewModel.updateCurrentDistanceFromStore(it2) }
                    if(viewModel.currDistanceFromStore.value!! > 5){
                        binding.cardDistance .setCardBackgroundColor(Color.RED)
                        binding.distance.setTextColor(Color.BLACK)
                    }else{
                        binding.cardDistance .setCardBackgroundColor(Color.GREEN)
                        binding.distance.setTextColor(Color.BLACK)
                    }

                    binding.progressBar.visibility = View.GONE
                    getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                    Log.d(TAG,  "from delhi observer"+locationUtil.currLocation.value?.latitude.toString())
                }

            }else{
                Log.d(TAG,   "delhi observer removed")
     // removed location update callback and live data listener
                locationUtil.removeListener()
                locationUtil.currLocation.removeObservers(this)

            }

            }
        }
    }
