package com.minigrocery.app.ui.product

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.minigrocery.app.data.model.Product
import com.minigrocery.app.data.repository.CartRepository
import com.minigrocery.app.data.repository.ProductRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * ViewModel for Product Details Activity
 */
class ProductDetailsViewModel(
    private val productRepository: ProductRepository,
    private val cartRepository: CartRepository
) : ViewModel() {
    
    private val _productDetails = MutableStateFlow<Product?>(null)
    val productDetails: StateFlow<Product?> = _productDetails.asStateFlow()
    
    private val _isInCart = MutableStateFlow(false)
    val isInCart: StateFlow<Boolean> = _isInCart.asStateFlow()
    
    private val _currentQuantity = MutableStateFlow(1)
    val currentQuantity: StateFlow<Int> = _currentQuantity.asStateFlow()
    
    /**
     * Load product details
     */
    fun loadProductDetails(productId: String) {
        viewModelScope.launch {
            try {
                val product = productRepository.getProductById(productId)
                _productDetails.value = product
                
                // Check if product is in cart
                product?.let {
                    _isInCart.value = cartRepository.isProductInCart(productId)
                }
                
            } catch (e: Exception) {
                // Handle error
            }
        }
    }
    
    /**
     * Add product to cart
     */
    suspend fun addToCart(productId: String, quantity: Int) {
        try {
            val product = _productDetails.value
            product?.let {
                cartRepository.addToCart(it, quantity)
                _isInCart.value = true
            }
        } catch (e: Exception) {
            // Handle error
        }
    }
    
    /**
     * Toggle favorite status
     */
    suspend fun toggleFavorite(productId: String) {
        try {
            val product = _productDetails.value
            product?.let {
                productRepository.updateFavoriteStatus(productId, !it.isFavorite)
                // Update local product state
                _productDetails.value = it.copy(isFavorite = !it.isFavorite)
            }
        } catch (e: Exception) {
            // Handle error
        }
    }
    
    /**
     * Update quantity
     */
    fun updateQuantity(quantity: Int) {
        _currentQuantity.value = quantity
    }
}
