package com.example.electrigo.activities

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Button
import android.widget.TextView
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
import javax.mail.*
import javax.mail.internet.InternetAddress
import javax.mail.internet.MimeMessage
import kotlin.concurrent.thread
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import android.app.AlertDialog


val backendUrl = "http://10.0.2.2:3000/api/payment/"

class CardActivity : BottomSheetDialogFragment() {

    private lateinit var paymentIntentClientSecret: String
    private lateinit var stripe: Stripe
    private lateinit var cardInputWidget: CardInputWidget
    private lateinit var tiEmail: TextInputEditText
    private lateinit var tiEmailLayout: TextInputLayout
    private lateinit var emailErrorTextView: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Utilisez le fichier XML avec ScrollView pour augmenter la hauteur
        val view = inflater.inflate(R.layout.activity_card, container, false)
        dialog?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN)

        // Récupérer la référence au champ d'adresse e-mail
        tiEmail = view.findViewById(R.id.tiEmail)
        tiEmailLayout = view.findViewById(R.id.tiEmailLayout)
        emailErrorTextView = view.findViewById(R.id.emailErrorTextView)

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

        // Pour afficher le montant sur le bouton
        val payButton = view.findViewById<Button>(R.id.payButton)
        payButton.text = "Payer $$amount"

        startCheckout(amount)
    }

    private fun startCheckout(amountPay: Int) {
        val amountPayInt = amountPay * 100
        RetrofitInstance.ApiClient().createPaymentIntent(
            amountPayInt.toDouble(),
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
                val recipientEmail = tiEmail.text.toString().trim()

                if (recipientEmail.isEmpty()) {
                    emailErrorTextView.text = "Le champ email ne doit pas être vide"
                    emailErrorTextView.visibility = View.VISIBLE
                } else if (!isValidEmail(recipientEmail)) {
                    emailErrorTextView.text = "Veuillez entrer une adresse e-mail valide"
                    emailErrorTextView.visibility = View.VISIBLE
                } else {
                    // Si l'adresse e-mail est valide et non vide, masquez le message d'erreur
                    emailErrorTextView.visibility = View.GONE
                    val confirmParams = ConfirmPaymentIntentParams.createWithPaymentMethodCreateParams(
                        params,
                        paymentIntentClientSecret
                    )
                    stripe.confirmPayment(requireActivity(), confirmParams)
                    showPaymentSuccessDialog()

                    // Envoyer un e-mail
                    sendPaymentConfirmationEmail()
                }
            }
        }

    }

    private fun showPaymentSuccessDialog() {
        val alertDialog = AlertDialog.Builder(requireContext()).create()
        alertDialog.setTitle("Paiement réussi")
        alertDialog.setMessage("Le paiement a été effectué avec succès.")
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK") { _, _ ->
            // Fermer la boîte de dialogue de succès
            alertDialog.dismiss()

            // Fermer également le BottomSheetDialogFragment et simuler le retour en arrière
            requireActivity().onBackPressed()
        }
        alertDialog.setCancelable(false)
        alertDialog.show()
    }

    // Email
    private fun sendPaymentConfirmationEmail() {
        thread {
            try {
                // Récupérer l'adresse e-mail entrée par l'utilisateur
                val recipientEmail = tiEmail.text.toString().trim()

                // Valider que l'adresse e-mail est valide avant de l'utiliser
                if (isValidEmail(recipientEmail)) {
                    // Paramètres du serveur de messagerie
                    val props = System.getProperties()
                    props["mail.smtp.host"] = "smtp.gmail.com"
                    props["mail.smtp.socketFactory.port"] = "465"
                    props["mail.smtp.socketFactory.class"] = "javax.net.ssl.SSLSocketFactory"
                    props["mail.smtp.auth"] = "true"
                    props["mail.smtp.port"] = "465"

                    // Authentification
                    val session = Session.getInstance(props, object : Authenticator() {
                        override fun getPasswordAuthentication(): PasswordAuthentication {
                            return PasswordAuthentication("stepstyle15@gmail.com", "bfxmzezqqknqmete")
                        }
                    })

                    val message = MimeMessage(session)
                    message.setFrom(InternetAddress("stepstyle15@gmail.com"))
                    message.setRecipients(
                        Message.RecipientType.TO,
                        InternetAddress.parse(recipientEmail)
                    )
                    message.subject = "Confirmation de paiement"
                    message.setText("Votre paiement a été effectué avec succès. Merci!")

                    // Envoi du message
                    Transport.send(message)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun isValidEmail(email: String): Boolean {
        val emailRegex = "^[A-Za-z](.*)([@]{1})(.{1,})(\\.)(.{1,})"
        return email.matches(emailRegex.toRegex())
    }

}
