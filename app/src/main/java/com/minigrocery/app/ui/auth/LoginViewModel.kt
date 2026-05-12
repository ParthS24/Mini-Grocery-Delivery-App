package com.minigrocery.app.ui.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.minigrocery.app.data.repository.UserRepository
import com.minigrocery.app.utils.Constants
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

/**
 * ViewModel for Login Activity
 */
class LoginViewModel(
    private val userRepository: UserRepository
) : ViewModel() {
    
    private val _loginState = MutableStateFlow<LoginState>(LoginState.Idle)
    val loginState: StateFlow<LoginState> = _loginState
    
    /**
     * Send OTP to phone number
     */
    fun sendOtp(phoneNumber: String) {
        viewModelScope.launch {
            _loginState.value = LoginState.Loading
            
            try {
                // Simulate API call delay
                delay(1000)
                
                // In real app, this would make an API call to send OTP
                // For demo, we'll just simulate success
                _loginState.value = LoginState.Success(phoneNumber)
                
            } catch (e: Exception) {
                _loginState.value = LoginState.Error(Constants.ERROR_NETWORK)
            }
        }
    }
    
    /**
     * Verify OTP
     */
    fun verifyOtp(phoneNumber: String, otp: String) {
        viewModelScope.launch {
            _loginState.value = LoginState.Loading
            
            try {
                // Simulate API call delay
                delay(500)
                
                if (otp == Constants.FAKE_OTP) {
                    // Save user login state
                    userRepository.saveUserLogin(phoneNumber)
                    _loginState.value = LoginState.OtpVerified
                } else {
                    _loginState.value = LoginState.Error(Constants.ERROR_INVALID_OTP)
                }
                
            } catch (e: Exception) {
                _loginState.value = LoginState.Error(Constants.ERROR_NETWORK)
            }
        }
    }
    
    /**
     * Reset state
     */
    fun resetState() {
        _loginState.value = LoginState.Idle
    }
}

/**
 * Sealed class for login states
 */
sealed class LoginState {
    object Idle : LoginState()
    object Loading : LoginState()
    data class Success(val phoneNumber: String) : LoginState()
    object OtpVerified : LoginState()
    data class Error(val message: String) : LoginState()
}
