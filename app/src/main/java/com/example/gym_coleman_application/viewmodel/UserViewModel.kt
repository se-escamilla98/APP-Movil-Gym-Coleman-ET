package com.example.gym_coleman_application.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gym_coleman_application.data.room.User
import com.example.gym_coleman_application.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class UserViewModel(private val repo: UserRepository) : ViewModel() {

    private val _users = MutableStateFlow<List<User>>(emptyList())
    val users: StateFlow<List<User>> = _users

    init {
        loadUsers()
    }

    fun loadUsers() {
        viewModelScope.launch {
            _users.value = repo.getAllUsers()
        }
    }

    fun deleteUser(user: User) {
        viewModelScope.launch {
            repo.deleteUser(user)
            loadUsers()
        }
    }
}
