package com.minigrocery.app

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.minigrocery.app.data.repository.UserRepository
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch

/**
 * ViewModel for MainActivity
 */
class MainViewModel(
    private val userRepository: UserRepository
) : ViewModel() {
    
    private val _logoutEvent = MutableSharedFlow<Boolean>()
    val logoutEvent: SharedFlow<Boolean> = _logoutEvent
    
    /**
     * Logout user
     */
    fun logout() {
        viewModelScope.launch {
            userRepository.logout()
            _logoutEvent.emit(true)
        }
    }
}
