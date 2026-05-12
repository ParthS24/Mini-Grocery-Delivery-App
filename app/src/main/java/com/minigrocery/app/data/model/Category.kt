package com.minigrocery.app.data.model

/**
 * Category model for products
 */
data class Category(
    val id: String,
    val name: String,
    val imageUrl: String,
    val itemCount: Int = 0
) {
    companion object {
        fun getDefaultCategories(): List<Category> {
            return listOf(
                Category("1", "Fruits", "https://images.unsplash.com/photo-1610832958506-aa56368176cf?w=150&h=150&fit=crop", 25),
                Category("2", "Vegetables", "https://images.unsplash.com/photo-1590596940548-76a0a2a9289e?w=150&h=150&fit=crop", 32),
                Category("3", "Dairy", "https://images.unsplash.com/photo-1550583724-b2692b85b150?w=150&h=150&fit=crop", 18),
                Category("4", "Snacks", "https://images.unsplash.com/photo-1566479165772-6a3680e3b3be?w=150&h=150&fit=crop", 45),
                Category("5", "Beverages", "https://images.unsplash.com/photo-1544787219-7f47ccb76574?w=150&h=150&fit=crop", 28),
                Category("6", "Bakery", "https://images.unsplash.com/photo-1509440159596-0249088772ff?w=150&h=150&fit=crop", 22),
                Category("7", "Meat", "https://images.unsplash.com/photo-1529692236671-f1f6e9d0e6be?w=150&h=150&fit=crop", 15),
                Category("8", "Personal Care", "https://images.unsplash.com/photo-1526947425969-8fd9f18d8c23?w=150&h=150&fit=crop", 35)
            )
        }
    }
}
