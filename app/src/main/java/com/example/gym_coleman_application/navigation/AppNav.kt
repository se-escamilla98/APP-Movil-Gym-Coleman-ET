package com.example.gym_coleman_application.navigation

import ExerciseScreen
import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.gym_coleman_application.R
import com.example.gym_coleman_application.ui.theme.login.LoginScreen
import com.example.gym_coleman_application.ui.theme.login.RegisterScreen
import com.example.gym_coleman_application.ui.theme.shop.CartScreen
import com.example.gym_coleman_application.ui.theme.shop.CatalogScreen
import com.example.gym_coleman_application.view.DrawerMenu
import com.example.gym_coleman_application.view.MapScreen
import com.example.gym_coleman_application.view.ProductoFormScreen
import com.example.gym_coleman_application.viewmodel.CartViewModel
import com.example.gym_coleman_application.viewmodel.LoginViewModel
import com.example.gym_coleman_application.viewmodel.RegisterViewModel

@SuppressLint("ViewModelConstructorInComposable")
@Composable
fun AppNav(
    loginViewModel: LoginViewModel,
    registerViewModel: RegisterViewModel,
    cartViewModel: CartViewModel // ✅ 1. Agregamos el ViewModel del carrito
) {
    val navController = rememberNavController()

    // NOTA: Ya no instanciamos la BD aquí porque los ViewModels vienen listos desde MainActivity

    NavHost(
        navController = navController,
        startDestination = "login"
    ) {

        // --- LOGIN ---
        composable("login") {
            LoginScreen(
                navController = navController,
                onLoginSuccess = { username ->
                    navController.navigate("DrawerMenu/$username") {
                        popUpTo("login") { inclusive = true }
                    }
                },
                onRegisterClick = { navController.navigate("register") },
                loginViewModel = loginViewModel
            )
        }

        // --- REGISTRO ---
        composable("register") {
            RegisterScreen(
                navController = navController,
                registerViewModel = registerViewModel
            )
        }

        // --- MENÚ PRINCIPAL ---
        composable(
            "DrawerMenu/{username}",
            arguments = listOf(navArgument("username") { type = NavType.StringType })
        ) { entry ->
            val username = entry.arguments?.getString("username").orEmpty()


            DrawerMenu(
                username = username,
                navController = navController,
                cartViewModel = cartViewModel
            )
        }

        // --- TIENDA (NUEVO) ---
        composable("catalog") {
            CatalogScreen(navController = navController, cartViewModel = cartViewModel)
        }

        // --- CARRITO (NUEVO) ---
        composable("cart") {
            CartScreen(navController = navController, cartViewModel = cartViewModel)
        }

        // --- OTROS ---
        composable(
            "ProductoFormScreen/{nombre}/{precio}/{imagen}",
            arguments = listOf(
                navArgument("nombre") { type = NavType.StringType },
                navArgument("precio") { type = NavType.StringType },
                navArgument("imagen") { type = NavType.IntType }
            )
        ) { entry ->
            val nombre = entry.arguments?.getString("nombre") ?: ""
            val precio = entry.arguments?.getString("precio") ?: ""
            val imagen = entry.arguments?.getInt("imagen") ?: R.drawable.creatina // Asegúrate que este recurso exista

            ProductoFormScreen(
                navController = navController,
                nombre = nombre,
                precio = precio,
                imagen = imagen
            )
        }

        composable("mapa") { MapScreen() }
        composable("trainings") { ExerciseScreen() }
    }
}