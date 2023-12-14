package com.example.electrigo.ViewModel

import android.widget.ImageView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bumptech.glide.Glide
import com.example.electrigo.Model.LocationItem
import com.example.electrigo.Model.Vehicule
import com.example.electrigo.Service.RetrofitInstance
import com.example.electrigo.utils.ApiResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class VehiculeViewModel :ViewModel(){

    private val _jobResponseVehiculeData: MutableLiveData<ApiResult> =
        MutableLiveData(ApiResult.Empty)
    val jobResponseVehiculeData: LiveData<ApiResult>
        get() = _jobResponseVehiculeData

    private val _filteredVehicules: MutableLiveData<List<Vehicule>> = MutableLiveData()
    val filteredLocations: LiveData<List<Vehicule>>
        get() = _filteredVehicules
    fun loadVehiculeImage(imageUrl: String, imageView: ImageView) {
        viewModelScope.launch(Dispatchers.Main) {
            Glide.with(imageView.context)
                .load(imageUrl)
                .override(310, 170) // Ajustez la taille souhaitée
                .into(imageView)
        }
    }

    fun getVehicule() = viewModelScope.launch(Dispatchers.IO) {
        _jobResponseVehiculeData.postValue(ApiResult.Loading)
        RetrofitInstance.retrofitService.getVehicule()
            .enqueue(object : Callback<List<Vehicule>> {
                override fun onResponse(
                    call: Call<List<Vehicule>>,
                    response: Response<List<Vehicule>>
                ) {
                    if (response.isSuccessful) {
                        val locationList = response.body()
                        _jobResponseVehiculeData.postValue(ApiResult.Success(locationList!!))
                    }
                }

                override fun onFailure(call: Call<List<Vehicule>>, t: Throwable) {
                    _jobResponseVehiculeData.postValue(ApiResult.Failure(t))
                }
            })
    }

    fun filterVehiculesByMarque(type: String) {
        // vérifie si la valeur obtenue est de type succes
        val allVehicule = (_jobResponseVehiculeData.value as? ApiResult.Success)?.data

        if (allVehicule is List<*>) {
            val vehiculeList = allVehicule.filterIsInstance<Vehicule>()
            val filteredList = vehiculeList.filter { vehicule ->
                vehicule.marque.equals(type, ignoreCase = true)
            }
            _filteredVehicules.postValue(filteredList)
        }
    }


    fun resetFilter() {
        val allvehicule = (_jobResponseVehiculeData.value as? ApiResult.Success)?.data
        if (allvehicule is List<*>) {
            val vehiculeList = allvehicule.filterIsInstance<Vehicule>()
            _filteredVehicules.postValue(vehiculeList)
        }
    }


    fun getDetailVehicule(vehiculeId: String?): LiveData<ApiResult> {
        val resultLiveData = MutableLiveData<ApiResult>()

        viewModelScope.launch(Dispatchers.IO) {
            if (vehiculeId != null) {
                resultLiveData.postValue(ApiResult.Loading)
                RetrofitInstance.retrofitService.getVehiculeDetails(vehiculeId)
                    .enqueue(object : Callback<Vehicule> {
                        override fun onResponse(call: Call<Vehicule>, response: Response<Vehicule>) {
                            if (response.isSuccessful) {
                                val vehiculeItem = response.body()
                                resultLiveData.postValue(ApiResult.Success(listOfNotNull(vehiculeItem)))
                            } else {
                                resultLiveData.postValue(ApiResult.Empty)
                            }
                        }

                        override fun onFailure(call: Call<Vehicule>, t: Throwable) {
                            resultLiveData.postValue(ApiResult.Failure(t))
                        }
                    })
            } else {
                resultLiveData.postValue(ApiResult.Empty)
            }
        }

        return resultLiveData
    }
}