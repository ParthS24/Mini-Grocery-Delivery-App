package com.minigrocery.app.ui.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.minigrocery.app.MiniGroceryApplication
import com.minigrocery.app.databinding.ActivitySplashBinding
import com.minigrocery.app.ui.auth.LoginActivity
import com.minigrocery.app.MainActivity
import kotlinx.coroutines.launch

/**
 * Splash Screen Activity
 * Shows app logo and navigates to appropriate screen based on login status
 */
class SplashActivity : AppCompatActivity() {
    
    private lateinit var binding: ActivitySplashBinding
    private val viewModel: SplashViewModel by viewModels {
        SplashViewModelFactory((application as MiniGroceryApplication).userRepository)
    }
    
    private val SPLASH_DELAY = 2000L // 2 seconds
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        // Initialize sample products if first time
        initializeSampleData()
        
        // Check login status and navigate accordingly
        checkLoginAndNavigate()
    }
    
    private fun initializeSampleData() {
        lifecycleScope.launch {
            val productRepository = (application as MiniGroceryApplication).productRepository
            val sampleProducts = productRepository.getSampleProducts()
            productRepository.insertOrUpdateProducts(sampleProducts)
        }
    }
    
    private fun checkLoginAndNavigate() {
        lifecycleScope.launch {
            val isLoggedIn = viewModel.checkLoginStatus()
            
            Handler(Looper.getMainLooper()).postDelayed({
                if (isLoggedIn) {
                    startActivity(Intent(this@SplashActivity, MainActivity::class.java))
                } else {
                    startActivity(Intent(this@SplashActivity, LoginActivity::class.java))
                }
                finish()
            }, SPLASH_DELAY)
        }
    }
}
