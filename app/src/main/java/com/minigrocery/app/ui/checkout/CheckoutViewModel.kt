package com.minigrocery.app.ui.checkout

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.minigrocery.app.data.model.CartItem
import com.minigrocery.app.data.model.Order
import com.minigrocery.app.data.repository.CartRepository
import com.minigrocery.app.data.repository.UserRepository
import com.minigrocery.app.utils.Constants
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.UUID

/**
 * ViewModel for Checkout Activity
 */
class CheckoutViewModel(
    private val cartRepository: CartRepository,
    private val userRepository: UserRepository
) : ViewModel() {
    
    private val _uiState = MutableStateFlow<CheckoutUiState>(CheckoutUiState.Loading)
    val uiState: StateFlow<CheckoutUiState> = _uiState.asStateFlow()
    
    /**
     * Load checkout data
     */
    fun loadCheckoutData() {
        viewModelScope.launch {
            try {
                _uiState.value = CheckoutUiState.Loading
                
                cartRepository.getAllCartItems().collect { cartItems ->
                    val subtotal = cartRepository.getCartTotal()
                    val deliveryCharge = if (subtotal >= Constants.FREE_DELIVERY_THRESHOLD) {
                        0.0
                    } else {
                        Constants.DELIVERY_CHARGE
                    }
                    val totalAmount = subtotal + deliveryCharge
                    
                    _uiState.value = CheckoutUiState.Success(
                        cartItems = cartItems,
                        subtotal = subtotal,
                        deliveryCharge = deliveryCharge,
                        totalAmount = totalAmount
                    )
                }
                
            } catch (e: Exception) {
                _uiState.value = CheckoutUiState.Error("Failed to load checkout data: ${e.message}")
            }
        }
    }
    
    /**
     * Place order
     */
    suspend fun placeOrder(deliveryAddress: String, paymentMethod: String) {
        try {
            _uiState.value = CheckoutUiState.Loading
            
            val currentState = _uiState.value
            if (currentState is CheckoutUiState.Success) {
                // Create order
                val orderId = "ORD${UUID.randomUUID().toString().substring(0, 8).uppercase()}"
                val order = Order(
                    id = orderId,
                    items = currentState.cartItems,
                    deliveryAddress = deliveryAddress,
                    paymentMethod = paymentMethod,
                    subtotal = currentState.subtotal,
                    deliveryCharge = currentState.deliveryCharge,
                    totalAmount = currentState.totalAmount,
                    customerPhone = userRepository.getUserPhone() ?: "",
                    customerName = userRepository.getUserName() ?: "Customer"
                )
                
                // Clear cart
                cartRepository.clearCart()
                
                _uiState.value = CheckoutUiState.OrderPlaced(
                    orderId = orderId,
                    totalAmount = currentState.totalAmount
                )
            }
            
        } catch (e: Exception) {
            _uiState.value = CheckoutUiState.Error("Failed to place order: ${e.message}")
        }
    }
}

/**
 * Sealed class for Checkout UI states
 */
sealed class CheckoutUiState {
    object Loading : CheckoutUiState()
    data class Success(
        val cartItems: List<CartItem>,
        val subtotal: Double,
        val deliveryCharge: Double,
        val totalAmount: Double
    ) : CheckoutUiState()
    data class Error(val message: String) : CheckoutUiState()
    data class OrderPlaced(
        val orderId: String,
        val totalAmount: Double
    ) : CheckoutUiState()
}
