package com.minigrocery.app.utils

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first

/**
 * Preference manager using DataStore for storing user preferences and data
 */
class PreferenceManager(private val context: Context) {
    
    companion object {
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_preferences")
        
        private val IS_LOGGED_IN = booleanPreferencesKey("is_logged_in")
        private val USER_PHONE = stringPreferencesKey("user_phone")
        private val USER_NAME = stringPreferencesKey("user_name")
        private val USER_EMAIL = stringPreferencesKey("user_email")
        private val USER_ADDRESS = stringPreferencesKey("user_address")
        private val IS_DARK_MODE = booleanPreferencesKey("is_dark_mode")
        private val FIRST_TIME = booleanPreferencesKey("first_time")
    }
    
    /**
     * Check if user is logged in
     */
    suspend fun isLoggedIn(): Boolean {
        return context.dataStore.data.first()[IS_LOGGED_IN] ?: false
    }
    
    /**
     * Get user phone number
     */
    suspend fun getUserPhone(): String? {
        return context.dataStore.data.first()[USER_PHONE]
    }
    
    /**
     * Save user login state
     */
    suspend fun saveUserLogin(phoneNumber: String) {
        context.dataStore.edit { preferences ->
            preferences[IS_LOGGED_IN] = true
            preferences[USER_PHONE] = phoneNumber
        }
    }
    
    /**
     * Save user logout state
     */
    suspend fun saveUserLogout() {
        context.dataStore.edit { preferences ->
            preferences[IS_LOGGED_IN] = false
        }
    }
    
    /**
     * Get user name
     */
    suspend fun getUserName(): String? {
        return context.dataStore.data.first()[USER_NAME]
    }
    
    /**
     * Save user name
     */
    suspend fun saveUserName(name: String) {
        context.dataStore.edit { preferences ->
            preferences[USER_NAME] = name
        }
    }
    
    /**
     * Get user email
     */
    suspend fun getUserEmail(): String? {
        return context.dataStore.data.first()[USER_EMAIL]
    }
    
    /**
     * Save user email
     */
    suspend fun saveUserEmail(email: String) {
        context.dataStore.edit { preferences ->
            preferences[USER_EMAIL] = email
        }
    }
    
    /**
     * Get user address
     */
    suspend fun getUserAddress(): String? {
        return context.dataStore.data.first()[USER_ADDRESS]
    }
    
    /**
     * Save user address
     */
    suspend fun saveUserAddress(address: String) {
        context.dataStore.edit { preferences ->
            preferences[USER_ADDRESS] = address
        }
    }
    
    /**
     * Get dark mode preference
     */
    suspend fun isDarkMode(): Boolean {
        return context.dataStore.data.first()[IS_DARK_MODE] ?: false
    }
    
    /**
     * Save dark mode preference
     */
    suspend fun saveDarkMode(isDark: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[IS_DARK_MODE] = isDark
        }
    }
    
    /**
     * Check if first time launch
     */
    suspend fun isFirstTime(): Boolean {
        return context.dataStore.data.first()[FIRST_TIME] ?: true
    }
    
    /**
     * Mark first time launch as complete
     */
    suspend fun markFirstTimeComplete() {
        context.dataStore.edit { preferences ->
            preferences[FIRST_TIME] = false
        }
    }
    
    /**
     * Clear all user data
     */
    suspend fun clearUserData() {
        context.dataStore.edit { preferences ->
            preferences[IS_LOGGED_IN] = false
            preferences.remove(USER_PHONE)
            preferences.remove(USER_NAME)
            preferences.remove(USER_EMAIL)
            preferences.remove(USER_ADDRESS)
        }
    }
}
