package com.minigrocery.app.ui.cart

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.minigrocery.app.MiniGroceryApplication
import com.minigrocery.app.databinding.FragmentCartBinding
import com.minigrocery.app.ui.checkout.CheckoutActivity
import com.minigrocery.app.utils.Constants
import com.minigrocery.app.utils.formatCurrency
import com.minigrocery.app.utils.launchWhenStarted
import com.minigrocery.app.utils.showToast
import kotlinx.coroutines.launch

/**
 * Cart Fragment for displaying and managing cart items
 */
class CartFragment : Fragment() {
    
    private var _binding: FragmentCartBinding? = null
    private val binding get() = _binding!!
    
    private val viewModel: CartViewModel by viewModels {
        CartViewModelFactory(
            (requireActivity().application as MiniGroceryApplication).cartRepository
        )
    }
    
    private lateinit var cartAdapter: CartAdapter
    
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCartBinding.inflate(inflater, container, false)
        return binding.root
    }
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        setupUI()
        setupObservers()
        loadCartData()
    }
    
    private fun setupUI() {
        setupRecyclerView()
        setupClickListeners()
    }
    
    private fun setupRecyclerView() {
        cartAdapter = CartAdapter(
            onQuantityChange = { cartItem, quantity ->
                updateCartItemQuantity(cartItem, quantity)
            },
            onRemoveClick = { cartItem ->
                removeCartItem(cartItem)
            }
        )
        binding.rvCart.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = cartAdapter
        }
    }
    
    private fun setupClickListeners() {
        binding.btnCheckout.setOnClickListener {
            navigateToCheckout()
        }
        
        binding.btnContinueShopping.setOnClickListener {
            // Navigate to home
            requireActivity().findViewById<com.google.android.material.bottomnavigation.BottomNavigationView>(
                com.minigrocery.app.R.id.bottomNavigation
            ).selectedItemId = com.minigrocery.app.R.id.navigation_home
        }
    }
    
    private fun setupObservers() {
        launchWhenStarted {
            viewModel.uiState.collect { state ->
                when (state) {
                    is CartUiState.Loading -> {
                        showLoading()
                    }
                    is CartUiState.Success -> {
                        hideLoading()
                        updateUI(state)
                    }
                    is CartUiState.Error -> {
                        hideLoading()
                        showToast(state.message)
                    }
                }
            }
        }
    }
    
    private fun loadCartData() {
        viewModel.loadCartItems()
    }
    
    private fun showLoading() {
        binding.progressBar.visibility = View.VISIBLE
        binding.contentLayout.visibility = View.GONE
    }
    
    private fun hideLoading() {
        binding.progressBar.visibility = View.GONE
        binding.contentLayout.visibility = View.VISIBLE
    }
    
    private fun updateUI(state: CartUiState.Success) {
        // Update cart items
        cartAdapter.submitList(state.cartItems)
        
        // Update totals
        binding.tvSubtotal.text = state.subtotal.formatCurrency()
        binding.tvDeliveryCharge.text = state.deliveryCharge.formatCurrency()
        binding.tvTotal.text = state.totalAmount.formatCurrency()
        
        // Update empty state
        if (state.cartItems.isEmpty()) {
            showEmptyState()
        } else {
            hideEmptyState()
        }
        
        // Update checkout button state
        binding.btnCheckout.isEnabled = state.cartItems.isNotEmpty() && 
                                   state.subtotal >= Constants.MIN_ORDER_AMOUNT
    }
    
    private fun showEmptyState() {
        binding.emptyStateLayout.visibility = View.VISIBLE
        binding.rvCart.visibility = View.GONE
        binding.summaryLayout.visibility = View.GONE
        binding.btnCheckout.visibility = View.GONE
        binding.btnContinueShopping.visibility = View.VISIBLE
    }
    
    private fun hideEmptyState() {
        binding.emptyStateLayout.visibility = View.GONE
        binding.rvCart.visibility = View.VISIBLE
        binding.summaryLayout.visibility = View.VISIBLE
        binding.btnCheckout.visibility = View.VISIBLE
        binding.btnContinueShopping.visibility = View.GONE
    }
    
    private fun updateCartItemQuantity(
        cartItem: com.minigrocery.app.data.model.CartItem,
        quantity: Int
    ) {
        lifecycleScope.launch {
            viewModel.updateCartItemQuantity(cartItem, quantity)
        }
    }
    
    private fun removeCartItem(cartItem: com.minigrocery.app.data.model.CartItem) {
        lifecycleScope.launch {
            viewModel.removeCartItem(cartItem)
            showToast("Item removed from cart")
        }
    }
    
    private fun navigateToCheckout() {
        val intent = Intent(requireContext(), CheckoutActivity::class.java)
        startActivity(intent)
    }
    
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
