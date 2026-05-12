package com.minigrocery.app.ui.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.minigrocery.app.data.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * ViewModel for Profile Fragment
 */
class ProfileViewModel(
    private val userRepository: UserRepository
) : ViewModel() {
    
    private val _userProfile = MutableStateFlow(UserProfile())
    val userProfile: StateFlow<UserProfile> = _userProfile.asStateFlow()
    
    /**
     * Load user profile
     */
    fun loadUserProfile() {
        viewModelScope.launch {
            try {
                val profile = UserProfile(
                    name = userRepository.getUserName(),
                    phone = userRepository.getUserPhone(),
                    email = userRepository.getUserEmail(),
                    address = userRepository.getUserAddress()
                )
                _userProfile.value = profile
            } catch (e: Exception) {
                // Handle error
            }
        }
    }
    
    /**
     * Logout user
     */
    suspend fun logout() {
        userRepository.logout()
    }
}

/**
 * User profile data class
 */
data class UserProfile(
    val name: String? = null,
    val phone: String? = null,
    val email: String? = null,
    val address: String? = null
)
