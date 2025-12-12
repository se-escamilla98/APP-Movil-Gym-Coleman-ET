package com.example.gym_coleman_application.ui.theme.shop

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.gym_coleman_application.viewmodel.CartViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartScreen(
    navController: NavController,
    cartViewModel: CartViewModel
) {
    val cartItems by cartViewModel.cartItems.collectAsState()

    // Calcular total a pagar
    val total = cartItems.sumOf { it.price * it.quantity }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Mi Carrito") })
        },
        bottomBar = {
            // Barra inferior con el Total y botón de Pagar
            if (cartItems.isNotEmpty()) {
                Surface(
                    shadowElevation = 8.dp,
                    color = MaterialTheme.colorScheme.surfaceVariant
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text("Total:", style = MaterialTheme.typography.titleLarge)
                            Text("$${total}", style = MaterialTheme.typography.titleLarge)
                        }
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(
                            onClick = {
                                cartViewModel.clearCart()
                                navController.popBackStack() // Volver atrás tras comprar
                            },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Finalizar Compra")
                        }
                    }
                }
            }
        }
    ) { padding ->
        if (cartItems.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("Tu carrito está vacío 🛒")
            }
        } else {
            LazyColumn(
                modifier = Modifier.padding(padding).fillMaxSize()
            ) {
                items(cartItems) { item ->
                    ListItem(
                        headlineContent = { Text(item.name) },
                        supportingContent = { Text("${item.quantity} x $${item.price}") },
                        trailingContent = {
                            IconButton(onClick = { cartViewModel.removeFromCart(item) }) {
                                Icon(Icons.Default.Delete, contentDescription = "Eliminar", tint = Color.Red)
                            }
                        }
                    )
                    Divider()
                }
            }
        }
    }
}