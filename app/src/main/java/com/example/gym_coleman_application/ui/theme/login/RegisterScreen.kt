package com.example.gym_coleman_application.ui.theme.login

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.gym_coleman_application.viewmodel.RegisterViewModel

// IMPORTANTE: NO definimos colores aquí para evitar conflictos.
// Usaremos los que definiste en LoginScreen o los genéricos.

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(
    navController: NavController,
    registerViewModel: RegisterViewModel,
) {
    val state by registerViewModel.uiState.collectAsState()

    LaunchedEffect(state.success) {
        if (state.success) {
            navController.navigate("login") {
                popUpTo("login") { inclusive = true }
            }
            registerViewModel.resetSuccess()
        }
    }

    // Usamos un color oscuro directo si la variable DarkBlueBackground da problemas
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color(0xFF001636) // Azul Oscuro Coleman
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = "CREAR CUENTA",
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.sp
                ),
                color = Color(0xFFFFD700) // Amarillo Coleman
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Únete al equipo Coleman",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.LightGray
            )

            Spacer(modifier = Modifier.height(40.dp))

            // -- INPUT USUARIO --
            OutlinedTextField(
                value = state.username,
                onValueChange = { registerViewModel.onUsernameChange(it) },
                label = { Text("Nuevo Usuario") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                isError = state.error != null,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFFFFD700),
                    unfocusedBorderColor = Color.White,
                    focusedLabelColor = Color(0xFFFFD700),
                    unfocusedLabelColor = Color.Gray,
                    cursorColor = Color(0xFFFFD700),
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White,
                    errorBorderColor = Color.Red,
                    errorLabelColor = Color.Red
                ),
                shape = RoundedCornerShape(8.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // -- INPUT CONTRASEÑA --
            OutlinedTextField(
                value = state.password,
                onValueChange = { registerViewModel.onPasswordChange(it) },
                label = { Text("Nueva Contraseña") },
                visualTransformation = PasswordVisualTransformation(),
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                isError = state.error != null,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFFFFD700),
                    unfocusedBorderColor = Color.White,
                    focusedLabelColor = Color(0xFFFFD700),
                    unfocusedLabelColor = Color.Gray,
                    cursorColor = Color(0xFFFFD700),
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White,
                    errorBorderColor = Color.Red,
                    errorLabelColor = Color.Red
                ),
                shape = RoundedCornerShape(8.dp)
            )

            if (state.error != null) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = state.error ?: "",
                    color = Color.Red,
                    style = MaterialTheme.typography.bodySmall
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = { registerViewModel.register() },
                modifier = Modifier.fillMaxWidth().height(50.dp),
                enabled = state.username.isNotBlank() && state.password.isNotBlank(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFFFD700),
                    contentColor = Color.Black,
                    disabledContainerColor = Color.Gray,
                    disabledContentColor = Color.DarkGray
                ),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(
                    text = "REGISTRARSE",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            TextButton(onClick = { navController.popBackStack() }) {
                Text("Volver al inicio de sesión", color = Color.White)
            }
        }
    }
}