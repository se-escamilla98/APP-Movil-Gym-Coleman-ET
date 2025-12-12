package com.example.gym_coleman_application.navigation

import ExerciseScreen
import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.gym_coleman_application.data.room.AppDatabase
import com.example.gym_coleman_application.viewmodel.LoginViewModel
import com.example.gym_coleman_application.viewmodel.RegisterViewModel
import com.example.gym_coleman_application.ui.theme.login.LoginScreen
import com.example.gym_coleman_application.ui.theme.login.RegisterScreen
import com.example.gym_coleman_application.view.DrawerMenu
import com.example.gym_coleman_application.view.MapScreen
import com.example.gym_coleman_application.view.ProductoFormScreen
import com.example.gym_coleman_application.R
import com.example.gym_coleman_application.repository.UserRepository

@SuppressLint("ViewModelConstructorInComposable")
@Composable
fun AppNav(
    loginViewModel: LoginViewModel,
    registerViewModel: RegisterViewModel
) {
    val navController = rememberNavController()
    val context = LocalContext.current

    // ⭐ Instancia BD Room
    val db = AppDatabase.getDatabase(context)
    val userRepository = UserRepository(db.userDao())

    NavHost(
        navController = navController,
        startDestination = "login"
    ) {

        // ------------------------------------
        // LOGIN
        // ------------------------------------
        composable("login") {

            val loginViewModel = LoginViewModel(userRepository)

            LoginScreen(
                navController = navController,
                onLoginSuccess = { username ->
                    navController.navigate("DrawerMenu/$username") {
                        popUpTo("login") { inclusive = true }
                    }
                },
                onRegisterClick = {
                    navController.navigate("register")
                },
                loginViewModel = loginViewModel
            )
        }

        // ------------------------------------
        // REGISTER (ARREGLADO)
        // ------------------------------------
// 🔹 REGISTRO
        composable("register") {
            val registerViewModel = RegisterViewModel(userRepository)

            RegisterScreen(
                navController = navController,
                registerViewModel = registerViewModel
            )
        }



        // ------------------------------------
        // DRAWER MENU
        // ------------------------------------
        composable(
            "DrawerMenu/{username}",
            arguments = listOf(navArgument("username") { type = NavType.StringType })
        ) { entry ->
            val username = entry.arguments?.getString("username").orEmpty()
            DrawerMenu(username = username, navController = navController)
        }

        // ------------------------------------
        // PRODUCT FORM
        // ------------------------------------
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
            val imagen = entry.arguments?.getInt("imagen") ?: R.drawable.creatina

            ProductoFormScreen(
                navController = navController,
                nombre = nombre,
                precio = precio,
                imagen = imagen
            )
        }

        // MAPA
        composable("mapa") {
            MapScreen()
        }

        // EJERCICIOS API
        composable("trainings") {
            ExerciseScreen()
        }
    }
}
