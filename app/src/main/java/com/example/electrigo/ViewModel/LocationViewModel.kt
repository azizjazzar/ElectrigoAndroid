// LocationViewModel.kt

package com.example.electrigo.ViewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.electrigo.Model.LocationItem
import com.example.electrigo.Service.RetrofitInstance
import com.example.electrigo.utils.ApiResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LocationViewModel : ViewModel() {
    private val _jobResponseLocationData: MutableLiveData<ApiResult> =
        MutableLiveData(ApiResult.Empty)
    val jobResponseLocationData: LiveData<ApiResult>
        get() = _jobResponseLocationData

    private val _filteredLocations: MutableLiveData<List<LocationItem>> = MutableLiveData()
    val filteredLocations: LiveData<List<LocationItem>>
        get() = _filteredLocations

    fun getLocations() = viewModelScope.launch(Dispatchers.IO) {
        _jobResponseLocationData.postValue(ApiResult.Loading)
        RetrofitInstance.retrofitService.getLocation()
            .enqueue(object : Callback<List<LocationItem>> {
                override fun onResponse(
                    call: Call<List<LocationItem>>,
                    response: Response<List<LocationItem>>
                ) {
                    if (response.isSuccessful) {
                        val locationList = response.body()
                        _jobResponseLocationData.postValue(ApiResult.Success(locationList!!))
                    }
                }

                override fun onFailure(call: Call<List<LocationItem>>, t: Throwable) {
                    _jobResponseLocationData.postValue(ApiResult.Failure(t))
                }
            })
    }

    // LocationViewModel.kt

    fun searchLocations(query: String) {
        val allLocations = (_jobResponseLocationData.value as? ApiResult.Success)?.data
        if (allLocations is List<*>) {
            val locationList = allLocations.filterIsInstance<LocationItem>()
            val filteredList = locationList.filter { location ->
                location.adresse?.contains(query, ignoreCase = true) == true ||
                        location.cityname?.contains(query, ignoreCase = true) == true
            }
            _filteredLocations.postValue(filteredList)
        }
    }

    fun filterLocationsByType(type: String) {
        val allLocations = (_jobResponseLocationData.value as? ApiResult.Success)?.data

        if (allLocations is List<*>) {
            val locationList = allLocations.filterIsInstance<LocationItem>()
            val filteredList = locationList.filter { location ->
                location.typelocation.equals(type, ignoreCase = true)
            }
            _filteredLocations.postValue(filteredList)
        }
    }


    fun resetFilter() {
        val allLocations = (_jobResponseLocationData.value as? ApiResult.Success)?.data
        if (allLocations is List<*>) {
            val locationList = allLocations.filterIsInstance<LocationItem>()
            _filteredLocations.postValue(locationList)
        }
    }



}
