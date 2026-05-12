package com.minigrocery.app.ui.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.minigrocery.app.data.repository.UserRepository
import kotlinx.coroutines.launch

/**
 * ViewModel for Splash Screen
 */
class SplashViewModel(
    private val userRepository: UserRepository
) : ViewModel() {
    
    /**
     * Check if user is logged in
     */
    suspend fun checkLoginStatus(): Boolean {
        return try {
            userRepository.isLoggedIn()
        } catch (e: Exception) {
            false
        }
    }
}
