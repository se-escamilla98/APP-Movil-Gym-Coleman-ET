package com.example.gym_coleman_application.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gym_coleman_application.data.room.CartItem
import com.example.gym_coleman_application.data.room.ProductCart
import com.example.gym_coleman_application.repository.CartRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class CartViewModel(private val repository: CartRepository) : ViewModel() {

    // Convertimos los flujos de la BD en StateFlow para la UI
    val products: StateFlow<List<ProductCart>> = repository.allProducts
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val cartItems: StateFlow<List<CartItem>> = repository.cartItems
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // Función para llenar la BD con datos de prueba (solo para que veas algo en pantalla)
    fun addSampleProducts() {
        viewModelScope.launch {
            if (products.value.isEmpty()) {
                repository.insertProduct(ProductCart(name = "Proteína Whey", price = 50000.0))
                repository.insertProduct(ProductCart(name = "Creatina Monohidrato", price = 25000.0))
                repository.insertProduct(ProductCart(name = "Pre-Entreno", price = 30000.0))
                repository.insertProduct(ProductCart(name = "BCAA", price = 20000.0))
            }
        }
    }

    fun addToCart(product: ProductCart) {
        viewModelScope.launch {
            repository.addToCart(product)
        }
    }

    fun removeFromCart(item: CartItem) {
        viewModelScope.launch {
            repository.removeFromCart(item)
        }
    }

    fun clearCart() {
        viewModelScope.launch {
            repository.clearCart()
        }
    }
}