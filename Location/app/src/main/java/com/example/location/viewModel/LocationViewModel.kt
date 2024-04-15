package com.example.location.viewModel

import com.example.location.MyApplication
import android.location.Location
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.location.DomainUtil.LocationUtil
import com.example.location.Repository.Repository
import com.example.location.db.LocationMenuEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LocationViewModel(val repository: Repository) : ViewModel() {
    var cheese = MutableLiveData<Double>()
    var flour = MutableLiveData<Double>()
    var veg  = MutableLiveData<Double>()
    var currLocation = MutableLiveData<Location>()
    var storeLocation = MutableLiveData<Location>()
    var currDistanceFromStore = MutableLiveData<Double>()
      var lastEntryDb = MutableLiveData<String>()


    init {
        cheese.value = repository.currCheese()
        flour.value = repository.currFlour()
        veg.value = repository.currVeg()
        currLocation.value = repository.currLocation()
        storeLocation.value = repository.currStoreLocation()


        viewModelScope.launch(Dispatchers.IO) {
            val v = repository.getLastDbEntry().toString()
            val temp:String = if(repository.getLastDbEntry().toString().length>18)
                            repository.getLastDbEntry().toString().substring(18)
                       else "no entry"
            lastEntryDb.postValue( "Last DB entry: " + temp) }
    }

    fun updateCurrentDistanceFromStore(d:Double){
        currDistanceFromStore.value = d
    }

    fun currLocation():Location{
        return repository.currLocation()
    }
    fun setCurrLocation(loc: Location){
        currLocation.value = loc
        repository.setCurrLocation(loc)
    }

    fun currStoreLocation():Location{
        return repository.currStoreLocation()
    }
    fun setCurrStoreLocation(loc: Location){
        storeLocation.value = loc
        repository.setCurrStoreLocation(loc)
    }

    fun currVeg():Double{
        return repository.currVeg()
    }
    fun setCurrVeg(v: Double){
        veg.value = v
        repository.setCurrVeg(v)
    }
    fun currFlour():Double{

        return repository.currFlour()
    }
    fun setCurrFlour(v: Double){
        flour.value = v
        repository.setCurrFlour( v)
    }
    fun currCheese():Double{
        return repository.currCheese()
    }
    fun setCurrCheese(v: Double){
        cheese.value = v
        repository.setCurrCheese(v)
    }


    suspend fun inserToDb(v: LocationMenuEntity){
        withContext(Dispatchers.Main){
            lastEntryDb.value = "Last DB entry: "+ v.toString().substring(18)
        }

        repository.inserToDb(v)
    }

    suspend fun getLastDbEntry(): LocationMenuEntity?{
       return repository.getLastDbEntry()
    }





    // Define ViewModel factory in a companion object
    companion object {

        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {

            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(
                modelClass: Class<T>,
                extras: CreationExtras
            ): T {
                val application = checkNotNull(extras[APPLICATION_KEY])
                val locationUtil = LocationUtil( application)
                locationUtil.updateCurrentLocation()
                return LocationViewModel(
                    (application as MyApplication).repository

                    ) as T
            }
        }
    }




}