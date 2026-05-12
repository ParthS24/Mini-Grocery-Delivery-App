package com.minigrocery.app.data.repository

import com.minigrocery.app.data.local.CartDao
import com.minigrocery.app.data.model.CartItem
import com.minigrocery.app.data.model.Product
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Repository for Cart data
 */
class CartRepository @Inject constructor(
    private val cartDao: CartDao
) {
    
    /**
     * Get all cart items
     */
    fun getAllCartItems(): Flow<List<CartItem>> {
        return cartDao.getAllCartItems()
    }
    
    /**
     * Get cart item count
     */
    fun getCartItemCount(): Flow<Int> {
        return cartDao.getCartItemCount()
    }
    
    /**
     * Get cart total amount
     */
    suspend fun getCartTotal(): Double {
        return cartDao.getCartTotal() ?: 0.0
    }
    
    /**
     * Get total quantity of items in cart
     */
    suspend fun getTotalQuantity(): Int {
        return cartDao.getTotalQuantity() ?: 0
    }
    
    /**
     * Add product to cart
     */
    suspend fun addToCart(product: Product, quantity: Int = 1) {
        val existingCartItem = cartDao.getCartItemByProductId(product.id)
        
        if (existingCartItem != null) {
            // Update quantity if item already exists
            val newQuantity = existingCartItem.quantity + quantity
            cartDao.updateQuantityByProductId(product.id, newQuantity)
        } else {
            // Add new item to cart
            val cartItem = CartItem(
                id = "cart_${product.id}_${System.currentTimeMillis()}",
                productId = product.id,
                productName = product.name,
                productImage = product.imageUrl,
                price = product.getDiscountedPrice(),
                quantity = quantity,
                unit = product.unit,
                category = product.category
            )
            cartDao.insertOrUpdateCartItem(cartItem)
        }
    }
    
    /**
     * Update cart item quantity
     */
    suspend fun updateCartItemQuantity(cartItemId: String, quantity: Int) {
        if (quantity > 0) {
            cartDao.updateCartItemQuantity(cartItemId, quantity)
        } else {
            cartDao.deleteCartItemById(cartItemId)
        }
    }
    
    /**
     * Update cart item quantity by product ID
     */
    suspend fun updateQuantityByProductId(productId: String, quantity: Int) {
        if (quantity > 0) {
            cartDao.updateQuantityByProductId(productId, quantity)
        } else {
            cartDao.deleteCartItemByProductId(productId)
        }
    }
    
    /**
     * Remove item from cart
     */
    suspend fun removeFromCart(cartItem: CartItem) {
        cartDao.deleteCartItem(cartItem)
    }
    
    /**
     * Remove item from cart by ID
     */
    suspend fun removeFromCartById(cartItemId: String) {
        cartDao.deleteCartItemById(cartItemId)
    }
    
    /**
     * Remove item from cart by product ID
     */
    suspend fun removeFromCartByProductId(productId: String) {
        cartDao.deleteCartItemByProductId(productId)
    }
    
    /**
     * Clear all cart items
     */
    suspend fun clearCart() {
        cartDao.clearCart()
    }
    
    /**
     * Check if product is in cart
     */
    suspend fun isProductInCart(productId: String): Boolean {
        return cartDao.isProductInCart(productId)
    }
    
    /**
     * Get cart item by product ID
     */
    suspend fun getCartItemByProductId(productId: String): CartItem? {
        return cartDao.getCartItemByProductId(productId)
    }
    
    /**
     * Get cart item count as integer
     */
    suspend fun getCartItemCountInt(): Int {
        return cartDao.getCartItemCountInt()
    }
}
