package com.minigrocery.app.data.model

/**
 * Order model for checkout and order history
 */
data class Order(
    val id: String,
    val items: List<CartItem>,
    val deliveryAddress: String,
    val paymentMethod: String,
    val subtotal: Double,
    val deliveryCharge: Double = 40.0,
    val totalAmount: Double,
    val orderDate: Long = System.currentTimeMillis(),
    val estimatedDeliveryTime: Long = System.currentTimeMillis() + (30 * 60 * 1000), // 30 minutes
    val status: OrderStatus = OrderStatus.CONFIRMED,
    val customerName: String = "",
    val customerPhone: String = ""
) {
    /**
     * Get formatted order date
     */
    fun getFormattedOrderDate(): String {
        return java.text.SimpleDateFormat("dd MMM yyyy, hh:mm a", java.util.Locale.getDefault())
            .format(java.util.Date(orderDate))
    }
    
    /**
     * Get formatted estimated delivery time
     */
    fun getFormattedDeliveryTime(): String {
        return java.text.SimpleDateFormat("hh:mm a", java.util.Locale.getDefault())
            .format(java.util.Date(estimatedDeliveryTime))
    }
    
    /**
     * Get formatted total amount
     */
    fun getFormattedTotalAmount(): String {
        return "₹${String.format("%.2f", totalAmount)}"
    }
    
    /**
     * Get formatted delivery charge
     */
    fun getFormattedDeliveryCharge(): String {
        return "₹${String.format("%.2f", deliveryCharge)}"
    }
}

/**
 * Order status enum
 */
enum class OrderStatus {
    CONFIRMED,
    PREPARING,
    OUT_FOR_DELIVERY,
    DELIVERED,
    CANCELLED
}
