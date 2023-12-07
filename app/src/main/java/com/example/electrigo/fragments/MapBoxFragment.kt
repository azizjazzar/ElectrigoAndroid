package com.example.electrigo.fragments

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.DrawableRes
import androidx.appcompat.content.res.AppCompatResources
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.electrigo.Model.LocationItem
import com.example.electrigo.R
import com.example.electrigo.ViewModel.LocationViewModel
import com.example.electrigo.databinding.FragmentMapBoxBinding
import com.example.electrigo.utils.ApiResult
import com.mapbox.common.MapboxOptions
import com.mapbox.geojson.Point
import com.mapbox.maps.CameraOptions
import com.mapbox.maps.EdgeInsets
import com.mapbox.maps.MapView
import com.mapbox.maps.Style
import com.mapbox.maps.plugin.annotation.annotations
import com.mapbox.maps.plugin.annotation.generated.PointAnnotationOptions
import com.mapbox.maps.plugin.annotation.generated.createPointAnnotationManager
import com.mapbox.maps.viewannotation.annotatedLayerFeature
import com.mapbox.maps.viewannotation.geometry
import com.mapbox.maps.viewannotation.viewAnnotationOptions


class MapBoxFragment : Fragment() {

    private lateinit var binding:FragmentMapBoxBinding

    private lateinit var locationViewModel: LocationViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val accessToken = "sk.eyJ1IjoiZGhpYWFpc3NhNzAiLCJhIjoiY2xwdHJuZDE0MGdxMTJtbzk3dWRmMHRndiJ9.KdTM-MXtfHdAPG6t5dHccQ"
        MapboxOptions.accessToken = accessToken
        binding = FragmentMapBoxBinding.inflate(inflater, container, false)
        locationViewModel = ViewModelProvider(requireActivity())[LocationViewModel::class.java]

        binding.mapView.mapboxMap.loadStyleUri(Style.MAPBOX_STREETS) {

            locationViewModel.jobResponseLocationData.observe(viewLifecycleOwner) { apiResult ->
                when (apiResult) {
                    is ApiResult.Success -> {
                        val locationList = apiResult.data as List<LocationItem>
                        val coordinates = locationList.map { it.coordinate?.coordinates }

                        coordinates.forEach { coordinate ->
                            coordinate?.let {
                                addAnnotationToMap(Point.fromLngLat(it[0], it[1]))
                            }
                        }

                        val centerPoint = coordinates.firstOrNull()?.let { coordinate ->
                            Point.fromLngLat(
                                coordinate[0],
                                coordinate[1]
                            )
                        }

                        centerPoint?.let {
                            binding.mapView.mapboxMap.setCamera(
                                CameraOptions.Builder()
                                    .center(it)
                                    .pitch(5.0)
                                    .zoom(10.0)
                                    .bearing(5.0)
                                    .build()



                            )
                        }
                    }
                    else -> {}
                }
            }


        }

        return binding.root
    }


    private fun addAnnotationToMap(point: Point) {

        bitmapFromDrawableRes(
            requireContext(),
            R.drawable.map_pin
        )?.let {
            val annotationApi = binding.mapView.annotations
            val pointAnnotationManager = annotationApi.createPointAnnotationManager()

            val pointAnnotationOptions: PointAnnotationOptions = PointAnnotationOptions()

                .withPoint(point)

                .withIconImage(it)

            pointAnnotationManager.create(pointAnnotationOptions)
        }
    }
    private fun bitmapFromDrawableRes(context: Context, @DrawableRes resourceId: Int) =
        convertDrawableToBitmap(AppCompatResources.getDrawable(context, resourceId))

    private fun convertDrawableToBitmap(sourceDrawable: Drawable?): Bitmap? {
        if (sourceDrawable == null) {
            return null
        }
        return if (sourceDrawable is BitmapDrawable) {
            sourceDrawable.bitmap
        } else {
            // copying drawable object to not manipulate on the same reference
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



}





