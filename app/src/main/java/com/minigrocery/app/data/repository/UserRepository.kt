package com.minigrocery.app.data.repository

import com.minigrocery.app.utils.PreferenceManager
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf

/**
 * Repository for User data and preferences
 */
class UserRepository(
    private val preferenceManager: PreferenceManager
) {
    
    /**
     * Check if user is logged in
     */
    suspend fun isLoggedIn(): Boolean {
        return preferenceManager.isLoggedIn()
    }
    
    /**
     * Get user phone number
     */
    suspend fun getUserPhone(): String? {
        return preferenceManager.getUserPhone()
    }
    
    /**
     * Save user login state
     */
    suspend fun saveUserLogin(phoneNumber: String) {
        preferenceManager.saveUserLogin(phoneNumber)
    }
    
    /**
     * Logout user
     */
    suspend fun logout() {
        preferenceManager.clearUserData()
    }
    
    /**
     * Get user name
     */
    suspend fun getUserName(): String? {
        return preferenceManager.getUserName()
    }
    
    /**
     * Save user name
     */
    suspend fun saveUserName(name: String) {
        preferenceManager.saveUserName(name)
    }
    
    /**
     * Get user email
     */
    suspend fun getUserEmail(): String? {
        return preferenceManager.getUserEmail()
    }
    
    /**
     * Save user email
     */
    suspend fun saveUserEmail(email: String) {
        preferenceManager.saveUserEmail(email)
    }
    
    /**
     * Get user address
     */
    suspend fun getUserAddress(): String? {
        return preferenceManager.getUserAddress()
    }
    
    /**
     * Save user address
     */
    suspend fun saveUserAddress(address: String) {
        preferenceManager.saveUserAddress(address)
    }
    
    /**
     * Get dark mode preference
     */
    fun getDarkModePreference(): Flow<Boolean> {
        return flow {
            emit(preferenceManager.isDarkMode())
        }
    }
    
    /**
     * Set dark mode preference
     */
    suspend fun setDarkModePreference(isDarkMode: Boolean) {
        preferenceManager.saveDarkMode(isDarkMode)
    }
}
