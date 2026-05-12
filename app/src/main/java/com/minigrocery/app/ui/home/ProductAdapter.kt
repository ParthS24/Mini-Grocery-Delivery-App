package com.minigrocery.app.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.minigrocery.app.data.model.Product
import com.minigrocery.app.databinding.ItemProductBinding
import com.minigrocery.app.utils.loadImage

/**
 * RecyclerView adapter for products
 */
class ProductAdapter(
    private val onProductClick: (Product) -> Unit,
    private val onAddToCart: (Product) -> Unit,
    private val onFavoriteClick: (Product) -> Unit
) : ListAdapter<Product, ProductAdapter.ProductViewHolder>(ProductDiffCallback()) {
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val binding = ItemProductBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ProductViewHolder(binding)
    }
    
    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
    
    inner class ProductViewHolder(
        private val binding: ItemProductBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        
        fun bind(product: Product) {
            binding.apply {
                ivProduct.loadImage(product.imageUrl)
                tvProductName.text = product.name
                tvProductPrice.text = product.getFormattedPrice()
                
                // Handle discount
                if (product.hasDiscount()) {
                    tvOriginalPrice.text = product.getFormattedOriginalPrice()
                    tvOriginalPrice.visibility = android.view.View.VISIBLE
                    tvDiscount.text = "${product.discount}% OFF"
                    tvDiscount.visibility = android.view.View.VISIBLE
                } else {
                    tvOriginalPrice.visibility = android.view.View.GONE
                    tvDiscount.visibility = android.view.View.GONE
                }
                
                // Handle rating
                tvRating.text = String.format("%.1f", product.rating)
                tvRatingCount.text = "(${(product.rating * 10).toInt()})"
                
                // Handle favorite
                if (product.isFavorite) {
                    ivFavorite.setImageResource(com.minigrocery.app.R.drawable.ic_favorite)
                } else {
                    ivFavorite.setImageResource(com.minigrocery.app.R.drawable.ic_favorite_border)
                }
                
                // Handle stock
                if (product.stock <= 10) {
                    tvStock.visibility = android.view.View.VISIBLE
                    tvStock.text = "Only ${product.stock} left"
                } else {
                    tvStock.visibility = android.view.View.GONE
                }
                
                // Set click listeners
                root.setOnClickListener {
                    onProductClick(product)
                }
                
                btnAddToCart.setOnClickListener {
                    onAddToCart(product)
                }
                
                ivFavorite.setOnClickListener {
                    onFavoriteClick(product)
                }
            }
        }
    }
    
    private class ProductDiffCallback : DiffUtil.ItemCallback<Product>() {
        override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem.id == newItem.id
        }
        
        override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem == newItem
        }
    }
}
