package com.example.electrigo.activities

import LocationViewModel
import android.graphics.BitmapFactory
import android.os.Bundle
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.electrigo.Model.Coordinate
import com.example.electrigo.Model.LocationItem
import com.example.electrigo.R
import com.example.electrigo.databinding.ActivityAddLocationBinding
import com.mapbox.common.MapboxOptions
import com.mapbox.geojson.Point
import com.mapbox.maps.CameraOptions
import com.mapbox.maps.MapboxMap
import com.mapbox.maps.Style
import com.mapbox.maps.extension.style.layers.properties.generated.IconAnchor
import com.mapbox.maps.plugin.animation.CameraAnimationsPlugin
import com.mapbox.maps.plugin.animation.MapAnimationOptions
import com.mapbox.maps.plugin.animation.camera
import com.mapbox.maps.plugin.annotation.annotations
import com.mapbox.maps.plugin.annotation.generated.PointAnnotationOptions
import com.mapbox.maps.plugin.annotation.generated.createPointAnnotationManager
import com.mapbox.maps.plugin.gestures.addOnMapClickListener

class AddLocation : AppCompatActivity() {
    private lateinit var binding: ActivityAddLocationBinding
    private lateinit var viewModel: LocationViewModel
    private lateinit var mapboxMap: MapboxMap
    private var coordinates: ArrayList<Double> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val accessToken =
            "sk.eyJ1IjoiZGhpYWFpc3NhNzAiLCJhIjoiY2xwdHJuZDE0MGdxMTJtbzk3dWRmMHRndiJ9.KdTM-MXtfHdAPG6t5dHccQ"
        MapboxOptions.accessToken = accessToken
        binding = ActivityAddLocationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize ViewModel
        viewModel = ViewModelProvider(this).get(LocationViewModel::class.java)

        // Initialize MapView

        binding.mapViewAdd.apply {
            getMapboxMap().apply {
                loadStyleUri(Style.MAPBOX_STREETS) {
                    //style?.addImage("map_pin.png", BitmapFactory.decodeResource(resources, R.drawable.map_pin))
                    val tunisiaLocation = Point.fromLngLat(9.5375, 33.8869) // Longitude, Latitude
                    this@AddLocation.mapboxMap = this
                    mapboxMap.setCamera(CameraOptions.Builder().center(tunisiaLocation).zoom(10.0).build())

                    addOnMapClickListener { point ->
                        handleMapClick(point)
                        true
                    }
                }
            }
        }

        binding.addlocation.setOnClickListener {
            addNewLocation()
        }
    }

    private fun handleMapClick(point: Point) {
        coordinates.clear()
        coordinates.add(point.longitude())
        coordinates.add(point.latitude())


        val annotationApi = binding.mapViewAdd.annotations
        val pointAnnotationManager = annotationApi.createPointAnnotationManager()
        pointAnnotationManager.deleteAll()


        val pointAnnotationOptions = PointAnnotationOptions()
            .withPoint(point)
            .withIconImage("map_pin.png")
            .withIconSize(1.3)
            .withIconAnchor(IconAnchor.BOTTOM)

        val annotation = pointAnnotationManager.create(pointAnnotationOptions)


        val cameraAnimationsPlugin: CameraAnimationsPlugin = binding.mapViewAdd.camera
        cameraAnimationsPlugin.easeTo(
            CameraOptions.Builder().center(point).build(),
            MapAnimationOptions.mapAnimationOptions {
                duration(1000)
            }
        )
    }

    private fun addNewLocation() {
        val name = binding.tiMarque.text.toString()
        val cityName = binding.tiModele.text.toString()
        val adresse = binding.tiPrix.text.toString()
        val typeLocation = getTypeFromRadioGroup(binding.boiteRadioGroup)
        val typeCharge = getTypeFromRadioGroup(binding.nombrePlacesRadioGroup)
        val pictureUrl = "https://example.com/djerba_hotel.jpg" // Replace with your image URL

        val locationRequest = LocationItem(
            name = name,
            cityname = cityName,
            adresse = adresse,
            typelocation = typeLocation,
            typecharge = typeCharge,
            picture = pictureUrl,
            coordinate = Coordinate(type = "Point", coordinates = coordinates)
        )

        viewModel.addLocation(locationRequest)
    }

    private fun getTypeFromRadioGroup(radioGroup: RadioGroup): String {
        val selectedRadioButtonId = radioGroup.checkedRadioButtonId
        val radioButton = findViewById<RadioButton>(selectedRadioButtonId)
        return radioButton.text.toString()
    }

    private fun List<Double>.toArrayList(): ArrayList<Double> {
        return ArrayList(this)
    }

    // Override necessary lifecycle methods for MapView
    override fun onStart() {
        super.onStart()
        binding.mapViewAdd.onStart()
    }

    override fun onStop() {
        super.onStop()
        binding.mapViewAdd.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.mapViewAdd.onDestroy()
    }
}
