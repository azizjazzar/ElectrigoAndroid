package com.example.electrigo.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.electrigo.R
import com.mapbox.common.MapboxOptions
import com.mapbox.geojson.Point
import com.mapbox.maps.CameraOptions
import com.mapbox.maps.MapView
import com.mapbox.maps.Style

class MapBoxFragment : Fragment() {

    private lateinit var mapView: MapView


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Get the Mapbox access token
        val accessToken = "sk.eyJ1IjoiZGhpYWFpc3NhNzAiLCJhIjoiY2xwdHJuZDE0MGdxMTJtbzk3dWRmMHRndiJ9.KdTM-MXtfHdAPG6t5dHccQ"
        MapboxOptions.accessToken = accessToken
        val view = inflater.inflate(R.layout.fragment_map_box, container, false)

        // Create the MapView
        mapView = view.findViewById(R.id.mapView)

        mapView.mapboxMap.setCamera(
            CameraOptions.Builder()
                .center(Point.fromLngLat(-73.9876, 40.7661))
                .pitch(0.0)
                .zoom(2.0)
                .bearing(0.0)
                .build()
        )

        return view
    }
}





