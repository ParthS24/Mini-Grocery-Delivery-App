package com.minigrocery.app.ui.cart

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.minigrocery.app.data.model.CartItem
import com.minigrocery.app.databinding.ItemCartBinding
import com.minigrocery.app.utils.loadImage

/**
 * RecyclerView adapter for cart items
 */
class CartAdapter(
    private val onQuantityChange: (CartItem, Int) -> Unit,
    private val onRemoveClick: (CartItem) -> Unit
) : ListAdapter<CartItem, CartAdapter.CartViewHolder>(CartDiffCallback()) {
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val binding = ItemCartBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return CartViewHolder(binding)
    }
    
    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
    
    inner class CartViewHolder(
        private val binding: ItemCartBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        
        fun bind(cartItem: CartItem) {
            binding.apply {
                ivProduct.loadImage(cartItem.productImage)
                tvProductName.text = cartItem.productName
                tvProductPrice.text = cartItem.getFormattedPrice()
                
                // Set quantity
                tvQuantity.text = cartItem.quantity.toString()
                
                // Update total price
                tvTotalPrice.text = cartItem.getFormattedTotalPrice()
                
                // Set click listeners
                btnIncrease.setOnClickListener {
                    onQuantityChange(cartItem, cartItem.quantity + 1)
                }
                
                btnDecrease.setOnClickListener {
                    if (cartItem.quantity > 1) {
                        onQuantityChange(cartItem, cartItem.quantity - 1)
                    }
                }
                
                btnRemove.setOnClickListener {
                    onRemoveClick(cartItem)
                }
            }
        }
    }
    
    private class CartDiffCallback : DiffUtil.ItemCallback<CartItem>() {
        override fun areItemsTheSame(oldItem: CartItem, newItem: CartItem): Boolean {
            return oldItem.id == newItem.id
        }
        
        override fun areContentsTheSame(oldItem: CartItem, newItem: CartItem): Boolean {
            return oldItem == newItem
        }
    }
}
