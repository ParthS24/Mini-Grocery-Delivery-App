package com.minigrocery.app.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

/**
 * Product entity for Room database and API response
 */
@Entity(tableName = "products")
data class Product(
    @PrimaryKey
    @SerializedName("id")
    val id: String,
    
    @SerializedName("name")
    val name: String,
    
    @SerializedName("description")
    val description: String,
    
    @SerializedName("category")
    val category: String,
    
    @SerializedName("price")
    val price: Double,
    
    @SerializedName("discount")
    val discount: Int = 0,
    
    @SerializedName("originalPrice")
    val originalPrice: Double = price,
    
    @SerializedName("imageUrl")
    val imageUrl: String,
    
    @SerializedName("rating")
    val rating: Float = 4.5f,
    
    @SerializedName("stock")
    val stock: Int = 100,
    
    @SerializedName("unit")
    val unit: String = "pcs",
    
    @SerializedName("isFavorite")
    var isFavorite: Boolean = false,
    
    @SerializedName("isAvailable")
    val isAvailable: Boolean = true
) {
    /**
     * Calculate discounted price
     */
    fun getDiscountedPrice(): Double {
        return if (discount > 0) {
            price - (price * discount / 100)
        } else {
            price
        }
    }
    
    /**
     * Check if product has discount
     */
    fun hasDiscount(): Boolean {
        return discount > 0
    }
    
    /**
     * Get formatted price string
     */
    fun getFormattedPrice(): String {
        return "₹${String.format("%.2f", getDiscountedPrice())}"
    }
    
    /**
     * Get formatted original price string
     */
    fun getFormattedOriginalPrice(): String {
        return "₹${String.format("%.2f", originalPrice)}"
    }
}
