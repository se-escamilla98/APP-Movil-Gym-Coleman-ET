package com.example.gym_coleman_application.repository

import com.example.gym_coleman_application.data.room.CartDao
import com.example.gym_coleman_application.data.room.CartItem
import com.example.gym_coleman_application.data.room.ProductCart
import kotlinx.coroutines.flow.Flow

class CartRepository(private val cartDao: CartDao) {

    // --- Productos ---
    val allProducts: Flow<List<ProductCart>> = cartDao.getAllProducts()

    suspend fun insertProduct(product: ProductCart) {
        cartDao.insertProduct(product)
    }

    // --- Carrito ---
    val cartItems: Flow<List<CartItem>> = cartDao.getCartItems()

    suspend fun addToCart(product: ProductCart) {
        // Verificar si ya existe para sumar cantidad
        val existingItem = cartDao.getCartItemByProductId(product.id)

        if (existingItem != null) {
            val updatedItem = existingItem.copy(quantity = existingItem.quantity + 1)
            cartDao.insertCartItem(updatedItem)
        } else {
            val newItem = CartItem(
                productId = product.id,
                name = product.name,
                price = product.price,
                quantity = 1
            )
            cartDao.insertCartItem(newItem)
        }
    }

    suspend fun removeFromCart(item: CartItem) {
        cartDao.deleteCartItem(item)
    }

    suspend fun clearCart() {
        cartDao.clearCart()
    }
}