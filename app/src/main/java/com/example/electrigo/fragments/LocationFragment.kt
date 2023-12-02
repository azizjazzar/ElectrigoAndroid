// LocationFragment.kt

package com.example.electrigo.fragments

import LocationAdapter
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewbinding.ViewBindings
import com.example.electrigo.Model.LocationItem
import com.example.electrigo.R
import com.example.electrigo.ViewModel.LocationViewModel
import com.example.electrigo.activities.AddLocation
import com.example.electrigo.activities.DetailLocation
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

        // Initialize the LocationAdapter with a click listener
        locationAdapter = LocationAdapter(mutableListOf()) { clickedLocation ->
            navigateToDetailActivity(clickedLocation)
        }
        recyclerView.adapter = locationAdapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        val restaurantFilter = view.findViewById<ImageView>(R.id.restaurantFilter)
        val stationFilter = view.findViewById<ImageView>(R.id.stationFilter)
        val hotelFilter = view.findViewById<ImageView>(R.id.hotelFilter)

        val searchAutoCompleteTextView = view.findViewById<AutoCompleteTextView>(R.id.searchBar)
        val suggestions = listOf("Tunis", "Marsa", "Sfax",)

        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, suggestions)
        searchAutoCompleteTextView.setAdapter(adapter)

        searchAutoCompleteTextView.setOnItemClickListener { _, _, _, _ ->
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

        val btnAddLocation: ImageView = view.findViewById(R.id.addlocation)

        btnAddLocation.setOnClickListener {
            val intent = Intent(requireContext(), AddLocation::class.java)
            startActivity(intent)
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

    private fun navigateToDetailActivity(locationItem: LocationItem) {
        val intent = Intent(requireContext(), DetailLocation::class.java)
        intent.putExtra("locationId", locationItem.Id)
        intent.putExtra("locationName", locationItem.name)
        startActivity(intent)
    }
}
