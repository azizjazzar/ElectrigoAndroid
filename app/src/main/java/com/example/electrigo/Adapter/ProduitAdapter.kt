package com.example.electrigo.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.electrigo.Model.Produit  // Updated import
import com.example.electrigo.R

class ProduitAdapter(private val produits: ArrayList<Produit>) :
    RecyclerView.Adapter<ProduitAdapter.ProduitViewHolder>() {

    class ProduitViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.imageViewVehicule)
        val textViewMarque: TextView = itemView.findViewById(R.id.textViewMarque)
        val textViewPrix: TextView = itemView.findViewById(R.id.Textprice)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProduitViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.produit_item, parent, false)
        return ProduitViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ProduitViewHolder, position: Int) {
        val produit = produits[position]
        holder.imageView.setImageResource(produit.image)
        holder.textViewMarque.text = produit.nom
        holder.textViewPrix.text = produit.prix.toString()

    }

    override fun getItemCount(): Int {
        return produits.size
    }
}
