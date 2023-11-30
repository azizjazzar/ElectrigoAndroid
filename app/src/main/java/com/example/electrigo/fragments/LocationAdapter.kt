import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.electrigo.Model.LocationItem
import com.example.electrigo.databinding.ListLocationBinding

class LocationAdapter(
    private val locationList: MutableList<LocationItem>,
    private val onItemClick: (LocationItem) -> Unit
) : RecyclerView.Adapter<LocationAdapter.LocationsHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocationsHolder {
        val binding = ListLocationBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return LocationsHolder(binding)
    }

    override fun onBindViewHolder(holder: LocationsHolder, position: Int) {
        val item = locationList[position]
        holder.binding.locationName.text = item.name?.capitalize()
        holder.binding.locationCity.text = "Ville : ${item.cityname?.capitalize()} "
        holder.binding.locationDetails.text = "Adresse : ${item.adresse?.capitalize()}"
        holder.binding.typelocation.text = item.typelocation?.capitalize()

        // Set click listener
        holder.itemView.setOnClickListener {
            onItemClick.invoke(item)
        }
    }

    override fun getItemCount(): Int {
        return locationList.size
    }

    fun updateList(newList: List<LocationItem>) {
        locationList.clear()
        locationList.addAll(newList)
        notifyDataSetChanged()
    }

    inner class LocationsHolder(val binding: ListLocationBinding) : RecyclerView.ViewHolder(binding.root)
}
