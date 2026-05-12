package com.minigrocery.app.data.repository

import com.minigrocery.app.data.local.ProductDao
import com.minigrocery.app.data.model.Product
import com.minigrocery.app.data.model.Category
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Repository for Product data
 */
class ProductRepository @Inject constructor(
    private val productDao: ProductDao
) {
    
    /**
     * Get all products
     */
    fun getAllProducts(): Flow<List<Product>> {
        return productDao.getAllProducts()
    }
    
    /**
     * Get product by ID
     */
    suspend fun getProductById(productId: String): Product? {
        return productDao.getProductById(productId)
    }
    
    /**
     * Get products by category
     */
    fun getProductsByCategory(category: String): Flow<List<Product>> {
        return productDao.getProductsByCategory(category)
    }
    
    /**
     * Search products
     */
    fun searchProducts(query: String): Flow<List<Product>> {
        return productDao.searchProducts(query)
    }
    
    /**
     * Get favorite products
     */
    fun getFavoriteProducts(): Flow<List<Product>> {
        return productDao.getFavoriteProducts()
    }
    
    /**
     * Get all categories
     */
    suspend fun getAllCategories(): List<String> {
        return productDao.getAllCategories()
    }
    
    /**
     * Insert or update products
     */
    suspend fun insertOrUpdateProducts(products: List<Product>) {
        productDao.insertOrUpdateProducts(products)
    }
    
    /**
     * Insert or update single product
     */
    suspend fun insertOrUpdateProduct(product: Product) {
        productDao.insertOrUpdateProduct(product)
    }
    
    /**
     * Update favorite status
     */
    suspend fun updateFavoriteStatus(productId: String, isFavorite: Boolean) {
        productDao.updateFavoriteStatus(productId, isFavorite)
    }
    
    /**
     * Get sample products for demo
     */
    fun getSampleProducts(): List<Product> {
        return listOf(
            Product(
                id = "1",
                name = "Fresh Red Apples",
                description = "Crispy and sweet red apples, perfect for snacking",
                category = "Fruits",
                price = 120.0,
                discount = 10,
                originalPrice = 133.0,
                imageUrl = "https://images.unsplash.com/photo-1560806887-1e4cd0b6cbd6?w=300&h=300&fit=crop",
                rating = 4.5f,
                stock = 50,
                unit = "kg"
            ),
            Product(
                id = "2",
                name = "Organic Bananas",
                description = "Ripe organic bananas, rich in potassium",
                category = "Fruits",
                price = 60.0,
                discount = 0,
                originalPrice = 60.0,
                imageUrl = "https://images.unsplash.com/photo-1571771894821-ce9b6c11b08e?w=300&h=300&fit=crop",
                rating = 4.3f,
                stock = 100,
                unit = "dozen"
            ),
            Product(
                id = "3",
                name = "Fresh Tomatoes",
                description = "Juicy red tomatoes, perfect for salads and cooking",
                category = "Vegetables",
                price = 40.0,
                discount = 15,
                originalPrice = 47.0,
                imageUrl = "https://images.unsplash.com/photo-1546470427-e92b2c9c09d6?w=300&h=300&fit=crop",
                rating = 4.6f,
                stock = 75,
                unit = "kg"
            ),
            Product(
                id = "4",
                name = "Whole Milk",
                description = "Fresh whole milk, rich and creamy",
                category = "Dairy",
                price = 55.0,
                discount = 0,
                originalPrice = 55.0,
                imageUrl = "https://images.unsplash.com/photo-1550583724-b2692b85b150?w=300&h=300&fit=crop",
                rating = 4.7f,
                stock = 30,
                unit = "liter"
            ),
            Product(
                id = "5",
                name = "Potato Chips",
                description = "Crunchy potato chips with sea salt",
                category = "Snacks",
                price = 35.0,
                discount = 20,
                originalPrice = 44.0,
                imageUrl = "https://images.unsplash.com/photo-1566479165772-6a3680e3b3be?w=300&h=300&fit=crop",
                rating = 4.2f,
                stock = 60,
                unit = "pack"
            ),
            Product(
                id = "6",
                name = "Orange Juice",
                description = "100% pure orange juice, no added sugar",
                category = "Beverages",
                price = 85.0,
                discount = 5,
                originalPrice = 89.0,
                imageUrl = "https://images.unsplash.com/photo-1613478223719-2ab802602423?w=300&h=300&fit=crop",
                rating = 4.4f,
                stock = 40,
                unit = "liter"
            ),
            Product(
                id = "7",
                name = "Whole Wheat Bread",
                description = "Freshly baked whole wheat bread",
                category = "Bakery",
                price = 45.0,
                discount = 0,
                originalPrice = 45.0,
                imageUrl = "https://images.unsplash.com/photo-1509440159596-0249088772ff?w=300&h=300&fit=crop",
                rating = 4.5f,
                stock = 25,
                unit = "loaf"
            ),
            Product(
                id = "8",
                name = "Chicken Breast",
                description = "Fresh chicken breast, boneless and skinless",
                category = "Meat",
                price = 280.0,
                discount = 10,
                originalPrice = 311.0,
                imageUrl = "https://images.unsplash.com/photo-1529692236671-f1f6e9d0e6be?w=300&h=300&fit=crop",
                rating = 4.6f,
                stock = 20,
                unit = "kg"
            ),
            Product(
                id = "9",
                name = "Fresh Strawberries",
                description = "Sweet and juicy strawberries",
                category = "Fruits",
                price = 150.0,
                discount = 0,
                originalPrice = 150.0,
                imageUrl = "https://images.unsplash.com/photo-1464965911861-746a04b4bca6?w=300&h=300&fit=crop",
                rating = 4.8f,
                stock = 35,
                unit = "box"
            ),
            Product(
                id = "10",
                name = "Greek Yogurt",
                description = "Creamy Greek yogurt, high in protein",
                category = "Dairy",
                price = 75.0,
                discount = 15,
                originalPrice = 88.0,
                imageUrl = "https://images.unsplash.com/photo-1488477181946-6428a0291777?w=300&h=300&fit=crop",
                rating = 4.5f,
                stock = 45,
                unit = "cup"
            ),
            Product(
                id = "11",
                name = "Green Tea",
                description = "Organic green tea bags, antioxidant rich",
                category = "Beverages",
                price = 120.0,
                discount = 0,
                originalPrice = 120.0,
                imageUrl = "https://images.unsplash.com/photo-1576092768241-dec231879fc3?w=300&h=300&fit=crop",
                rating = 4.3f,
                stock = 50,
                unit = "pack"
            ),
            Product(
                id = "12",
                name = "Dark Chocolate",
                description = "70% cocoa dark chocolate bar",
                category = "Snacks",
                price = 95.0,
                discount = 10,
                originalPrice = 105.0,
                imageUrl = "https://images.unsplash.com/photo-1506905925346-21bda4d32df4?w=300&h=300&fit=crop",
                rating = 4.7f,
                stock = 40,
                unit = "bar"
            ),
            Product(
                id = "13",
                name = "Fresh Spinach",
                description = "Organic spinach leaves, rich in iron",
                category = "Vegetables",
                price = 35.0,
                discount = 0,
                originalPrice = 35.0,
                imageUrl = "https://images.unsplash.com/photo-1574386282615-a33b8dceba77?w=300&h=300&fit=crop",
                rating = 4.4f,
                stock = 60,
                unit = "bunch"
            ),
            Product(
                id = "14",
                name = "Almonds",
                description = "Premium quality almonds, rich in nutrients",
                category = "Snacks",
                price = 200.0,
                discount = 5,
                originalPrice = 210.0,
                imageUrl = "https://images.unsplash.com/photo-1528207776546-365bb710ee93?w=300&h=300&fit=crop",
                rating = 4.6f,
                stock = 30,
                unit = "pack"
            ),
            Product(
                id = "15",
                name = "Croissants",
                description = "Buttery and flaky croissants, freshly baked",
                category = "Bakery",
                price = 80.0,
                discount = 0,
                originalPrice = 80.0,
                imageUrl = "https://images.unsplash.com/photo-1555507036-ab1f4038808a?w=300&h=300&fit=crop",
                rating = 4.5f,
                stock = 20,
                unit = "pack"
            ),
            Product(
                id = "16",
                name = "Mineral Water",
                description = "Pure mineral water, 1L bottle",
                category = "Beverages",
                price = 25.0,
                discount = 0,
                originalPrice = 25.0,
                imageUrl = "https://images.unsplash.com/photo-1548839140-29a749e1b4d2?w=300&h=300&fit=crop",
                rating = 4.2f,
                stock = 100,
                unit = "bottle"
            ),
            Product(
                id = "17",
                name = "Fresh Carrots",
                description = "Crunchy orange carrots, perfect for salads",
                category = "Vegetables",
                price = 30.0,
                discount = 10,
                originalPrice = 33.0,
                imageUrl = "https://images.unsplash.com/photo-1445282768818-728615cc910a?w=300&h=300&fit=crop",
                rating = 4.3f,
                stock = 80,
                unit = "kg"
            ),
            Product(
                id = "18",
                name = "Cheese Slices",
                description = "Processed cheese slices, perfect for sandwiches",
                category = "Dairy",
                price = 110.0,
                discount = 0,
                originalPrice = 110.0,
                imageUrl = "https://images.unsplash.com/photo-1486477181946-746a04b4bca6?w=300&h=300&fit=crop",
                rating = 4.4f,
                stock = 35,
                unit = "pack"
            ),
            Product(
                id = "19",
                name = "Peanut Butter",
                description = "Creamy peanut butter, no added sugar",
                category = "Snacks",
                price = 140.0,
                discount = 15,
                originalPrice = 165.0,
                imageUrl = "https://images.unsplash.com/photo-1506258840774-931a771ab44d?w=300&h=300&fit=crop",
                rating = 4.6f,
                stock = 45,
                unit = "jar"
            ),
            Product(
                id = "20",
                name = "Face Wash",
                description = "Gentle face wash for all skin types",
                category = "Personal Care",
                price = 180.0,
                discount = 20,
                originalPrice = 225.0,
                imageUrl = "https://images.unsplash.com/photo-1526947425969-8fd9f18d8c23?w=300&h=300&fit=crop",
                rating = 4.3f,
                stock = 25,
                unit = "tube"
            )
        )
    }
}
