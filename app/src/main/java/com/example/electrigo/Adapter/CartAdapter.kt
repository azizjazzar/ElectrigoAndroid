import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.electrigo.Model.Product
import com.example.electrigo.R
import com.example.electrigo.databinding.EachProductBinding

class CartAdapter(
    private val products: ArrayList<Product>,
    private val onItemClick: (Product) -> Unit
) : RecyclerView.Adapter<CartAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: EachProductBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.each_cart, // Assurez-vous que le nom du fichier XML est correct
            parent,
            false
        )
        return ViewHolder(binding)
    }

    fun updateList(newList: List<Product>) {
        products.clear()
        products.addAll(newList)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return products.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val product = products[position]
        holder.bind(product)
    }

    inner class ViewHolder(val binding: EachProductBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(product: Product) {
            // Utilisez la classe de liaison pour définir les données sur la vue
            binding.textViewProductName.text = product.name
            binding.textViewProductPrice.text= product.price.toString()
            binding.imageOfProduct
            binding.executePendingBindings()
        }
    }
}
