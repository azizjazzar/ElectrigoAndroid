package com.example.electrigo.Adapter



import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.electrigo.Model.Logo
import com.example.electrigo.Model.Vehicule
import com.example.electrigo.R

class logoAdapter(private val logos: ArrayList<Logo>) :
    RecyclerView.Adapter<logoAdapter.logoViewHolder>() {

    class logoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.imagelogo)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): logoViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_logo, parent, false)
        return logoViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: logoViewHolder, position: Int) {
        val logo = logos[position]
        holder.imageView.setImageResource(logo.image)

    }


    override fun getItemCount(): Int {
        return logos.size
    }
}
