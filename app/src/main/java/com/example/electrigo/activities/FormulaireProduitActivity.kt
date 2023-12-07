package com.example.electrigo.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.electrigo.R
import com.example.electrigo.ViewModel.VehiculeViewModel
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.electrigo.ViewModel.ProductViewModel
import com.example.electrigo.Model.Product
import com.google.android.material.textfield.TextInputEditText


class FormulaireProduitActivity : AppCompatActivity() {

    private lateinit var tiProductName: TextInputEditText
    private lateinit var tiProductDescription: TextInputEditText
    private lateinit var tiProductImageUrl: TextInputEditText
    private lateinit var tiProductPrice: TextInputEditText
    private lateinit var btnCancel: Button
    private lateinit var btnAdd: Button
    private lateinit var productViewModel: ProductViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.formulaire_produit)
        val annuler: Button = findViewById(R.id.btnCancel)
        btnCancel = findViewById(R.id.btnCancel)
        btnAdd = findViewById(R.id.btnAdd)

        // Initialiser les propriétés lateinit
        tiProductName = findViewById(R.id.tiProductName)
        tiProductDescription = findViewById(R.id.tiProductDescription)
        tiProductImageUrl = findViewById(R.id.tiProductImageUrl)
        tiProductPrice = findViewById(R.id.tiProductPrice)
        btnCancel = findViewById(R.id.btnCancel)
        btnAdd = findViewById(R.id.btnAdd)

        productViewModel = ViewModelProvider(this)[ProductViewModel::class.java]



        // Gestion du clic sur le bouton Annuler
        btnCancel.setOnClickListener {
            // Fermer l'activité
            finish()
        }

        // Gestion du clic sur le bouton Ajouter
        btnAdd.setOnClickListener {
            // Récupération des valeurs des champs
            val productName = tiProductName.text.toString().trim()
            val productDescription = tiProductDescription.text.toString().trim()
            val productImageUrl = tiProductImageUrl.text.toString().trim()
            val productPrice = tiProductPrice.text.toString().trim()

            // Vérification si les champs sont vides
            if (productName.isEmpty() || productDescription.isEmpty() || productImageUrl.isEmpty() || productPrice.isEmpty()) {
                Toast.makeText(this, "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show()
            } else {
                // Créer un objet Product avec les données
                val newProduct = Product(
                    productName,
                    productDescription,
                    productImageUrl,
                    productPrice.toFloat()
                )

                // Ajouter le nouveau produit via le ViewModel
                productViewModel.addProduct(newProduct)

                // Exemple de message Toast pour indiquer que les données sont ajoutées
                Toast.makeText(this, "Produit ajouté avec succès", Toast.LENGTH_SHORT).show()

                // Fermer l'activité
                finish()
            }
        }



    }
}