package com.example.gym_coleman_application.ui.theme.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.gym_coleman_application.R // Asegúrate de importar tu R
import com.example.gym_coleman_application.viewmodel.LoginViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.CoroutineScope

// Definimos los colores del diseño
val DarkBlueBackground = Color(0xFF001636) // Azul oscuro profundo
val ColemanYellow = Color(0xFFFFD700)      // Amarillo dorado
val WhiteText = Color.White

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    navController: NavController,
    onLoginSuccess: (String) -> Unit,
    onRegisterClick: () -> Unit,
    loginViewModel: LoginViewModel
) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var showError by remember { mutableStateOf(false) }

    // Usamos Surface para pintar el fondo de toda la pantalla
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = DarkBlueBackground
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(rememberScrollState()), // Scroll por si la pantalla es chica
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {

            Spacer(modifier = Modifier.height(40.dp))

            // 1. LOGO DUOC UC (Asegúrate de tener la imagen en res/drawable)
            // Si no tienes la imagen aún, comenta esta línea
            Image(
                painter = painterResource(id = R.drawable.duocuc), // Cambia por el nombre real de tu imagen
                contentDescription = "Logo Duoc UC",
                modifier = Modifier
                    .height(60.dp)
                    .background(Color.White, shape = RoundedCornerShape(4.dp))
                    .padding(8.dp),
                contentScale = ContentScale.Fit
            )

            Spacer(modifier = Modifier.height(40.dp))

            // 2. BANNER RONNIE COLEMAN
            // Si no tienes la imagen aún, comenta esta línea
            Card(
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier.fillMaxWidth().height(150.dp),
                elevation = CardDefaults.cardElevation(8.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.planes), // Cambia por el nombre real de tu imagen
                    contentDescription = "Banner Gym",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // 3. TEXTOS TITULO
            Text(
                text = "GYM COLEMAN",
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 2.sp
                ),
                color = WhiteText
            )

            Text(
                text = "Entrena. Supera. Inspira.",
                style = MaterialTheme.typography.bodyLarge,
                color = ColemanYellow
            )

            Spacer(modifier = Modifier.height(32.dp))

            // 4. INPUTS (CAMPOS DE TEXTO)
            // Usuario
            OutlinedTextField(
                value = username,
                onValueChange = { username = it },
                label = { Text("Usuario") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = ColemanYellow,
                    unfocusedBorderColor = Color.White,
                    focusedLabelColor = ColemanYellow,
                    unfocusedLabelColor = Color.Gray,
                    cursorColor = ColemanYellow,
                    focusedTextColor = WhiteText,
                    unfocusedTextColor = WhiteText
                ),
                shape = RoundedCornerShape(8.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Contraseña
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Contraseña") },
                visualTransformation = PasswordVisualTransformation(),
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = ColemanYellow,
                    unfocusedBorderColor = Color.White,
                    focusedLabelColor = ColemanYellow,
                    unfocusedLabelColor = Color.Gray,
                    cursorColor = ColemanYellow,
                    focusedTextColor = WhiteText,
                    unfocusedTextColor = WhiteText
                ),
                shape = RoundedCornerShape(8.dp)
            )

            // Mensaje de Error
            if (showError) {
                Text(
                    "Usuario o contraseña incorrectos",
                    color = Color.Red,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            // 5. BOTÓN INGRESAR
            Button(
                onClick = {
                    CoroutineScope(Dispatchers.Main).launch {
                        val success = loginViewModel.login(username, password)
                        if (success) {
                            showError = false
                            onLoginSuccess(username)
                        } else {
                            showError = true
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = ColemanYellow,
                    contentColor = Color.Black
                ),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(
                    text = "INGRESAR",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // 6. LINK REGISTRO
            TextButton(onClick = onRegisterClick) {
                Text(
                    text = "¿No tienes cuenta? Regístrate",
                    color = WhiteText
                )
            }
        }
    }
}