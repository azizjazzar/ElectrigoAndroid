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
class VehiculeAdapter(
    private val  vehiculeList :MutableList<Vehicule>,
    private val onItemClick : (Vehicule) -> Unit


) : RecyclerView.Adapter<VehiculeAdapter.VehiculeViewHolder>() {


    fun updateList(newList: List<Vehicule>) {
        vehiculeList.clear()
        vehiculeList.addAll(newList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VehiculeViewHolder {
        val binding = ItemVehiculeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return VehiculeViewHolder(binding)
    }


    override fun onBindViewHolder(holder: VehiculeViewHolder, position: Int) {
        val vehicule = vehiculeList[position]
        holder.binding.textViewMarque.text = vehicule.marque?.capitalize()
        holder.binding.Textprice.text = vehicule.prix.toString()
        holder.binding.textNbredeplaces.text = vehicule.nombreDePlaces.toString()
        holder.binding.textvitesseMax.text = vehicule.vitesseMax.toString()
        /*
                // Load and display the image using Glide
                Glide.with(holder.itemView.context)
                    .load(vehicule.image) // Assuming 'image' is the URL of the image
                 // Error image if loading fails
                    .into(holder.binding.imageViewVehicule) // ImageView in your layout

         */
    }


    override fun getItemCount(): Int {
        return vehiculeList.size
    }

    inner class VehiculeViewHolder(val binding: ItemVehiculeBinding) : RecyclerView.ViewHolder(binding.root)
}
