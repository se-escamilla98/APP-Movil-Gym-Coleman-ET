package com.example.gym_coleman_application.data.room

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface CartDao {

    // --- Productos del Catálogo ---
    @Query("SELECT * FROM products")
    fun getAllProducts(): Flow<List<ProductCart>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProduct(product: ProductCart)

    // --- Carrito de Compras ---
    @Query("SELECT * FROM cart_items")
    fun getCartItems(): Flow<List<CartItem>>

    @Query("SELECT * FROM cart_items WHERE productId = :productId LIMIT 1")
    suspend fun getCartItemByProductId(productId: Int): CartItem?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCartItem(item: CartItem)

    @Delete
    suspend fun deleteCartItem(item: CartItem)

    @Query("DELETE FROM cart_items")
    suspend fun clearCart()
}