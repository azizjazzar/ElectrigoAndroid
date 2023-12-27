package com.example.electrigo.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.electrigo.Model.Product
import com.example.electrigo.databinding.EachProductBinding
import com.example.electrigo.fragments.ProductHomeFragment
import com.example.electrigo.utils.downloadFromUrl
import com.example.electrigo.utils.placeholderProgressBar

class ProductsAdapter(
    private val products: ArrayList<Product>,
    private val OnItemClickListenerProduct: ProductHomeFragment
) : RecyclerView.Adapter<ProductsAdapter.ViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = EachProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    fun updateList(newList: List<Product>) {
        products.clear()
        products.addAll(newList)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return products.size // Return the actual count of items in your list
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val product = products[position]
        // Bind your data to the view using data binding
        holder.binding.textViewProductName.text=product.name
        holder.binding.textViewProductPrice.text=product.price.toString()+"TND"
        holder.binding.imageOfProduct.downloadFromUrl(
            products[position].imageUrl,
            placeholderProgressBar(holder.itemView.context)
        )
        holder.itemView.setOnClickListener{
            OnItemClickListenerProduct.onItemClick(product)
        }
    }

    inner class ViewHolder(val binding: EachProductBinding) : RecyclerView.ViewHolder(binding.root)
}
