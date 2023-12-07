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
import com.example.electrigo.fragments.LocationFragment
import com.example.electrigo.fragments.VehiculeFragment

class VehiculeAdapter(
    private val  vehiculeList :MutableList<Vehicule>,
    private val itemClickListener: VehiculeFragment
) : RecyclerView.Adapter<VehiculeAdapter.VehiculeViewHolder>() {


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
