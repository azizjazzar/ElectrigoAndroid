package com.example.electrigo.Adapter



import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView.OnItemClickListener
import android.widget.ImageView
import android.widget.TextView
import androidx.compose.ui.text.capitalize
import androidx.recyclerview.widget.RecyclerView
import com.example.electrigo.Model.Vehicule
import com.example.electrigo.R
import com.example.electrigo.databinding.ItemVehiculeBinding
import com.bumptech.glide.Glide
import com.example.electrigo.ViewModel.VehiculeViewModel
import com.example.electrigo.fragments.LocationFragment
import com.example.electrigo.fragments.VehiculeFragment

class VehiculeAdapter(
    private val  vehiculeList :MutableList<Vehicule>,
    private val itemClickListener: VehiculeFragment,
    private val vehiculeViewModel: VehiculeViewModel // Ajout du ViewModel
) : RecyclerView.Adapter<VehiculeAdapter.VehiculeViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VehiculeViewHolder {
        val binding = ItemVehiculeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return VehiculeViewHolder(binding)
    }
    private fun loadVehiculeImage(imageUrl: String, imageView: ImageView) {
        vehiculeViewModel.loadVehiculeImage(imageUrl, imageView)
    }
    override fun onBindViewHolder(holder: VehiculeViewHolder, position: Int) {
        val vehicule = vehiculeList[position]

        holder.binding.textViewMarque.text = vehicule.marque?.capitalize()
        holder.binding.Textprice.text = vehicule.prix.toString()
        holder.binding.textNbredeplaces.text = vehicule.nombreDePlaces.toString()
        holder.binding.textvitesseMax.text = vehicule.vitesseMax.toString()
        loadVehiculeImage(vehicule.image.toString(), holder.binding.imageViewVehicule)

        holder.itemView.setOnClickListener {
            itemClickListener.onItemClick(vehicule)
        }
    }


    override fun getItemCount(): Int {
        return vehiculeList.size
    }


    fun updateList(newList: List<Vehicule>) {
        vehiculeList.clear()
        vehiculeList.addAll(newList)
        notifyDataSetChanged()
    }








    inner class VehiculeViewHolder(val binding: ItemVehiculeBinding) : RecyclerView.ViewHolder(binding.root)

}
