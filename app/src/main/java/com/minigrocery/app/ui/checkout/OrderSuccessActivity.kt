package com.minigrocery.app.ui.checkout

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.minigrocery.app.databinding.ActivityOrderSuccessBinding
import com.minigrocery.app.MainActivity
import com.minigrocery.app.utils.Constants
import com.minigrocery.app.utils.formatCurrency

/**
 * Order Success Activity
 */
class OrderSuccessActivity : AppCompatActivity() {
    
    private lateinit var binding: ActivityOrderSuccessBinding
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOrderSuccessBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        setupUI()
        setupClickListeners()
    }
    
    private fun setupUI() {
        // Get order details from intent
        val orderId = intent.getStringExtra(Constants.EXTRA_ORDER_ID) ?: "ORD123456"
        val totalAmount = intent.getDoubleExtra(Constants.EXTRA_ORDER_TOTAL, 0.0)
        
        // Update UI with order details
        binding.tvOrderId.text = orderId
        binding.tvOrderTotal.text = totalAmount.formatCurrency()
        
        // Set estimated delivery time (30 minutes from now)
        val estimatedTime = System.currentTimeMillis() + (30 * 60 * 1000)
        binding.tvDeliveryTime.text = java.text.SimpleDateFormat(
            "hh:mm a",
            java.util.Locale.getDefault()
        ).format(java.util.Date(estimatedTime))
        
        // Start Lottie animation
        binding.lottieAnimation.playAnimation()
    }
    
    private fun setupClickListeners() {
        binding.btnContinueShopping.setOnClickListener {
            navigateToHome()
        }
        
        binding.btnViewOrders.setOnClickListener {
            navigateToHome() // Navigate to orders section in future
        }
    }
    
    private fun navigateToHome() {
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }
    
    override fun onDestroy() {
        super.onDestroy()
        binding.lottieAnimation.cancelAnimation()
    }
}
