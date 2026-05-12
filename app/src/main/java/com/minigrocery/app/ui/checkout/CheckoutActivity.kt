package com.minigrocery.app.ui.checkout

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.minigrocery.app.MiniGroceryApplication
import com.minigrocery.app.databinding.ActivityCheckoutBinding
import com.minigrocery.app.utils.Constants
import com.minigrocery.app.utils.formatCurrency
import com.minigrocery.app.utils.launchWhenStarted
import com.minigrocery.app.utils.showToast
import com.minigrocery.app.R
import kotlinx.coroutines.launch

/**
 * Checkout Activity for order placement
 */
class CheckoutActivity : AppCompatActivity() {
    
    private lateinit var binding: ActivityCheckoutBinding
    private val viewModel: CheckoutViewModel by viewModels {
        CheckoutViewModelFactory(
            (application as MiniGroceryApplication).cartRepository,
            (application as MiniGroceryApplication).userRepository
        )
    }
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCheckoutBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        setupUI()
        setupObservers()
        loadCheckoutData()
    }
    
    private fun setupUI() {
        setupToolbar()
        setupAddressInput()
        setupPaymentMethods()
        setupClickListeners()
    }
    
    private fun setupToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
            title = "Checkout"
        }
        binding.toolbar.setNavigationOnClickListener {
            finish()
        }
    }
    
    private fun setupAddressInput() {
        binding.etDeliveryAddress.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                validateForm()
            }
            
            override fun afterTextChanged(s: Editable?) {}
        })
    }
    
    private fun setupPaymentMethods() {
        binding.radioGroupPayment.setOnCheckedChangeListener { _, checkedId ->
            validateForm()
        }
    }
    
    private fun setupClickListeners() {
        binding.btnPlaceOrder.setOnClickListener {
            placeOrder()
        }
    }
    
    private fun setupObservers() {
        launchWhenStarted {
            viewModel.uiState.collect { state ->
                when (state) {
                    is CheckoutUiState.Loading -> {
                        showLoading()
                    }
                    is CheckoutUiState.Success -> {
                        hideLoading()
                        updateUI(state)
                    }
                    is CheckoutUiState.Error -> {
                        hideLoading()
                        showToast(state.message)
                    }
                    is CheckoutUiState.OrderPlaced -> {
                        hideLoading()
                        navigateToOrderSuccess(state.orderId, state.totalAmount)
                    }
                }
            }
        }
    }
    
    private fun loadCheckoutData() {
        viewModel.loadCheckoutData()
    }
    
    private fun validateForm(): Boolean {
        val address = binding.etDeliveryAddress.text.toString().trim()
        val selectedPaymentId = binding.radioGroupPayment.checkedRadioButtonId
        
        val isAddressValid = address.isNotEmpty()
        val isPaymentSelected = selectedPaymentId != -1
        
        binding.btnPlaceOrder.isEnabled = isAddressValid && isPaymentSelected
        
        // Show error messages
        if (!isAddressValid) {
            binding.tilDeliveryAddress.error = "Delivery address is required"
        } else {
            binding.tilDeliveryAddress.error = null
        }
        
        return isAddressValid && isPaymentSelected
    }
    
    private fun placeOrder() {
        if (!validateForm()) {
            showToast("Please fill all required fields")
            return
        }
        
        val address = binding.etDeliveryAddress.text.toString().trim()
        val paymentMethod = when (binding.radioGroupPayment.checkedRadioButtonId) {
            R.id.radioCod -> Constants.PAYMENT_COD
            R.id.radioOnline -> Constants.PAYMENT_ONLINE
            else -> Constants.PAYMENT_COD
        }
        
        lifecycleScope.launch {
            viewModel.placeOrder(address, paymentMethod)
        }
    }
    
    private fun showLoading() {
        binding.progressBar.visibility = android.view.View.VISIBLE
        binding.btnPlaceOrder.isEnabled = false
        binding.btnPlaceOrder.text = "Placing Order..."
    }
    
    private fun hideLoading() {
        binding.progressBar.visibility = android.view.View.GONE
        binding.btnPlaceOrder.isEnabled = true
        binding.btnPlaceOrder.text = "Place Order"
    }
    
    private fun updateUI(state: CheckoutUiState.Success) {
        // Update order summary
        binding.tvSubtotal.text = state.subtotal.formatCurrency()
        binding.tvDeliveryCharge.text = state.deliveryCharge.formatCurrency()
        binding.tvTotalAmount.text = state.totalAmount.formatCurrency()
        
        // Update cart items count
        binding.tvItemsCount.text = "${state.cartItems.size} items"
        
        // Show empty state if no items
        if (state.cartItems.isEmpty()) {
            showEmptyState()
        } else {
            hideEmptyState()
        }
    }
    
    private fun showEmptyState() {
        binding.emptyStateLayout.visibility = android.view.View.VISIBLE
        binding.contentLayout.visibility = android.view.View.GONE
    }
    
    private fun hideEmptyState() {
        binding.emptyStateLayout.visibility = android.view.View.GONE
        binding.contentLayout.visibility = android.view.View.VISIBLE
    }
    
    private fun navigateToOrderSuccess(orderId: String, totalAmount: Double) {
        val intent = Intent(this, OrderSuccessActivity::class.java)
        intent.putExtra(Constants.EXTRA_ORDER_ID, orderId)
        intent.putExtra(Constants.EXTRA_ORDER_TOTAL, totalAmount)
        startActivity(intent)
        finish()
    }
}
