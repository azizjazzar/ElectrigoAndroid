package com.example.electrigo.fragments

import android.annotation.SuppressLint
import android.content.Intent
import com.example.electrigo.ViewModel.ProductViewModel
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.electrigo.Adapter.ProductsAdapter
import com.example.electrigo.Model.Product
import com.example.electrigo.R
import com.example.electrigo.activities.FormulaireProduitActivity
//import com.example.electrigo.activities.formulaireProductActivity
import com.example.electrigo.databinding.ProduitHomeFragmentBinding
import com.example.electrigo.utils.ApiResult

class ProductHomeFragment : Fragment(R.layout.produit_home_fragment) {

    private lateinit var productViewModel: ProductViewModel
    private lateinit var productAdapter: ProductsAdapter
    private var toast: Toast? = null
    private val handler = Handler(Looper.getMainLooper())

    @SuppressLint("SuspiciousIndentation")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        productViewModel = ViewModelProvider(requireActivity())[ProductViewModel::class.java]
        productViewModel.getProducts()
        observeAllProductResponse()
// varibles
        val binding = ProduitHomeFragmentBinding.bind(view)
        val recyclerView = binding.recyclerView
        val searchBarProduct = binding.searchBarProduct // Ajout de la référence
// bar de recherche


        // Ajout du OnQueryTextListener
        searchBarProduct.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                // Appelé lorsque l'utilisateur soumet la requête (appuie sur Entrée)
                search(query.orEmpty())
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                // Appelé à chaque changement de texte dans la barre de recherche
                search(newText.orEmpty())
                return true
            }
        })
        // fonction recherche


        productAdapter = ProductsAdapter(ArrayList()) { clickedProduct ->
            // Handle item click if needed
        }

        recyclerView.adapter = productAdapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        // Your filter image views and click listeners here...

/*        observeFilteredProductData()

        val resetFilterButton = view.findViewById<Button>(R.id.resetFilterButton)

        resetFilterButton.setOnClickListener {
            productViewModel.resetFilter()
        }*/

        observeFilteredProductData()

           val buttonAddProduct: Button = view.findViewById(R.id.buttonaddProduct)

              buttonAddProduct.setOnClickListener {
                  val intent = Intent(requireContext(), FormulaireProduitActivity::class.java)
                  startActivity(intent)
              }
    }
    private fun search(query: String) {
        // Mettez à jour la logique de recherche ici
        // Vous pouvez utiliser le texte de la requête pour filtrer vos produits
        productViewModel.filterProductsByName(query)
    }
    private fun observeAllProductResponse() {
        productViewModel.productListData.observe(viewLifecycleOwner) { apiResult ->
            when (apiResult) {
                is ApiResult.Success -> {
                    val productList = apiResult.data as List<Product>
                    productAdapter.updateList(productList)
                }
                is ApiResult.Empty -> {
                    Log.d("Empty Response", "Empty response! Currently, there are no active API requests.")
                }
                is ApiResult.Failure -> {
                    Log.e("Error", "Exception: ${apiResult.t}")
                }
                is ApiResult.Loading -> {
                    // Handle loading state if needed
                }
            }
        }
    }

    private fun observeFilteredProductData() {
        productViewModel.filteredProducts.observe(viewLifecycleOwner) { filteredList ->
            if (filteredList.isEmpty()) {
                showToast("Cette marque est indisponible")
            } else {
                toast?.cancel()
                productAdapter.updateList(filteredList)
            }
        }
    }

    private fun showToast(message: String) {
        toast?.cancel()
        toast = Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT)
        toast?.show()
        handler.postDelayed({ toast?.cancel() }, 2000)
    }
}
