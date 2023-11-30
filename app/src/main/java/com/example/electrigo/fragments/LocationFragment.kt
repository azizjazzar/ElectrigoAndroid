// LocationFragment.kt

package com.example.electrigo.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.electrigo.Model.LocationItem
import com.example.electrigo.R
import com.example.electrigo.ViewModel.LocationViewModel
import com.example.electrigo.databinding.FragmentLocationBinding
import com.example.electrigo.utils.ApiResult

class LocationFragment : Fragment(R.layout.fragment_location) {

    private lateinit var locationViewModel: LocationViewModel
    private lateinit var locationAdapter: LocationAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        locationViewModel = ViewModelProvider(requireActivity())[LocationViewModel::class.java]
        locationViewModel.getLocations()
        observeAllLocationResponse()

        val binding = FragmentLocationBinding.bind(view)
        val recyclerView = binding.recycleLocation

        locationAdapter = LocationAdapter(mutableListOf())
        recyclerView.adapter = locationAdapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())


        val restaurantFilter = view.findViewById<ImageView>(R.id.restaurantFilter)
        val stationFilter = view.findViewById<ImageView>(R.id.stationFilter)
        val hotelFilter = view.findViewById<ImageView>(R.id.hotelFilter)

        val searchAutoCompleteTextView = view.findViewById<AutoCompleteTextView>(R.id.searchBar)
        val suggestions = listOf("Tunis", "Marsa", "Sfax", /* Add more suggestions */)

        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, suggestions)
        searchAutoCompleteTextView.setAdapter(adapter)

        searchAutoCompleteTextView.setOnItemClickListener { _, _, _, _ ->
            // Handle item click, for example, initiate the search with the selected suggestion
            val selectedSuggestion = searchAutoCompleteTextView.text.toString()
            locationViewModel.searchLocations(selectedSuggestion)
        }

        restaurantFilter.setOnClickListener {
            locationViewModel.filterLocationsByType("Restaurant")
        }

        stationFilter.setOnClickListener {
            locationViewModel.filterLocationsByType("Station")
        }

        hotelFilter.setOnClickListener {
            locationViewModel.filterLocationsByType("Hotel")
        }


        observeFilteredLocationData()

        val resetFilterButton = view.findViewById<ImageView>(R.id.resetFilterButton)

        resetFilterButton.setOnClickListener {
            locationViewModel.resetFilter()
        }
    }

    private fun observeAllLocationResponse() {
        locationViewModel.jobResponseLocationData.observe(viewLifecycleOwner) { apiResult ->
            when (apiResult) {
                is ApiResult.Success -> {
                    val locationList = apiResult.data as List<LocationItem>
                    locationAdapter.updateList(locationList)
                }
                is ApiResult.Empty -> {
                    Log.d("Empty Response", "Empty response! Currently, there are no active API requests.")
                }
                is ApiResult.Failure -> {
                    Log.e("Error", "Exception: ${apiResult.t}")
                }
                is ApiResult.Loading -> {
                    // Handle loading state if needed
                }
            }
        }
    }

    private fun observeFilteredLocationData() {
        locationViewModel.filteredLocations.observe(viewLifecycleOwner) { filteredList ->
            locationAdapter.updateList(filteredList)
        }
    }
}
