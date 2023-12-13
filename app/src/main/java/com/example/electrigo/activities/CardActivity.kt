package com.example.electrigo.activities

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Button
import androidx.fragment.app.DialogFragment
import com.stripe.android.ApiResultCallback
import com.stripe.android.PaymentConfiguration
import com.stripe.android.PaymentIntentResult
import com.stripe.android.Stripe
import com.stripe.android.model.ConfirmPaymentIntentParams
import com.stripe.android.view.CardInputWidget
import com.example.electrigo.R
import com.example.electrigo.Service.RetrofitInstance
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

val backendUrl = "http://10.0.2.2:3000/api/reservation/"


class CardActivity : BottomSheetDialogFragment() {

    private lateinit var paymentIntentClientSecret: String
    private lateinit var stripe: Stripe
    private lateinit var cardInputWidget: CardInputWidget

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Utilisez le fichier XML avec ScrollView pour augmenter la hauteur
        val view = inflater.inflate(R.layout.activity_card, container, false)
        dialog?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        PaymentConfiguration.init(
            requireContext(),
            "pk_test_51OErmACis87pjNWpmR1mA9OY8bC9joB8m3yMTqOlDqonuPHoOla3qdFxRI4l23Rqpn4RjSQjj1H75UgBbpTr2Os800jsLoQ4TE"
        )

        stripe = Stripe(requireContext(), PaymentConfiguration.getInstance(requireContext()).publishableKey)

        cardInputWidget = view.findViewById(R.id.cardInputWidget)

        val amount = requireArguments().getInt("transactionAmount", 0)

        startCheckout(amount)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }



    private fun startCheckout(amountPay: Int) {
        val amountPayDouble = amountPay.toDouble() // Convertir l'Int en Double
        RetrofitInstance.ApiClient().createPaymentIntent(
            amountPayDouble,
            "card",
            "usd",
            completion = { paymentIntentClientSecret, error ->
                run {
                    paymentIntentClientSecret?.let {
                        this.paymentIntentClientSecret = it
                    }
                    error?.let {
                        // Gérer l'erreur de chargement du PaymentIntent
                    }
                }
            })

        // Confirmez le PaymentIntent avec le widget de carte
        view?.findViewById<Button>(R.id.payButton)?.setOnClickListener {
            cardInputWidget.paymentMethodCreateParams?.let { params ->
                val confirmParams = ConfirmPaymentIntentParams.createWithPaymentMethodCreateParams(
                    params,
                    paymentIntentClientSecret
                )
                stripe.confirmPayment(requireActivity(), confirmParams)
            }
        }
    }

    private fun displayAlert(title: String, message: String) {
        // Afficher l'alerte à l'intérieur du fragment (vous pouvez personnaliser cela selon vos besoins)
        val builder = AlertDialog.Builder(requireContext())
            .setTitle(title)
            .setMessage(message)

        builder.create().show()
    }
}
