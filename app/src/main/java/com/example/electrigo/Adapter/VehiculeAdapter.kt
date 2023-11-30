package com.example.electrigo.Adapter



import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.electrigo.Model.Vehicule
import com.example.electrigo.R

class VehiculeAdapter(private val vehicules: ArrayList<Vehicule>) :
    RecyclerView.Adapter<VehiculeAdapter.VehiculeViewHolder>() {

    class VehiculeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.imageViewVehicule)
        val textViewMarque: TextView = itemView.findViewById(R.id.textViewMarque)
        val textViewPrix: TextView = itemView.findViewById(R.id.Textprice)
        val textViewnombreDePlaces: TextView = itemView.findViewById(R.id.textNbredeplaces)
        val textViewvitesseMax: TextView = itemView.findViewById(R.id.textvitesseMax)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VehiculeViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_vehicule, parent, false)
        return VehiculeViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: VehiculeViewHolder, position: Int) {
        val vehicule = vehicules[position]
        holder.imageView.setImageResource(vehicule.image)
        holder.textViewMarque.text = vehicule.marque
        holder.textViewPrix.text = vehicule.prix
        holder.textViewnombreDePlaces.text = vehicule.nombreDePlaces.toString()
        holder.textViewvitesseMax.text = vehicule.vitesseMax.toString()

    }


    override fun getItemCount(): Int {
        return vehicules.size
    }
}
