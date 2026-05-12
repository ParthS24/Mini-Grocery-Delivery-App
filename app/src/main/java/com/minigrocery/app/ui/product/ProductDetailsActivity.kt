package com.minigrocery.app.ui.product

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.minigrocery.app.MiniGroceryApplication
import com.minigrocery.app.databinding.ActivityProductDetailsBinding
import com.minigrocery.app.utils.Constants
import com.minigrocery.app.utils.IntentConstants
import com.minigrocery.app.utils.formatCurrency
import com.minigrocery.app.utils.launchWhenStarted
import com.minigrocery.app.utils.loadImage
import com.minigrocery.app.utils.showToast
import kotlinx.coroutines.launch

/**
 * Product Details Activity
 */
class ProductDetailsActivity : AppCompatActivity() {
    
    private lateinit var binding: ActivityProductDetailsBinding
    private val viewModel: ProductDetailsViewModel by viewModels {
        ProductDetailsViewModelFactory(
            (application as MiniGroceryApplication).productRepository,
            (application as MiniGroceryApplication).cartRepository
        )
    }
    
    private var productId = ""
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        setupUI()
        setupObservers()
        
        // Get product ID from intent
        productId = intent.getStringExtra(IntentConstants.EXTRA_PRODUCT_ID) ?: ""
        loadProductDetails()
    }
    
    private fun setupUI() {
        setupToolbar()
        setupClickListeners()
    }
    
    private fun setupToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
            title = ""
        }
        binding.toolbar.setNavigationOnClickListener {
            finish()
        }
    }
    
    private fun setupClickListeners() {
        binding.btnAddToCart.setOnClickListener {
            addToCart()
        }
        
        binding.ivFavorite.setOnClickListener {
            toggleFavorite()
        }
        
        binding.btnIncrease.setOnClickListener {
            updateQuantity(viewModel.currentQuantity.value + 1)
        }
        
        binding.btnDecrease.setOnClickListener {
            if (viewModel.currentQuantity.value > 1) {
                updateQuantity(viewModel.currentQuantity.value - 1)
            }
        }
    }
    
    private fun setupObservers() {
        launchWhenStarted {
            viewModel.productDetails.collect { product ->
                product?.let {
                    updateUI(it)
                }
            }
        }
        
        launchWhenStarted {
            viewModel.isInCart.collect { isInCart ->
                updateCartButton(isInCart)
            }
        }
        
        launchWhenStarted {
            viewModel.currentQuantity.collect { quantity ->
                binding.tvQuantity.text = quantity.toString()
            }
        }
    }
    
    private fun loadProductDetails() {
        viewModel.loadProductDetails(productId)
    }
    
    private fun updateUI(product: com.minigrocery.app.data.model.Product) {
        binding.apply {
            // Product image
            ivProduct.loadImage(product.imageUrl)
            
            // Product details
            tvProductName.text = product.name
            tvProductDescription.text = product.description
            tvCategory.text = product.category
            tvUnit.text = product.unit
            
            // Price
            if (product.hasDiscount()) {
                tvPrice.text = product.getFormattedPrice()
                tvOriginalPrice.text = product.getFormattedOriginalPrice()
                tvOriginalPrice.visibility = View.VISIBLE
                tvDiscount.text = "${product.discount}% OFF"
                tvDiscount.visibility = View.VISIBLE
            } else {
                tvPrice.text = product.getFormattedPrice()
                tvOriginalPrice.visibility = View.GONE
                tvDiscount.visibility = View.GONE
            }
            
            // Rating
            tvRating.text = String.format("%.1f", product.rating)
            tvRatingCount.text = "(${(product.rating * 10).toInt()} reviews)"
            
            // Stock
            if (product.stock <= 10) {
                tvStock.text = "Only ${product.stock} left"
                tvStock.visibility = View.VISIBLE
            } else {
                tvStock.visibility = View.GONE
            }
            
            // Favorite
            if (product.isFavorite) {
                ivFavorite.setImageResource(com.minigrocery.app.R.drawable.ic_favorite)
            } else {
                ivFavorite.setImageResource(com.minigrocery.app.R.drawable.ic_favorite_border)
            }
            
            // Availability
            if (product.isAvailable) {
                tvOutOfStock.visibility = View.GONE
                btnAddToCart.isEnabled = true
            } else {
                tvOutOfStock.visibility = View.VISIBLE
                btnAddToCart.isEnabled = false
            }
        }
    }
    
    private fun updateCartButton(isInCart: Boolean) {
        if (isInCart) {
            binding.btnAddToCart.text = "Update Cart"
            binding.quantityControls.visibility = View.VISIBLE
        } else {
            binding.btnAddToCart.text = "Add to Cart"
            binding.quantityControls.visibility = View.GONE
        }
    }
    
    private fun addToCart() {
        lifecycleScope.launch {
            viewModel.addToCart(productId, viewModel.currentQuantity.value)
            showToast("Added to cart")
        }
    }
    
    private fun toggleFavorite() {
        lifecycleScope.launch {
            viewModel.toggleFavorite(productId)
            showToast("Favorite status updated")
        }
    }
    
    private fun updateQuantity(quantity: Int) {
        viewModel.updateQuantity(quantity)
    }
}
