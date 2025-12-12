package com.example.gym_coleman_application.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gym_coleman_application.repository.UserRepository
import com.example.gym_coleman_application.ui.theme.login.RegisterUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RegisterViewModel(
    private val repo: UserRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(RegisterUiState())
    val uiState: StateFlow<RegisterUiState> = _uiState.asStateFlow()

    fun onUsernameChange(newValue: String) {
        _uiState.update { it.copy(username = newValue, error = null) }
    }

    fun onPasswordChange(newValue: String) {
        _uiState.update { it.copy(password = newValue, error = null) }
    }

    fun register() {
        val currentState = _uiState.value

        if (currentState.username.isBlank() || currentState.password.isBlank()) {
            _uiState.update { it.copy(error = "Completa todos los campos") }
            return
        }

        viewModelScope.launch {
            try {
                // 1. Verificamos si el nombre ya está ocupado
                val userExists = repo.getUserByUsername(currentState.username) != null

                if (userExists) {
                    _uiState.update { it.copy(error = "El usuario '${currentState.username}' ya existe") }
                } else {
                    // 2. Si no existe, lo registramos
                    repo.registerUser(currentState.username, currentState.password)
                    _uiState.update { it.copy(success = true) }
                }
            } catch (e: Exception) {
                // 3. Si algo falla (ej. error de BD), atrapamos el error AQUÍ
                e.printStackTrace()
                _uiState.update { it.copy(error = "Error al registrar: ${e.message}") }
            }
        }
    }

    fun resetSuccess() {
        _uiState.update { it.copy(success = false) }
    }
}