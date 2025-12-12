package com.example.gym_coleman_application.viewmodel

import androidx.lifecycle.ViewModel
import com.example.gym_coleman_application.repository.UserRepository

class LoginViewModel(
    private val repo: UserRepository
) : ViewModel() {

    suspend fun login(username: String, password: String): Boolean {
        val user = repo.login(username, password)
        return user != null && user.password == password
    }
}
