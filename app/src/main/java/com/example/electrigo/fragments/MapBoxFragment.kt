
package com.example.electrigo.fragments

import LocationViewModel
import com.google.android.material.snackbar.Snackbar
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.appcompat.content.res.AppCompatResources
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.electrigo.Model.LocationItem
import com.example.electrigo.R
import com.example.electrigo.databinding.FragmentMapBoxBinding
import com.example.electrigo.utils.ApiResult
import com.mapbox.common.MapboxOptions
import com.mapbox.geojson.Point
import com.mapbox.maps.CameraOptions
import com.mapbox.maps.Style
import com.mapbox.maps.plugin.annotation.annotations
import com.mapbox.maps.plugin.annotation.generated.PointAnnotationOptions
import com.mapbox.maps.plugin.annotation.generated.createPointAnnotationManager

class MapBoxFragment : Fragment() {

    private lateinit var binding: FragmentMapBoxBinding
    private lateinit var locationViewModel: LocationViewModel

    private var locationList: List<Pair<Point, String>> = emptyList()
    private var currentLocationIndex = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val accessToken =
            "sk.eyJ1IjoiZGhpYWFpc3NhNzAiLCJhIjoiY2xwdHJuZDE0MGdxMTJtbzk3dWRmMHRndiJ9.KdTM-MXtfHdAPG6t5dHccQ"
        MapboxOptions.accessToken = accessToken
        binding = FragmentMapBoxBinding.inflate(inflater, container, false)
        locationViewModel = ViewModelProvider(requireActivity())[LocationViewModel::class.java]

        binding.mapView.mapboxMap.loadStyleUri(Style.MAPBOX_STREETS) {
            observeViewModelData()
        }
        setupNavigationButtons()

        return binding.root
    }

    private fun observeViewModelData() {
        locationViewModel.jobResponseLocationData.observe(viewLifecycleOwner) { apiResult ->
            when (apiResult) {
                is ApiResult.Success -> handleSuccess(apiResult.data)
                is ApiResult.Failure -> handleError(apiResult.message)

                else -> {}
            }
        }
    }

    private fun handleSuccess(data: Any) {
        if (data is List<*>) {
            val locationItems = data.filterIsInstance<LocationItem>()
            locationList = locationItems.mapNotNull { locationItem ->
                locationItem.coordinate?.coordinates?.let { coordinates ->
                    if (coordinates.size >= 2) {
                        val point = Point.fromLngLat(coordinates[0], coordinates[1])
                        val locationName = locationItem.name ?: "Unknown Location"
                        Pair(point, locationName)
                    } else {
                        null
                    }
                }
            }

            if (locationList.isNotEmpty()) {
                showLocationAtIndex(currentLocationIndex)
                displayLocationName(locationList[currentLocationIndex].second)
                addAnnotationsToMap(locationList)
            }
        } else {
            handleError("Data is not a list of LocationItem")
        }
    }


    private fun handleError(message: String) {
        Snackbar.make(binding.root, "Error: $message", Snackbar.LENGTH_LONG).show()
    }

    private fun setupNavigationButtons() {
        binding.btnNext.setOnClickListener {
            showNextLocation()
            displayLocationName(locationList[currentLocationIndex].second)
        }

        binding.btnPrevious.setOnClickListener {
            showPreviousLocation()
            displayLocationName(locationList[currentLocationIndex].second)
        }
    }
    private fun addAnnotationsToMap(locations: List<Pair<Point, String>>) {
        val annotationApi = binding.mapView.annotations
        val pointAnnotationManager = annotationApi.createPointAnnotationManager()

        for ((point, locationName) in locations) {
            bitmapFromDrawableRes(
                requireContext(),
                R.drawable.map_pin
            )?.let {
                val pointAnnotationOptions: PointAnnotationOptions = PointAnnotationOptions()
                    .withPoint(point)
                    .withIconImage(it)


                pointAnnotationManager.create(pointAnnotationOptions)
            }
        }
    }


    private fun bitmapFromDrawableRes(context: Context, @DrawableRes resourceId: Int): Bitmap? =
        convertDrawableToBitmap(AppCompatResources.getDrawable(context, resourceId))

    private fun convertDrawableToBitmap(sourceDrawable: Drawable?): Bitmap? {
        if (sourceDrawable == null) {
            return null
        }
        return if (sourceDrawable is BitmapDrawable) {
            sourceDrawable.bitmap
        } else {

            val constantState = sourceDrawable.constantState ?: return null
            val drawable = constantState.newDrawable().mutate()
            val bitmap: Bitmap = Bitmap.createBitmap(
                drawable.intrinsicWidth, drawable.intrinsicHeight,
                Bitmap.Config.ARGB_8888
            )
            val canvas = Canvas(bitmap)
            drawable.setBounds(0, 0, canvas.width, canvas.height)
            drawable.draw(canvas)
            bitmap
        }
    }

    private fun showLocationAtIndex(index: Int) {
        if (index in 0 until locationList.size) {
            val centerPoint = locationList[index].first // Extract Point from Pair
            binding.mapView.mapboxMap.setCamera(
                CameraOptions.Builder()
                    .center(centerPoint)
                    .pitch(5.0)
                    .zoom(15.5)
                    .bearing(5.0)
                    .build()
            )
        }
    }

    private fun showNextLocation() {
        currentLocationIndex = (currentLocationIndex + 1) % locationList.size
        showLocationAtIndex(currentLocationIndex)
    }

    private fun showPreviousLocation() {
        currentLocationIndex =
            (currentLocationIndex - 1 + locationList.size) % locationList.size
        showLocationAtIndex(currentLocationIndex)
    }

    private fun displayLocationName(locationName: String) {
        val locationNameTextView: TextView = binding.root.findViewById(R.id.annotation)
        locationNameTextView.text = locationName
    }


    override fun onStart() {
        super.onStart()
        binding.mapView.onStart()
    }


    override fun onStop() {
        super.onStop()
        binding.mapView.onStop()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.mapView.onDestroy()
    }
}

