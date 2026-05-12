package com.minigrocery.app

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.minigrocery.app.databinding.ActivityMainBinding
import com.minigrocery.app.utils.launchWhenStarted
import com.minigrocery.app.ui.auth.LoginActivity
import com.minigrocery.app.ui.cart.CartFragment
import com.minigrocery.app.ui.home.HomeFragment
import com.minigrocery.app.ui.profile.ProfileFragment
import com.minigrocery.app.utils.launchWhenStarted

/**
 * Main Activity with bottom navigation
 */
class MainActivity : AppCompatActivity() {
    
    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels {
        MainViewModelFactory((application as MiniGroceryApplication).userRepository)
    }
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        setupNavigation()
        setupObservers()
    }
    
    private fun setupNavigation() {
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        
        // Setup bottom navigation
        binding.bottomNavigation.setupWithNavController(navController)
        
        // Setup toolbar
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home,
                R.id.navigation_cart,
                R.id.navigation_profile
            )
        )
        binding.toolbar.setupWithNavController(navController, appBarConfiguration)
    }
    
    private fun setupObservers() {
        launchWhenStarted {
            viewModel.logoutEvent.collect { shouldLogout ->
                if (shouldLogout) {
                    logout()
                }
            }
        }
    }
    
    private fun logout() {
        val intent = Intent(this, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }
}
