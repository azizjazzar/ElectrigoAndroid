package com.example.electrigo.activities

import android.content.Context
import android.os.Environment
import com.itextpdf.text.*
import com.itextpdf.text.pdf.PdfWriter
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class PdfGenerator(private val context: Context) {

    fun generateInvoicePDF(paymentAmount: Double, recipientName: String): String {
        val fileName = "ElectriGo.pdf"
        val dir = context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS)
        val pdfFilePath = File(dir, fileName).absolutePath
        val document = Document()
        val paymentAmountMultiplied = paymentAmount * 10
        try {
            val outputStream = FileOutputStream(pdfFilePath)
            PdfWriter.getInstance(document, outputStream)
            document.open()

            // Créer un objet Font pour le texte en gras
            val titleFont = Font(Font.FontFamily.HELVETICA, 36f, Font.BOLD, BaseColor.BLACK)
            val blueFont = Font(Font.FontFamily.HELVETICA, 36f, Font.BOLD, BaseColor.BLUE)
            val normalFont = Font(Font.FontFamily.HELVETICA, 12f, Font.NORMAL, BaseColor.BLACK)

            // Créer un paragraphe pour le titre
            val titleParagraph = Paragraph()
            titleParagraph.alignment = Element.ALIGN_CENTER
            titleParagraph.add(Chunk("ELECTI", titleFont))
            titleParagraph.add(Chunk("GO", blueFont))
            document.add(titleParagraph)

            val emptyParagraph = Paragraph()
            emptyParagraph.spacingAfter = 40f // Ajustez la valeur pour définir l'espace souhaité
            document.add(emptyParagraph)

            // Ajouter Statut, Date pièce, Je soussigné(e), Trésoriére(e) de, et Certifie avoir recu de
            val currentDate = SimpleDateFormat("dd/MM/YYYY", Locale.getDefault()).format(Date())
            val statusParagraph = Paragraph("Statut: Comptabilisation versement en banque", normalFont)
            val dateParagraph = Paragraph("Date pièce: $currentDate", normalFont)
            val treasurerParagraph = Paragraph("Je soussigné(e): USERSYSTEM\nTrésoriére(e) de: ELECTRIGO", normalFont)
            val receivedFromParagraph = Paragraph("Certifie avoir reçu de (Mr/Mme): $recipientName", normalFont)
            val amountParagraph = Paragraph("La somme de: $$paymentAmountMultiplied", normalFont)

            document.add(statusParagraph)
            document.add(dateParagraph)
            document.add(treasurerParagraph)
            document.add(receivedFromParagraph)
            document.add(amountParagraph)

            // Ajouter plus de détails de paiement au besoin

            document.close()
            outputStream.close()
        } catch (e: DocumentException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }

        return pdfFilePath
    }
}
