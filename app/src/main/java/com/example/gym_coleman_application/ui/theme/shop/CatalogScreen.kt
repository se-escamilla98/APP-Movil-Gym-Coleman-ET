package com.example.gym_coleman_application.ui.theme.shop

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.gym_coleman_application.data.room.ProductCart as Product
import com.example.gym_coleman_application.viewmodel.CartViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CatalogScreen(
    navController: NavController,
    cartViewModel: CartViewModel
) {
    // Escuchamos los productos y el carrito desde el ViewModel
    val products by cartViewModel.products.collectAsState()
    val cartItems by cartViewModel.cartItems.collectAsState()

    // Calculamos cuántos items hay en total para el icono del carrito
    val totalItems = cartItems.sumOf { it.quantity }

    // Efecto para cargar datos de prueba si la lista está vacía
    LaunchedEffect(Unit) {
        cartViewModel.addSampleProducts()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Suplementos") },
                actions = {
                    // Botón para ir al Carrito con contador (Badge)
                    IconButton(onClick = { navController.navigate("cart") }) {
                        BadgedBox(
                            badge = {
                                if (totalItems > 0) {
                                    Badge { Text(totalItems.toString()) }
                                }
                            }
                        ) {
                            Icon(Icons.Filled.ShoppingCart, contentDescription = "Carrito")
                        }
                    }
                }
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(products) { product ->
                ProductItem(product = product) {
                    cartViewModel.addToCart(product)
                }
            }
        }
    }
}

@Composable
fun ProductItem(product: Product, onAdd: () -> Unit) {
    Card(
        elevation = CardDefaults.cardElevation(4.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(text = product.name, style = MaterialTheme.typography.titleMedium)
                Text(text = "$${product.price}", style = MaterialTheme.typography.bodyMedium)
            }
            Button(onClick = onAdd) {
                Text("Agregar")
            }
        }
    }
}