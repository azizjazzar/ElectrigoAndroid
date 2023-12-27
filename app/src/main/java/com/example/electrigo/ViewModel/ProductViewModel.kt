package com.example.electrigo.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.electrigo.Model.Product
import com.example.electrigo.Service.RetrofitInstance
import com.example.electrigo.utils.ApiResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProductViewModel : ViewModel() {
    private val _productListData: MutableLiveData<ApiResult> = MutableLiveData(ApiResult.Empty)
    val productListData: LiveData<ApiResult>
        get() = _productListData

    private val _filteredProducts: MutableLiveData<List<Product>> = MutableLiveData()
    val filteredProducts: LiveData<List<Product>>
        get() = _filteredProducts

    private val _addProductResult: MutableLiveData<ApiResult> = MutableLiveData()
    val addProductResult: LiveData<ApiResult>
        get() = _addProductResult

    fun getProducts() = viewModelScope.launch(Dispatchers.IO) {
        _productListData.postValue(ApiResult.Loading)
        RetrofitInstance.retrofitService.getProducts()
            .enqueue(object : Callback<List<Product>> {
                override fun onResponse(call: Call<List<Product>>, response: Response<List<Product>>) {
                    if (response.isSuccessful) {
                        val productList = response.body()
                        _productListData.postValue(ApiResult.Success(productList!!))
                    }
                }

                override fun onFailure(call: Call<List<Product>>, t: Throwable) {
                    _productListData.postValue(ApiResult.Failure(t))
                }
            })
    }

    fun addProduct(product: Product) = viewModelScope.launch(Dispatchers.IO) {
        _addProductResult.postValue(ApiResult.Loading)
        RetrofitInstance.retrofitService.addProduct(product)
            .enqueue(object : Callback<Void> {
                override fun onResponse(call: Call<Void>, response: Response<Void>) {
                    if (response.isSuccessful) {
                        _addProductResult.postValue(ApiResult.Success("sucess"))
                    } else {
                        _addProductResult.postValue(ApiResult.Failure(Exception("Failed to add product")))
                    }
                }

                override fun onFailure(call: Call<Void>, t: Throwable) {
                    _addProductResult.postValue(ApiResult.Failure(t))
                }
            })
    }




    fun filterProductsByName(name: String) {
        val allProducts = (_productListData.value as? ApiResult.Success)?.data

        if (allProducts is List<*>) {
            val productList = allProducts.filterIsInstance<Product>()
            val filteredList = productList.filter { product ->
                product.name?.contains(name, ignoreCase = true) ?: false
            }
            _filteredProducts.postValue(filteredList)
        }
    }

    // Add more filter methods if needed

    fun resetFilter() {
        val allProducts = (_productListData.value as? ApiResult.Success)?.data
        if (allProducts is List<*>) {
            val productList = allProducts.filterIsInstance<Product>()
            _filteredProducts.postValue(productList)
        }
    }
}
