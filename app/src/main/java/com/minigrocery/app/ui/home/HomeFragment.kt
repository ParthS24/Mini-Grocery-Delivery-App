package com.minigrocery.app.ui.home

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.minigrocery.app.MiniGroceryApplication
import com.minigrocery.app.utils.IntentConstants
import com.minigrocery.app.databinding.FragmentHomeBinding
import com.minigrocery.app.ui.product.ProductDetailsActivity
import com.minigrocery.app.utils.launchWhenStarted
import com.minigrocery.app.utils.showToast
import kotlinx.coroutines.launch

/**
 * Home Fragment with search, categories and product listing
 */
class HomeFragment : Fragment() {
    
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    
    private val viewModel: HomeViewModel by viewModels {
        HomeViewModelFactory(
            (requireActivity().application as MiniGroceryApplication).productRepository,
            (requireActivity().application as MiniGroceryApplication).cartRepository
        )
    }
    
    private lateinit var categoryAdapter: CategoryAdapter
    private lateinit var productAdapter: ProductAdapter
    private lateinit var bannerAdapter: BannerAdapter
    
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        setupUI()
        setupObservers()
        loadData()
    }
    
    private fun setupUI() {
        setupSearch()
        setupBanner()
        setupCategories()
        setupProducts()
        setupClickListeners()
    }
    
    private fun setupSearch() {
        binding.etSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.searchProducts(s.toString())
            }
            
            override fun afterTextChanged(s: Editable?) {}
        })
    }
    
    private fun setupBanner() {
        bannerAdapter = BannerAdapter()
        binding.viewPagerBanner.adapter = bannerAdapter
        binding.viewPagerBanner.offscreenPageLimit = 3
        
        // Auto scroll banner
        val handler = android.os.Handler()
        val runnable = object : Runnable {
            override fun run() {
                // Check if binding is still valid and view is attached
                if (_binding != null && isAdded) {
                    val currentItem = binding.viewPagerBanner.currentItem
                    val totalItems = bannerAdapter.itemCount
                    val nextItem = if (currentItem < totalItems - 1) currentItem + 1 else 0
                    binding.viewPagerBanner.setCurrentItem(nextItem, true)
                    handler.postDelayed(this, 3000)
                }
            }
        }
        handler.postDelayed(runnable, 3000)
    }
    
    private fun setupCategories() {
        categoryAdapter = CategoryAdapter { category ->
            viewModel.filterByCategory(category.name)
        }
        binding.rvCategories.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = categoryAdapter
        }
    }
    
    private fun setupProducts() {
        productAdapter = ProductAdapter(
            onProductClick = { product ->
                navigateToProductDetails(product.id)
            },
            onAddToCart = { product ->
                addToCart(product)
            },
            onFavoriteClick = { product ->
                toggleFavorite(product)
            }
        )
        binding.rvProducts.apply {
            layoutManager = GridLayoutManager(requireContext(), 2)
            adapter = productAdapter
        }
    }
    
    private fun setupClickListeners() {
        binding.btnCart.setOnClickListener {
            // Navigate to cart fragment
            requireActivity().findViewById<com.google.android.material.bottomnavigation.BottomNavigationView>(
                com.google.android.material.R.id.design_bottom_sheet
            ).selectedItemId = com.minigrocery.app.R.id.navigation_cart
        }
    }
    
    private fun setupObservers() {
        launchWhenStarted {
            viewModel.uiState.collect { state ->
                when (state) {
                    is HomeUiState.Loading -> {
                        showLoading()
                    }
                    is HomeUiState.Success -> {
                        hideLoading()
                        updateUI(state)
                    }
                    is HomeUiState.Error -> {
                        hideLoading()
                        showToast(state.message)
                    }
                }
            }
        }
        
        launchWhenStarted {
            viewModel.cartItemCount.collect { count ->
                updateCartBadge(count)
            }
        }
    }
    
    private fun loadData() {
        viewModel.loadInitialData()
    }
    
    private fun showLoading() {
        binding.progressBar.visibility = View.VISIBLE
        binding.shimmerViewContainer.visibility = View.VISIBLE
        binding.contentLayout.visibility = View.GONE
    }
    
    private fun hideLoading() {
        binding.progressBar.visibility = View.GONE
        binding.shimmerViewContainer.visibility = View.GONE
        binding.contentLayout.visibility = View.VISIBLE
    }
    
    private fun updateUI(state: HomeUiState.Success) {
        // Update banner
        bannerAdapter.submitList(state.banners)
        
        // Update categories
        categoryAdapter.submitList(state.categories)
        
        // Update products
        productAdapter.submitList(state.products)
        
        // Update empty state
        if (state.products.isEmpty()) {
            binding.tvEmptyState.visibility = View.VISIBLE
            binding.rvProducts.visibility = View.GONE
        } else {
            binding.tvEmptyState.visibility = View.GONE
            binding.rvProducts.visibility = View.VISIBLE
        }
    }
    
    private fun updateCartBadge(count: Int) {
        binding.tvCartBadge.text = count.toString()
        binding.tvCartBadge.visibility = if (count > 0) View.VISIBLE else View.GONE
    }
    
    private fun navigateToProductDetails(productId: String) {
        val intent = Intent(requireContext(), ProductDetailsActivity::class.java)
        intent.putExtra(IntentConstants.EXTRA_PRODUCT_ID, productId)
        startActivity(intent)
    }
    
    private fun addToCart(product: com.minigrocery.app.data.model.Product) {
        lifecycleScope.launch {
            viewModel.addToCart(product)
            showToast("Added to cart")
        }
    }
    
    private fun toggleFavorite(product: com.minigrocery.app.data.model.Product) {
        lifecycleScope.launch {
            viewModel.toggleFavorite(product.id, !product.isFavorite)
            if (product.isFavorite) {
                showToast("Removed from favorites")
            } else {
                showToast("Added to favorites")
            }
        }
    }
    
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
