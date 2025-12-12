package com.example.gym_coleman_application

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.room.Room
import com.example.gym_coleman_application.data.room.AppDatabase

import com.example.gym_coleman_application.navigation.AppNav
import com.example.gym_coleman_application.repository.CartRepository
import com.example.gym_coleman_application.repository.UserRepository
import com.example.gym_coleman_application.ui.theme.theme.Gym_coleman_applicationTheme
import com.example.gym_coleman_application.viewmodel.CartViewModel
import com.example.gym_coleman_application.viewmodel.LoginViewModel
import com.example.gym_coleman_application.viewmodel.RegisterViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // ⭐ Crear instancia ROOM DATABASE
        val db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "gym_coleman_db"
        ).build()

        // ⭐ Repositorio de usuarios
        val userRepository = UserRepository(db.userDao())
        val cartRepository = CartRepository(db.cartDao())

        // ⭐ ViewModels conectados al repositorio
        val loginViewModel = LoginViewModel(userRepository)
        val registerViewModel = RegisterViewModel(userRepository)
        val cartViewModel = CartViewModel(cartRepository)

        setContent {
            Gym_coleman_applicationTheme {
                // 4. Navegación
                AppNav(
                    loginViewModel = loginViewModel,
                    registerViewModel = registerViewModel,
                    cartViewModel = cartViewModel // ✅ Ahora sí existe la variable
                )
            }
        }
    }
}
