package com.example.electrigo.fragments

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

import com.example.electrigo.Model.LocationItem
import com.example.electrigo.databinding.ListLocationBinding

class LocationAdapter  (val location: MutableList<LocationItem>): RecyclerView.Adapter<LocationAdapter.LocationsHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocationsHolder {
        val binding = ListLocationBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return LocationsHolder(binding)
    }

    override fun onBindViewHolder(holder: LocationsHolder, position: Int) {
        val item = location[position]
        holder.binding.locationName.text = item.name?.capitalize()
        holder.binding.locationCity.text = "Ville : ${item.cityname?.capitalize()} "
        holder.binding.locationDetails.text ="Adresse : ${item.adresse?.capitalize()}"
        holder.binding.typelocation.text =item.typelocation?.capitalize()


    }

    override fun getItemCount(): Int {
        return location.size
    }

    fun updateList(newList: List<LocationItem>) {
        location.clear()
        location.addAll(newList)
        notifyDataSetChanged()
    }



    inner  class LocationsHolder(val binding: ListLocationBinding) : RecyclerView.ViewHolder(binding.root)
}
