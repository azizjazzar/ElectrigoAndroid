// VehiculeAdapter.kt
package com.example.electrigo.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.electrigo.Model.Vehicule
import com.example.electrigo.R

class VehiculeAdapter(private val vehicules: List<Vehicule>) :
    RecyclerView.Adapter<VehiculeAdapter.VehiculeViewHolder>() {

    class VehiculeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.imageViewVehicule)
        val textViewDescription: TextView = itemView.findViewById(R.id.textViewDescription)
        val textViewPrix: TextView = itemView.findViewById(R.id.textViewPrix)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VehiculeViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_vehicule, parent, false)
        return VehiculeViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: VehiculeViewHolder, position: Int) {
        val vehicule = vehicules[position]
        holder.imageView.setImageResource(vehicule.imageResId)
        holder.textViewDescription.text = vehicule.description
        holder.textViewPrix.text = "${vehicule.prix} €"
    }

    override fun getItemCount(): Int {
        return vehicules.size
    }
}
