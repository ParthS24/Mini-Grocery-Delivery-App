package com.minigrocery.app

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.minigrocery.app.data.repository.UserRepository

/**
 * ViewModel Factory for MainViewModel
 */
class MainViewModelFactory(
    private val userRepository: UserRepository
) : ViewModelProvider.Factory {
    
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(userRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
