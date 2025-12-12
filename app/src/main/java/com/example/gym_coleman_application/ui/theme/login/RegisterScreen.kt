package com.example.gym_coleman_application.ui.theme.login

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.gym_coleman_application.viewmodel.RegisterViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(
    navController: NavController,
    registerViewModel: RegisterViewModel,
) {
    // Observamos el estado
    val state by registerViewModel.uiState.collectAsState()

    // Navegación segura cuando success es true
    LaunchedEffect(state.success) {
        if (state.success) {
            navController.navigate("login") {
                popUpTo("login") { inclusive = true }
            }
            registerViewModel.resetSuccess()
        }
    }

    Column(
        modifier = Modifier.fillMaxSize().padding(32.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text("Crear Cuenta", style = MaterialTheme.typography.headlineMedium)

        Spacer(modifier = Modifier.height(32.dp))

        OutlinedTextField(
            value = state.username,
            onValueChange = { registerViewModel.onUsernameChange(it) },
            label = { Text("Usuario") },
            modifier = Modifier.fillMaxWidth(),
            isError = state.error != null
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = state.password,
            onValueChange = { registerViewModel.onPasswordChange(it) },
            label = { Text("Contraseña") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth(),
            isError = state.error != null
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = { registerViewModel.register() },
            modifier = Modifier.fillMaxWidth(),
            enabled = state.username.isNotBlank() && state.password.isNotBlank()
        ) {
            Text("Registrarse")
        }

        if (state.error != null) {
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = state.error ?: "",
                color = MaterialTheme.colorScheme.error
            )
        }
    }
}