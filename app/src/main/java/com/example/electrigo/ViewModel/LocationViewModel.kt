import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.electrigo.Model.LocationItem
import com.example.electrigo.Model.ReviewItem
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

    private val mapboxDirectionsApi = RetrofitInstance.mapboxDirectionsService

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
                        if (locationList != null) {
                            _jobResponseLocationData.postValue(ApiResult.Success(locationList))
                        } else {
                            _jobResponseLocationData.postValue(ApiResult.Empty)
                        }
                    } else {
                        Log.e("LocationViewModel", "Error: ${response.errorBody()?.string()}")
                        _jobResponseLocationData.postValue(ApiResult.Failure(Throwable(response.message())))
                    }
                }

                override fun onFailure(call: Call<List<LocationItem>>, t: Throwable) {
                    _jobResponseLocationData.postValue(ApiResult.Failure(t))
                }
            })
    }

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


    suspend fun getDirections(coordinates: String, accessToken: String): DirectionsResponse {
        return mapboxDirectionsApi.getDirections(coordinates, accessToken)
    }

    // Function to get detail location by ID
    fun getDetailLocation(locationId: String?): LiveData<ApiResult> {
        val resultLiveData = MutableLiveData<ApiResult>()

        viewModelScope.launch(Dispatchers.IO) {
            if (locationId != null) {
                resultLiveData.postValue(ApiResult.Loading)
                RetrofitInstance.retrofitService.getDetailLocation(locationId)
                    .enqueue(object : Callback<LocationItem> {
                        override fun onResponse(call: Call<LocationItem>, response: Response<LocationItem>) {
                            if (response.isSuccessful) {
                                val locationItem = response.body()
                                resultLiveData.postValue(ApiResult.Success(listOfNotNull(locationItem)))
                            } else {
                                resultLiveData.postValue(ApiResult.Empty)
                            }
                        }

                        override fun onFailure(call: Call<LocationItem>, t: Throwable) {
                            resultLiveData.postValue(ApiResult.Failure(t))
                        }
                    })
            } else {
                resultLiveData.postValue(ApiResult.Empty)
            }
        }

        return resultLiveData
    }

    fun addLocation(locationRequest: LocationItem) = viewModelScope.launch(Dispatchers.IO) {
        _jobResponseLocationData.postValue(ApiResult.Loading)
        RetrofitInstance.retrofitService.addLocation(locationRequest)
            .enqueue(object : Callback<LocationItem> {
                override fun onResponse(
                    call: Call<LocationItem>,
                    response: Response<LocationItem>
                ) {
                    if (response.isSuccessful) {
                        val locationItem = response.body()
                        _jobResponseLocationData.postValue(ApiResult.Success(listOfNotNull(locationItem)))
                    } else {
                        _jobResponseLocationData.postValue(ApiResult.Empty)
                    }
                }

                override fun onFailure(call: Call<LocationItem>, t: Throwable) {
                    _jobResponseLocationData.postValue(ApiResult.Failure(t))
                }
            })
    }

    fun getLocationReviews(locationId: String): LiveData<ApiResult> {
        viewModelScope.launch(Dispatchers.IO) {
            _jobResponseLocationData.postValue(ApiResult.Loading)
            RetrofitInstance.retrofitService.getLocationReviews(locationId)
                .enqueue(object : Callback<List<ReviewItem>> {
                    override fun onResponse(
                        call: Call<List<ReviewItem>>,
                        response: Response<List<ReviewItem>>
                    ) {
                        if (response.isSuccessful) {
                            val reviewList = response.body() // This should be a List<ReviewItem>
                            _jobResponseLocationData.postValue(reviewList?.let {
                                ApiResult.Success(
                                    it
                                )
                            })
                        } else {
                            Log.e("LocationViewModel", "Error: ${response.errorBody()?.string()}")
                            _jobResponseLocationData.postValue(ApiResult.Failure(Throwable(response.message())))
                        }
                    }

                    override fun onFailure(call: Call<List<ReviewItem>>, t: Throwable) {
                        _jobResponseLocationData.postValue(ApiResult.Failure(t))
                    }
                })
        }
        return _jobResponseLocationData
    }

}
