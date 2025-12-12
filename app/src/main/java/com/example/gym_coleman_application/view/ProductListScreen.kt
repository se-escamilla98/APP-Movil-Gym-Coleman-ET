package com.example.gym_coleman_application.view

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.gym_coleman_application.R
// ✅ CAMBIO 1: Importamos el ViewModel y la entidad de Base de Datos
import com.example.gym_coleman_application.viewmodel.CartViewModel
import com.example.gym_coleman_application.data.room.ProductCart as Product

data class ProductoSimple(
    val nombre: String,
    val precio: String,
    val imagen: Int
)

@Composable
fun ProductListScreen(
    navController: NavController,
    cartViewModel: CartViewModel // ✅ CAMBIO 2: Recibimos el ViewModel aquí
) {

    val productosDisponibles = listOf(
        ProductoSimple("Creatina", "25.000", R.drawable.creatina),
        ProductoSimple("Proteína Whey", "60.000", R.drawable.proteina),
        ProductoSimple("Pre-entreno", "48.990", R.drawable.pre),
        ProductoSimple("Omega 3", "30.000", R.drawable.omega3),
        ProductoSimple("Barra de Proteína", "1.500", R.drawable.barraproteina),
        ProductoSimple("Shaker", "7.990", R.drawable.shaker)
    )

    var showBanner by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        showBanner = true
    }

    // Estado para mostrar un mensaje temporal (Snackbar) al agregar
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { paddingValues ->

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {

            item {
                AnimatedVisibility(
                    visible = showBanner,
                    enter = fadeIn(animationSpec = tween(800)) +
                            slideInVertically(initialOffsetY = { -80 }, animationSpec = tween(600))
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.planes),
                        contentDescription = "Banner Gimnasio Coleman",
                        modifier = Modifier.fillMaxWidth().height(130.dp),
                        contentScale = ContentScale.Crop
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
            }

            item {
                Text(
                    text = "Nuestros Productos",
                    style = MaterialTheme.typography.headlineLarge,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
                Spacer(modifier = Modifier.height(16.dp))
            }

            itemsIndexed(productosDisponibles) { index, producto ->

                var visible by remember { mutableStateOf(false) }
                val scale by animateFloatAsState(
                    targetValue = if (visible) 1f else 0.9f,
                    animationSpec = tween(500, easing = FastOutSlowInEasing)
                )

                LaunchedEffect(Unit) {
                    kotlinx.coroutines.delay(index * 120L)
                    visible = true
                }

                AnimatedVisibility(
                    visible = visible,
                    enter = fadeIn(animationSpec = tween(500)) + slideInHorizontally(initialOffsetX = { 100 }),
                ) {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 8.dp)
                            .scale(scale)
                            .clickable {
                                navController.navigate("ProductoFormScreen/${producto.nombre}/${producto.precio}/${producto.imagen}")
                            },
                        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth().padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {

                            Image(
                                painter = painterResource(id = producto.imagen),
                                contentDescription = producto.nombre,
                                modifier = Modifier.size(70.dp),
                                contentScale = ContentScale.Crop
                            )

                            Spacer(modifier = Modifier.width(16.dp))

                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = producto.nombre,
                                    style = MaterialTheme.typography.titleLarge,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                                Text(
                                    text = "$${producto.precio}",
                                    style = MaterialTheme.typography.titleMedium,
                                    color = MaterialTheme.colorScheme.primary,
                                    fontWeight = FontWeight.SemiBold
                                )
                            }

                            // ✅ CAMBIO 3: Botón para AGREGAR AL CARRITO
                            IconButton(
                                onClick = {
                                    // 1. Limpiamos el precio (quitamos el punto "25.000" -> 25000.0)
                                    val precioLimpio = producto.precio.replace(".", "").toDoubleOrNull() ?: 0.0

                                    // 2. Creamos el objeto para la Base de Datos
                                    val productoBD = Product(
                                        name = producto.nombre,
                                        price = precioLimpio,
                                        // Guardamos el ID de la imagen como String temporalmente
                                        // para poder recuperarlo después
                                        imageUrl = producto.imagen.toString()
                                    )

                                    // 3. Llamamos al ViewModel
                                    cartViewModel.addToCart(productoBD)

                                    // (Opcional) Mostrar confirmación visual
                                    // scope.launch { snackbarHostState.showSnackbar("Agregado al carrito") }
                                }
                            ) {
                                Icon(
                                    imageVector = Icons.Default.ShoppingCart,
                                    contentDescription = "Agregar al carrito",
                                    tint = MaterialTheme.colorScheme.primary
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}