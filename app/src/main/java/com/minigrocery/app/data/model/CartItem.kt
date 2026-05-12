package com.minigrocery.app.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Cart item entity for Room database
 */
@Entity(tableName = "cart_items")
data class CartItem(
    @PrimaryKey
    val id: String,
    
    val productId: String,
    val productName: String,
    val productImage: String,
    val price: Double,
    val quantity: Int,
    val unit: String,
    val category: String,
    val addedAt: Long = System.currentTimeMillis()
) {
    /**
     * Calculate total price for this cart item
     */
    fun getTotalPrice(): Double {
        return price * quantity
    }
    
    /**
     * Get formatted total price string
     */
    fun getFormattedTotalPrice(): String {
        return "₹${String.format("%.2f", getTotalPrice())}"
    }
    
    /**
     * Get formatted price per unit string
     */
    fun getFormattedPrice(): String {
        return "₹${String.format("%.2f", price)}"
    }
}
