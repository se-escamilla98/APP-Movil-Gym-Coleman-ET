package com.example.gym_coleman_application.repository


import com.example.gym_coleman_application.data.room.User
import com.example.gym_coleman_application.data.room.UserDao


class UserRepository(private val userDao: UserDao) {

    // ✅ NUEVO: Función puente
    suspend fun getUserByUsername(username: String): User? {
        return userDao.getUserByUsername(username)
    }

    suspend fun registerUser(username: String, password: String) {
        userDao.insertUser(
            User(username = username, password = password)
        )
    }

    suspend fun insertUser(user: User) {
        userDao.insertUser(user)
    }

    suspend fun login(username: String, password: String): User? {
        return userDao.login(username, password)
    }

    suspend fun getAllUsers(): List<User> {
        return userDao.getAllUsers()
    }

    suspend fun deleteUser(user: User) {
        userDao.deleteUser(user)
    }
}
