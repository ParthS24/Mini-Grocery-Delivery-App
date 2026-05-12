package com.minigrocery.app.data.local

import androidx.room.*
import com.minigrocery.app.data.model.Product
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object for Product entity
 */
@Dao
interface ProductDao {
    
    /**
     * Get all products
     */
    @Query("SELECT * FROM products")
    fun getAllProducts(): Flow<List<Product>>
    
    /**
     * Get product by ID
     */
    @Query("SELECT * FROM products WHERE id = :productId")
    suspend fun getProductById(productId: String): Product?
    
    /**
     * Get products by category
     */
    @Query("SELECT * FROM products WHERE category = :category")
    fun getProductsByCategory(category: String): Flow<List<Product>>
    
    /**
     * Search products by name
     */
    @Query("SELECT * FROM products WHERE name LIKE '%' || :query || '%'")
    fun searchProducts(query: String): Flow<List<Product>>
    
    /**
     * Get favorite products
     */
    @Query("SELECT * FROM products WHERE isFavorite = 1")
    fun getFavoriteProducts(): Flow<List<Product>>
    
    /**
     * Get all categories
     */
    @Query("SELECT DISTINCT category FROM products")
    suspend fun getAllCategories(): List<String>
    
    /**
     * Insert or update products
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdateProducts(products: List<Product>)
    
    /**
     * Insert or update single product
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdateProduct(product: Product)
    
    /**
     * Update product favorite status
     */
    @Query("UPDATE products SET isFavorite = :isFavorite WHERE id = :productId")
    suspend fun updateFavoriteStatus(productId: String, isFavorite: Boolean)
    
    /**
     * Delete all products
     */
    @Query("DELETE FROM products")
    suspend fun deleteAllProducts()
    
    /**
     * Get products count
     */
    @Query("SELECT COUNT(*) FROM products")
    suspend fun getProductsCount(): Int
}
