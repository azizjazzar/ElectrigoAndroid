package com.example.electrigo.fragments

import android.content.Intent
import com.example.electrigo.ViewModel.VehiculeViewModel

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.electrigo.Adapter.VehiculeAdapter
import com.example.electrigo.Model.Vehicule
import com.example.electrigo.R
import com.example.electrigo.activities.DetailsVehiculeActivity
import com.example.electrigo.activities.formulaireVehiculeActivity
import com.example.electrigo.databinding.FragmentVehiculeBinding
import com.example.electrigo.utils.ApiResult
import com.google.android.material.floatingactionbutton.FloatingActionButton


interface OnItemClickListener {
    fun onItemClick(vehiculeItem: Vehicule)
}
class VehiculeFragment : Fragment(R.layout.fragment_vehicule), OnItemClickListener {


    private lateinit var vehiculeViewModel: VehiculeViewModel
    private lateinit var vehiculeAdapter: VehiculeAdapter
    private var toast: Toast? = null
    private val handler = Handler(Looper.getMainLooper())


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        vehiculeViewModel = ViewModelProvider(requireActivity())[VehiculeViewModel::class.java]
        vehiculeViewModel.getVehicule()
        observeAllVehiculeResponse()

        val binding = FragmentVehiculeBinding.bind(view)
        val recyclerView = binding.recyclerViewVehicules
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        vehiculeAdapter = VehiculeAdapter(mutableListOf(), this, vehiculeViewModel)

        recyclerView.adapter = vehiculeAdapter
        // Set the adapter to the RecyclerView


        val bmwFilter = view.findViewById<ImageView>(R.id.bmwFilter)
        val fiatFilter = view.findViewById<ImageView>(R.id.fiatFilter)
        val skodaFilter = view.findViewById<ImageView>(R.id.skodaFilter)
        val kiaFilter = view.findViewById<ImageView>(R.id.kiaFilter)
        val opelFilter = view.findViewById<ImageView>(R.id.opelFilter)
        val alphaFilter = view.findViewById<ImageView>(R.id.alfaromaFilter)
        val nissanFilter = view.findViewById<ImageView>(R.id.nissanFilter)
        val rangerFilter = view.findViewById<ImageView>(R.id.rangerFilter)
        val audiFilter = view.findViewById<ImageView>(R.id.audiFilter)
        val ferrariFilter = view.findViewById<ImageView>(R.id.ferrariFilter)
        val CitroenFilter = view.findViewById<ImageView>(R.id.citroenFilter)
        val hyndaiFilter = view.findViewById<ImageView>(R.id.hyndaiFilter)
        val teslaFilter = view.findViewById<ImageView>(R.id.teslaFilter)

        bmwFilter.setOnClickListener {
            vehiculeViewModel.filterVehiculesByMarque("bmw")
        }

        fiatFilter.setOnClickListener {
            vehiculeViewModel.filterVehiculesByMarque("fiat")
        }

        skodaFilter.setOnClickListener {
            vehiculeViewModel.filterVehiculesByMarque("skoda")
        }
        kiaFilter.setOnClickListener {
            vehiculeViewModel.filterVehiculesByMarque("kia")
        }
        opelFilter.setOnClickListener {
            vehiculeViewModel.filterVehiculesByMarque("opel")
        }

        alphaFilter.setOnClickListener {
            vehiculeViewModel.filterVehiculesByMarque("alfaromeo")
        }
        nissanFilter.setOnClickListener {
            vehiculeViewModel.filterVehiculesByMarque("nissan")
        }

        rangerFilter.setOnClickListener {
            vehiculeViewModel.filterVehiculesByMarque("ranger")
        }
        audiFilter.setOnClickListener {
            vehiculeViewModel.filterVehiculesByMarque("audi")
        }
        ferrariFilter.setOnClickListener {
            vehiculeViewModel.filterVehiculesByMarque("ferrari")
        }

        CitroenFilter.setOnClickListener {
            vehiculeViewModel.filterVehiculesByMarque("citroen")
        }
        hyndaiFilter.setOnClickListener {
            vehiculeViewModel.filterVehiculesByMarque("hyndai")
        }

        teslaFilter.setOnClickListener {
            vehiculeViewModel.filterVehiculesByMarque("tesla")
        }


        observeFilteredVehiculeData()

        val resetFilterButton = view.findViewById<Button>(R.id.resetFilterButton)

        resetFilterButton.setOnClickListener {
            vehiculeViewModel.resetFilter()
        }


        observeFilteredVehiculeData()

        val buttonAddVehicule: FloatingActionButton = view.findViewById(R.id.buttonaddVehicule)


// Ajouter un écouteur de clic au bouton
        buttonAddVehicule.setOnClickListener {
            val intent = Intent(requireContext(), formulaireVehiculeActivity::class.java)
            startActivity(intent)
        }

    }


    override fun onItemClick(vehiculeItem: Vehicule) {
        vehiculeItem.Id?.let { navigateToDetailActivity(it) }
    }
    private fun loadVehiculeImage(imageUrl: String, imageView: ImageView) {
        vehiculeViewModel.loadVehiculeImage(imageUrl, imageView)
    }



    private fun observeAllVehiculeResponse() {
        vehiculeViewModel.jobResponseVehiculeData.observe(viewLifecycleOwner) { apiResult ->
            when (apiResult) {
                is ApiResult.Success -> {
                    val VehiculeList = apiResult.data as List<Vehicule>
                    vehiculeAdapter.updateList(VehiculeList)
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

    private fun observeFilteredVehiculeData() {
        vehiculeViewModel.filteredLocations.observe(viewLifecycleOwner) { filteredList ->
            if (filteredList.isEmpty()) {
                showToast("Cette marque est indisponible")
            } else {
                // Fermer le Toast précédent s'il est affiché
                toast?.cancel()

                // Afficher la liste mise à jour
                vehiculeAdapter.updateList(filteredList)
            }
        }
    }

    private fun showToast(message: String) {
        // Fermer le Toast précédent s'il est affiché
        toast?.cancel()

        // Afficher le nouveau Toast
        toast = Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT)
        toast?.show()

        // Retarder la fermeture du Toast
        handler.postDelayed({ toast?.cancel() }, 2000)
    }


    private fun navigateToDetailActivity(vehiculeId: String) {
        val intent = Intent(requireContext(), DetailsVehiculeActivity::class.java)
        intent.putExtra("vehiculeId", vehiculeId)
        startActivity(intent)
    }
}