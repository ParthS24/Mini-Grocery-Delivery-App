package com.minigrocery.app.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.minigrocery.app.data.model.Category
import com.minigrocery.app.data.model.Product
import com.minigrocery.app.data.repository.CartRepository
import com.minigrocery.app.data.repository.ProductRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch

/**
 * ViewModel for Home Fragment
 */
class HomeViewModel(
    private val productRepository: ProductRepository,
    private val cartRepository: CartRepository
) : ViewModel() {
    
    private val _uiState = MutableStateFlow<HomeUiState>(HomeUiState.Loading)
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()
    
    private val _cartItemCount = MutableStateFlow(0)
    val cartItemCount: StateFlow<Int> = _cartItemCount.asStateFlow()
    
    private var allProducts: List<Product> = emptyList()
    private var selectedCategory = "All"
    
    init {
        observeCartItemCount()
    }
    
    /**
     * Load initial data
     */
    fun loadInitialData() {
        viewModelScope.launch {
            try {
                _uiState.value = HomeUiState.Loading
                
                // Load sample products
                val sampleProducts = productRepository.getSampleProducts()
                productRepository.insertOrUpdateProducts(sampleProducts)
                
                // Get all products
                productRepository.getAllProducts().collect { products ->
                    allProducts = products
                    filterProducts()
                }
                
            } catch (e: Exception) {
                _uiState.value = HomeUiState.Error("Failed to load data: ${e.message}")
            }
        }
    }
    
    /**
     * Filter products by category
     */
    fun filterByCategory(category: String) {
        selectedCategory = category
        filterProducts()
    }
    
    /**
     * Search products
     */
    fun searchProducts(query: String) {
        viewModelScope.launch {
            try {
                val filteredProducts = if (query.isBlank()) {
                    if (selectedCategory == "All") {
                        allProducts
                    } else {
                        allProducts.filter { it.category == selectedCategory }
                    }
                } else {
                    allProducts.filter { product ->
                        product.name.contains(query, ignoreCase = true) &&
                        (selectedCategory == "All" || product.category == selectedCategory)
                    }
                }
                
                _uiState.value = HomeUiState.Success(
                    banners = getBanners(),
                    categories = Category.getDefaultCategories(),
                    products = filteredProducts
                )
                
            } catch (e: Exception) {
                _uiState.value = HomeUiState.Error("Search failed: ${e.message}")
            }
        }
    }
    
    /**
     * Add product to cart
     */
    suspend fun addToCart(product: Product) {
        cartRepository.addToCart(product, 1)
    }
    
    /**
     * Toggle favorite status
     */
    suspend fun toggleFavorite(productId: String, isFavorite: Boolean) {
        productRepository.updateFavoriteStatus(productId, isFavorite)
        // Refresh products
        filterProducts()
    }
    
    private fun filterProducts() {
        viewModelScope.launch {
            try {
                val filteredProducts = if (selectedCategory == "All") {
                    allProducts
                } else {
                    allProducts.filter { it.category == selectedCategory }
                }
                
                _uiState.value = HomeUiState.Success(
                    banners = getBanners(),
                    categories = Category.getDefaultCategories(),
                    products = filteredProducts
                )
                
            } catch (e: Exception) {
                _uiState.value = HomeUiState.Error("Filter failed: ${e.message}")
            }
        }
    }
    
    private fun observeCartItemCount() {
        viewModelScope.launch {
            cartRepository.getCartItemCount().collect { count ->
                _cartItemCount.value = count
            }
        }
    }
    
    private fun getBanners(): List<Banner> {
        return listOf(
            Banner(
                id = "1",
                title = "Fresh Vegetables",
                subtitle = "Up to 30% off",
                imageUrl = "https://images.unsplash.com/photo-1540420773420-3366772f4999?w=800&h=400&fit=crop"
            ),
            Banner(
                id = "2",
                title = "Daily Essentials",
                subtitle = "Fast delivery",
                imageUrl = "https://images.unsplash.com/photo-1542838132-92c53300491e?w=800&h=400&fit=crop"
            ),
            Banner(
                id = "3",
                title = "Organic Fruits",
                subtitle = "Healthy & Fresh",
                imageUrl = "https://images.unsplash.com/photo-1610832958506-aa56368176cf?w=800&h=400&fit=crop"
            )
        )
    }
}

/**
 * Sealed class for Home UI states
 */
sealed class HomeUiState {
    object Loading : HomeUiState()
    data class Success(
        val banners: List<Banner>,
        val categories: List<Category>,
        val products: List<Product>
    ) : HomeUiState()
    data class Error(val message: String) : HomeUiState()
}

/**
 * Banner data class
 */
data class Banner(
    val id: String,
    val title: String,
    val subtitle: String,
    val imageUrl: String
)
