package com.example.gym_coleman_application.view

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.*
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInHorizontally
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.gym_coleman_application.R
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DrawerMenu(username: String, navController: NavController, cartViewModel: com.example.gym_coleman_application.viewmodel.CartViewModel) {

    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    // --- ANIMACIONES DE FONDO ---
    val infiniteTransition = rememberInfiniteTransition(label = "")
    val animatedOffset by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1000f,
        animationSpec = infiniteRepeatable(
            animation = tween(8000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = ""
    )

    val animatedBrush = Brush.verticalGradient(
        colors = listOf(Color(0xFF0047AB), Color(0xFF0A1931), Color(0xFF00111F)),
        startY = animatedOffset,
        endY = animatedOffset + 1000f
    )

    // --- ANIMACIÓN PESA ---
    val weightTransition = rememberInfiniteTransition(label = "")
    val offsetY by weightTransition.animateFloat(
        initialValue = 0f, targetValue = -10f,
        animationSpec = infiniteRepeatable(animation = tween(1000, easing = LinearEasing), repeatMode = RepeatMode.Reverse), label = ""
    )
    val rotation by weightTransition.animateFloat(
        initialValue = -8f, targetValue = 8f,
        animationSpec = infiniteRepeatable(animation = tween(1500, easing = LinearEasing), repeatMode = RepeatMode.Reverse), label = ""
    )

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(
                drawerContainerColor = Color.Transparent,
                modifier = Modifier.fillMaxHeight().background(animatedBrush)
            ) {
                Spacer(modifier = Modifier.height(24.dp))

                // Logo Pesa
                Image(
                    painter = painterResource(id = R.drawable.pesa),
                    contentDescription = "Pesa animada",
                    modifier = Modifier
                        .size(90.dp)
                        .align(Alignment.CenterHorizontally)
                        .offset(y = offsetY.dp)
                        .rotate(rotation)
                )

                Spacer(modifier = Modifier.height(12.dp))

                // Textos
                Text(
                    text = "GYM Coleman",
                    color = Color.White,
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.align(Alignment.CenterHorizontally).padding(bottom = 8.dp)
                )

                Text(
                    text = "Bienvenido, $username",
                    color = Color(0xFFFFD700),
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )

                Divider(color = Color.White.copy(alpha = 0.3f), modifier = Modifier.padding(vertical = 16.dp))

                // Opciones del Menú
                val items = listOf(
                    "Inicio" to "🏠",
                    "Tienda Suplementos" to "💪", // Esta es la NUEVA tienda con BD
                    "Mi Carrito" to "🛒",
                    "Entrenamientos" to "🔥",
                    "Mapa de Gimnasios" to "📍",
                    "Cerrar sesión" to "🚪"
                )

                items.forEachIndexed { index, pair ->
                    AnimatedVisibility(
                        visible = true,
                        enter = fadeIn(animationSpec = tween(300, delayMillis = index * 150)) +
                                slideInHorizontally(initialOffsetX = { -100 })
                    ) {
                        DrawerItem(
                            text = pair.first,
                            icon = pair.second,
                            onClick = {
                                scope.launch {
                                    drawerState.close()
                                    when (pair.first) {
                                        "Inicio" -> {
                                            // Si quieres recargar inicio, podrías navegar a una ruta "home"
                                            // Por ahora, al cerrar el drawer ya verás la pantalla de inicio
                                        }
                                        "Tienda Suplementos" -> navController.navigate("catalog")
                                        "Mi Carrito" -> navController.navigate("cart")
                                        "Entrenamientos" -> navController.navigate("trainings")
                                        "Mapa de Gimnasios" -> navController.navigate("mapa")
                                        "Cerrar sesión" -> {
                                            navController.navigate("login") {
                                                popUpTo("login") { inclusive = true }
                                            }
                                        }
                                    }
                                }
                            }
                        )
                    }
                }
            }
        }
    ) {
        // CONTENIDO PRINCIPAL DEL SCAFFOLD
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("GYM Coleman", color = Color(0xFF0047AB)) }, // Título original
                    navigationIcon = {
                        IconButton(onClick = { scope.launch { if (drawerState.isClosed) drawerState.open() else drawerState.close() } }) {
                            Icon(imageVector = Icons.Default.List, contentDescription = "Menú", tint = Color(0xFF0047AB))
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
                )
            }
        ) { innerPadding ->
            Box(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
                    .background(Color.White),
                contentAlignment = Alignment.TopCenter
            ) {
                // ✅ AQUÍ ESTÁ LA RESTAURACIÓN:
                // Volvemos a llamar a tu pantalla original con el banner y la lista
                // Llamo a la pantalla de productos
                ProductListScreen(
                    navController = navController,
                    cartViewModel = cartViewModel // <--- AGREGAR ESTO
                )
            }
        }
    }
}

@Composable
fun DrawerItem(text: String, icon: String, onClick: () -> Unit) {
    TextButton(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Text(
            text = "$icon  $text",
            color = Color.White,
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Medium
        )
    }
}