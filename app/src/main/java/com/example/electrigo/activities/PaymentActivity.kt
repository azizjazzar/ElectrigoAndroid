import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.electrigo.R
import com.stripe.android.paymentsheet.PaymentSheet
// Add the following lines to build.gradle to use this example's networking library:

import com.github.kittinunf.fuel.httpPost
import com.github.kittinunf.fuel.json.responseJson
import com.github.kittinunf.result.Result
import com.stripe.android.PaymentConfiguration
import com.stripe.android.paymentsheet.PaymentSheetResult

class PaymentActivity : AppCompatActivity() {
    lateinit var paymentSheet: PaymentSheet
    lateinit var customerConfig: PaymentSheet.CustomerConfiguration
    lateinit var paymentIntentClientSecret: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment)
        paymentSheet = PaymentSheet(this, ::onPaymentSheetResult)


        "https://electrigo.onrender.com/api/reservation/create-payment-intent".httpPost().responseJson { _, _, result ->
            if (result is Result.Success) {
                val responseJson = result.get().obj()
                paymentIntentClientSecret = responseJson.getString("")
                customerConfig = PaymentSheet.CustomerConfiguration(
                    responseJson.getString("customer"),
                    responseJson.getString("ephemeralKey")
                )
                val publishableKey = responseJson.getString("pk_test_51OErmACis87pjNWpmR1mA9OY8bC9joB8m3yMTqOlDqonuPHoOla3qdFxRI4l23Rqpn4RjSQjj1H75UgBbpTr2Os800jsLoQ4TE")
                PaymentConfiguration.init(this, publishableKey)
            }
        }
    }
    fun presentPaymentSheet() {
        paymentSheet.presentWithPaymentIntent(
            paymentIntentClientSecret,
            PaymentSheet.Configuration(
                merchantDisplayName = "My merchant name",
                customer = customerConfig,
                // Set `allowsDelayedPaymentMethods` to true if your business handles
                // delayed notification payment methods like US bank accounts.
                allowsDelayedPaymentMethods = true
            )
        )
    }
    fun onPaymentSheetResult(paymentSheetResult: PaymentSheetResult) {
        when(paymentSheetResult) {
            is PaymentSheetResult.Canceled -> {
                print("Canceled")
            }
            is PaymentSheetResult.Failed -> {
                print("Error: ${paymentSheetResult.error}")
            }
            is PaymentSheetResult.Completed -> {
                // Display for example, an order confirmation screen
                print("Completed")
            }
        }
    }

    fun onPayerButtonClicked(view: View) {

        presentPaymentSheet()
    }

}