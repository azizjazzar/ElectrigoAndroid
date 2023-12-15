package com.example.electrigo.activities

import LocationViewModel
import ReviewAdapter
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.electrigo.R
import com.example.electrigo.utils.ApiResult
import com.example.electrigo.Model.ReviewItem

class Review : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ReviewAdapter
    private lateinit var locationViewModel: LocationViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_review)

        recyclerView = findViewById(R.id.existingReviewsRecyclerView)
        adapter = ReviewAdapter(emptyList()) // Initialize with an empty list
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Initialize ViewModel
        locationViewModel = ViewModelProvider(this).get(LocationViewModel::class.java)

        // Call a function to fetch reviews and update the adapter
        fetchReviews()
    }

    private fun fetchReviews() {
        val locationId = "65722fced96ef2ca5f2b5c35" // Replace with the actual location ID
        // Call your API to fetch reviews by locationId
        locationViewModel.getLocationReviews(locationId).observe(this, Observer { apiResult ->
            when (apiResult) {
                is ApiResult.Success -> {
                    val reviews = apiResult.data as? List<ReviewItem> ?: listOf()
                    adapter.updateReviews(reviews)
                }
                is ApiResult.Failure -> {
                    // Handle API failure
                    Toast.makeText(this, "Failed to fetch reviews", Toast.LENGTH_SHORT).show()
                }
                // Handle other cases if needed (e.g., loading state)
                else -> {

                }
            }
        })
    }
}
