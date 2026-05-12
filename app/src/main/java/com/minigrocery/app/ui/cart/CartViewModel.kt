package com.minigrocery.app.ui.cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.minigrocery.app.data.model.CartItem
import com.minigrocery.app.data.repository.CartRepository
import com.minigrocery.app.utils.Constants
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * ViewModel for Cart Fragment
 */
class CartViewModel(
    private val cartRepository: CartRepository
) : ViewModel() {
    
    private val _uiState = MutableStateFlow<CartUiState>(CartUiState.Loading)
    val uiState: StateFlow<CartUiState> = _uiState.asStateFlow()
    
    /**
     * Load cart items
     */
    fun loadCartItems() {
        viewModelScope.launch {
            try {
                _uiState.value = CartUiState.Loading
                
                cartRepository.getAllCartItems().collect { cartItems ->
                    val subtotal = cartRepository.getCartTotal()
                    val deliveryCharge = if (subtotal >= Constants.FREE_DELIVERY_THRESHOLD) {
                        0.0
                    } else {
                        Constants.DELIVERY_CHARGE
                    }
                    val totalAmount = subtotal + deliveryCharge
                    
                    _uiState.value = CartUiState.Success(
                        cartItems = cartItems,
                        subtotal = subtotal,
                        deliveryCharge = deliveryCharge,
                        totalAmount = totalAmount
                    )
                }
                
            } catch (e: Exception) {
                _uiState.value = CartUiState.Error("Failed to load cart: ${e.message}")
            }
        }
    }
    
    /**
     * Update cart item quantity
     */
    suspend fun updateCartItemQuantity(cartItem: CartItem, quantity: Int) {
        try {
            if (quantity > 0) {
                cartRepository.updateQuantityByProductId(cartItem.productId, quantity)
            } else {
                cartRepository.removeFromCartByProductId(cartItem.productId)
            }
            // Reload cart data
            loadCartItems()
        } catch (e: Exception) {
            _uiState.value = CartUiState.Error("Failed to update cart: ${e.message}")
        }
    }
    
    /**
     * Remove cart item
     */
    suspend fun removeCartItem(cartItem: CartItem) {
        try {
            cartRepository.removeFromCartByProductId(cartItem.productId)
            // Reload cart data
            loadCartItems()
        } catch (e: Exception) {
            _uiState.value = CartUiState.Error("Failed to remove item: ${e.message}")
        }
    }
    
    /**
     * Clear cart
     */
    suspend fun clearCart() {
        try {
            cartRepository.clearCart()
            // Reload cart data
            loadCartItems()
        } catch (e: Exception) {
            _uiState.value = CartUiState.Error("Failed to clear cart: ${e.message}")
        }
    }
}

/**
 * Sealed class for Cart UI states
 */
sealed class CartUiState {
    object Loading : CartUiState()
    data class Success(
        val cartItems: List<CartItem>,
        val subtotal: Double,
        val deliveryCharge: Double,
        val totalAmount: Double
    ) : CartUiState()
    data class Error(val message: String) : CartUiState()
}
