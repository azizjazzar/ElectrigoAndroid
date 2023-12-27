package com.example.electrigo.activities
import com.example.electrigo.utils.downloadFromUrl
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.ComponentActivity
import com.example.electrigo.R
import com.example.electrigo.databinding.ProductDetailsBinding
import com.example.electrigo.Model.Product
import com.example.electrigo.utils.placeholderProgressBar

class ProductDetailsActivity : ComponentActivity() {
    private lateinit var binding: ProductDetailsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ProductDetailsBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.backButton.setOnClickListener {
            super.onBackPressedDispatcher.onBackPressed()
        }
        val productName=intent.getStringExtra("productName")
        val productDescription=intent.getStringExtra("productDescription")
        val productImageUrl=intent.getStringExtra("productImageUrl")
        val productPrice = intent.getFloatExtra("productPrice", 0.0f)





        if(productName != null){
            val productDescriptionR :TextView =findViewById(R.id.textViewProductDescription)
            val productPriceR :TextView =findViewById(R.id.textViewProductPrice)
            val productImageR :ImageView  =findViewById(R.id.recyclerViewAllImages)
            val productNameR : TextView= findViewById(R.id.textViewProductName)

            productNameR.text= productName
            productDescriptionR.text= productDescription
            productPriceR.text= productPrice.toString()
            productImageR.downloadFromUrl(
                productImageUrl,
                placeholderProgressBar(this)
            )



        }

    }
}

