package com.example.electrigo.activities

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Button
import android.widget.TextView
import com.stripe.android.PaymentConfiguration
import com.stripe.android.Stripe
import com.stripe.android.model.ConfirmPaymentIntentParams
import com.stripe.android.view.CardInputWidget
import com.example.electrigo.R
import com.example.electrigo.Service.RetrofitInstance
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.File
import javax.activation.DataHandler
import javax.activation.FileDataSource
import javax.mail.*
import javax.mail.internet.InternetAddress
import javax.mail.internet.MimeBodyPart
import javax.mail.internet.MimeMessage
import javax.mail.internet.MimeMultipart
import android.app.AlertDialog




val backendUrl = "https://electrigo.onrender.com/api/payment/"

class CardActivity : BottomSheetDialogFragment() {

    private lateinit var paymentIntentClientSecret: String
    private lateinit var stripe: Stripe
    private lateinit var cardInputWidget: CardInputWidget
    private lateinit var tiEmail: TextInputEditText
    private lateinit var tiEmailLayout: TextInputLayout
    private lateinit var emailErrorTextView: TextView
    private lateinit var tiNom: TextInputEditText
    private lateinit var tiPrenom: TextInputEditText

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.activity_card, container, false)
        dialog?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN)

        tiEmail = view.findViewById(R.id.tiEmail)
        tiEmailLayout = view.findViewById(R.id.tiEmailLayout)
        emailErrorTextView = view.findViewById(R.id.emailErrorTextView)
        tiNom = view.findViewById(R.id.tiNom)
        tiPrenom = view.findViewById(R.id.tiPrenom)

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
                    emailErrorTextView.visibility = View.GONE

                    val recipientName = "${tiNom.text.toString()} ${tiPrenom.text.toString()}"
                    val paymentAmount = amountPay.toDouble() / 100.0

                    GlobalScope.launch(Dispatchers.IO) {
                        //appel de pdf et email
                        val pdfGenerator = PdfGenerator(requireContext())
                        val pdfFilePath = pdfGenerator.generateInvoicePDF(paymentAmount, recipientName)
                        sendPaymentConfirmationEmail(pdfFilePath)
                    }

                    val confirmParams = ConfirmPaymentIntentParams.createWithPaymentMethodCreateParams(
                        params,
                        paymentIntentClientSecret
                    )
                    stripe.confirmPayment(requireActivity(), confirmParams)
                    showPaymentSuccessDialog()
                }
            }
        }
    }

    private fun showPaymentSuccessDialog() {
        val alertDialog = AlertDialog.Builder(requireContext()).create()
        alertDialog.setTitle("Paiement réussi")
        alertDialog.setMessage("Le paiement a été effectué avec succès.")
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK") { _, _ ->
            alertDialog.dismiss()
            requireActivity().onBackPressed()
        }
        alertDialog.setCancelable(false)
        alertDialog.show()
    }


    //EMAIL
    private fun sendPaymentConfirmationEmail(pdfFilePath: String) {
        GlobalScope.launch(Dispatchers.IO) {
            try {

                //recuperer l@ ecrire  par utilisateur
                val recipientEmail = tiEmail.text.toString().trim()

                if (isValidEmail(recipientEmail)) {
                    val props = System.getProperties()
                    //Configuration des paramètres du serveur de messagerie SMTP
                    props["mail.smtp.host"] = "smtp.gmail.com"
                    props["mail.smtp.socketFactory.port"] = "465"
                    props["mail.smtp.socketFactory.class"] = "javax.net.ssl.SSLSocketFactory"
                    props["mail.smtp.auth"] = "true"
                    props["mail.smtp.port"] = "465"

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

                    val multipart = MimeMultipart()

                    val messageContent = "Bonjour! ${tiNom.text.toString()} ${tiPrenom.text.toString()}, Votre paiement a été effectué avec succès. Merci!"
                    val textPart = MimeBodyPart()
                    textPart.setText(messageContent)
                    multipart.addBodyPart(textPart)

                    val pdfAttachment = MimeBodyPart()
                    val pdfFileDataSource = FileDataSource(pdfFilePath)
                    pdfAttachment.dataHandler = DataHandler(pdfFileDataSource)
                    pdfAttachment.fileName = "ElectriGo.pdf"
                    multipart.addBodyPart(pdfAttachment)

                    message.setContent(multipart)

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