package com.minigrocery.app.utils

/**
 * App constants
 */
object Constants {
    
    // Network
    const val BASE_URL = "https://api.minigrocery.com/"
    const val CONNECT_TIMEOUT = 30L
    const val READ_TIMEOUT = 30L
    const val WRITE_TIMEOUT = 30L
    
    // Authentication
    const val FAKE_OTP = "1234"
    const val PHONE_NUMBER_LENGTH = 10
    const val PHONE_PATTERN = "^[6-9]\\d{9}$"
    const val EXTRA_PRODUCT_ID = "extra_product_id"
    const val EXTRA_PRODUCT_NAME = "product_name"
    
    // Cart
    const val DELIVERY_CHARGE = 40.0
    const val FREE_DELIVERY_THRESHOLD = 500.0
    const val MIN_ORDER_AMOUNT = 100.0
    
    // UI
    const val ANIMATION_DURATION = 300L
    const val SHIMMER_DURATION = 1000L
    
    // Database
    const val DATABASE_NAME = "mini_grocery_database"
    const val DATABASE_VERSION = 1
    
    // SharedPreferences/DataStore
    const val PREFERENCES_NAME = "mini_grocery_preferences"
    
    // Intent Extras
    const val EXTRA_USER_PHONE = "extra_user_phone"
    const val EXTRA_ORDER_ID = "order_id"
    const val EXTRA_ORDER_TOTAL = "order_total"
    
    // Error Messages
    const val ERROR_NETWORK = "No internet connection. Please check your network and try again."
    const val ERROR_SERVER = "Server error. Please try again later."
    const val ERROR_UNKNOWN = "Something went wrong. Please try again."
    const val ERROR_INVALID_PHONE = "Please enter a valid 10-digit mobile number"
    const val ERROR_INVALID_OTP = "Invalid OTP. Please try again."
    const val ERROR_EMPTY_CART = "Your cart is empty"
    const val ERROR_MIN_ORDER = "Minimum order amount is ₹100"
    
    // Success Messages
    const val SUCCESS_LOGIN = "Login successful"
    const val SUCCESS_ADDED_TO_CART = "Added to cart"
    const val SUCCESS_REMOVED_FROM_CART = "Removed from cart"
    const val SUCCESS_ORDER_PLACED = "Order placed successfully"
    const val SUCCESS_FAVORITE_ADDED = "Added to favorites"
    const val SUCCESS_FAVORITE_REMOVED = "Removed from favorites"
    
    // Categories
    val CATEGORIES = listOf(
        "All",
        "Fruits",
        "Vegetables", 
        "Dairy",
        "Snacks",
        "Beverages",
        "Bakery",
        "Meat",
        "Personal Care"
    )
    
    // Payment Methods
    const val PAYMENT_COD = "Cash on Delivery"
    const val PAYMENT_ONLINE = "Online Payment"
    
    // Order Status
    const val STATUS_CONFIRMED = "Confirmed"
    const val STATUS_PREPARING = "Preparing"
    const val STATUS_OUT_FOR_DELIVERY = "Out for Delivery"
    const val STATUS_DELIVERED = "Delivered"
    const val STATUS_CANCELLED = "Cancelled"
}
