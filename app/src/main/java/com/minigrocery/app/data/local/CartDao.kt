package com.minigrocery.app.data.local

import androidx.room.*
import com.minigrocery.app.data.model.CartItem
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object for CartItem entity
 */
@Dao
interface CartDao {
    
    /**
     * Get all cart items
     */
    @Query("SELECT * FROM cart_items ORDER BY addedAt DESC")
    fun getAllCartItems(): Flow<List<CartItem>>
    
    /**
     * Get cart item by product ID
     */
    @Query("SELECT * FROM cart_items WHERE productId = :productId")
    suspend fun getCartItemByProductId(productId: String): CartItem?
    
    /**
     * Get cart item count
     */
    @Query("SELECT COUNT(*) FROM cart_items")
    fun getCartItemCount(): Flow<Int>
    
    /**
     * Get cart items count as integer
     */
    @Query("SELECT COUNT(*) FROM cart_items")
    suspend fun getCartItemCountInt(): Int
    
    /**
     * Get cart total amount
     */
    @Query("SELECT SUM(price * quantity) FROM cart_items")
    suspend fun getCartTotal(): Double?
    
    /**
     * Get cart total quantity
     */
    @Query("SELECT SUM(quantity) FROM cart_items")
    suspend fun getTotalQuantity(): Int?
    
    /**
     * Insert or update cart item
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdateCartItem(cartItem: CartItem)
    
    /**
     * Update cart item quantity
     */
    @Query("UPDATE cart_items SET quantity = :quantity WHERE id = :cartItemId")
    suspend fun updateCartItemQuantity(cartItemId: String, quantity: Int)
    
    /**
     * Update cart item quantity by product ID
     */
    @Query("UPDATE cart_items SET quantity = :quantity WHERE productId = :productId")
    suspend fun updateQuantityByProductId(productId: String, quantity: Int)
    
    /**
     * Delete cart item
     */
    @Delete
    suspend fun deleteCartItem(cartItem: CartItem)
    
    /**
     * Delete cart item by ID
     */
    @Query("DELETE FROM cart_items WHERE id = :cartItemId")
    suspend fun deleteCartItemById(cartItemId: String)
    
    /**
     * Delete cart item by product ID
     */
    @Query("DELETE FROM cart_items WHERE productId = :productId")
    suspend fun deleteCartItemByProductId(productId: String)
    
    /**
     * Clear all cart items
     */
    @Query("DELETE FROM cart_items")
    suspend fun clearCart()
    
    /**
     * Check if product exists in cart
     */
    @Query("SELECT EXISTS(SELECT 1 FROM cart_items WHERE productId = :productId)")
    suspend fun isProductInCart(productId: String): Boolean
}
