import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MapboxDirectionsApi {
    @GET("directions/v5/mapbox/driving/{coordinates}")
    suspend fun getDirections(
        @Path("coordinates") coordinates: String,
        @Query("access_token") accessToken: String
    ): DirectionsResponse
}


data class DirectionsResponse(
    val routes: List<Route>
)

data class Route(
    val duration: Double,
    val distance: Double,
    // Add other properties as needed
)
