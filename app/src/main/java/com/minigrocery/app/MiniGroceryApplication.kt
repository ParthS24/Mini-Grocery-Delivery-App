package com.minigrocery.app

import android.app.Application
import com.minigrocery.app.data.local.AppDatabase
import com.minigrocery.app.data.repository.CartRepository
import com.minigrocery.app.data.repository.ProductRepository
import com.minigrocery.app.data.repository.UserRepository
import com.minigrocery.app.utils.PreferenceManager

/**
 * Application class for initializing global components
 */
class MiniGroceryApplication : Application() {
    
    // Database instance
    val database by lazy { AppDatabase.getDatabase(this) }
    
    // Repository instances
    val productRepository by lazy { ProductRepository(database.productDao()) }
    val cartRepository by lazy { CartRepository(database.cartDao()) }
    val userRepository by lazy { UserRepository(PreferenceManager(this)) }
    
    override fun onCreate() {
        super.onCreate()
        // Initialize any global components here
        instance = this
    }
    
    companion object {
        lateinit var instance: MiniGroceryApplication
            private set
    }
}
