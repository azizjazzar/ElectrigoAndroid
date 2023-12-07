import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.electrigo.Service.LocationApi
import com.example.electrigo.Model.PaymentIntentResponse
import com.example.electrigo.Model.PaymentConfirmationResponse
import com.example.electrigo.databinding.ActivityPaymentBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class PaymentActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPaymentBinding
    private lateinit var locationApi: LocationApi

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPaymentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialisez Retrofit
        val retrofit = Retrofit.Builder()
            .baseUrl("https://electrigo.onrender.com/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        // Créez une instance de l'interface LocationApi
        locationApi = retrofit.create(LocationApi::class.java)


        val paymentData = mapOf("amount" to 1000) // ajustez la valeur du montant selon vos besoins
        val createPaymentIntentCall = locationApi.createPaymentIntent(paymentData)
        createPaymentIntentCall.enqueue(object : Callback<PaymentIntentResponse> {
            override fun onResponse(call: Call<PaymentIntentResponse>, response: Response<PaymentIntentResponse>) {
                if (response.isSuccessful) {
                    val clientSecret = response.body()?.clientSecret
                    // Utilisez clientSecret comme nécessaire
                } else {
                    // Traitement des erreurs
                }
            }

            override fun onFailure(call: Call<PaymentIntentResponse>, t: Throwable) {

            }
        })

        // Exemple d'utilisation de confirmPayment
        val paymentConfirmationData = mapOf("paymentIntentId" to "your_payment_intent_id") // ajustez l'ID selon vos besoins
        val confirmPaymentCall = locationApi.confirmPayment(paymentConfirmationData)
        confirmPaymentCall.enqueue(object : Callback<PaymentConfirmationResponse> {
            override fun onResponse(call: Call<PaymentConfirmationResponse>, response: Response<PaymentConfirmationResponse>) {
                if (response.isSuccessful) {
                    val paymentIntentId = response.body()?.paymentId
                    // Utilisez paymentIntentId comme nécessaire
                } else {
                    // Traitement des erreurs
                }
            }

            override fun onFailure(call: Call<PaymentConfirmationResponse>, t: Throwable) {
                // Traitement des erreurs réseau
            }
        })
    }
}
