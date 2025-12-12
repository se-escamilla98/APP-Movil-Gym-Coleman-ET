package com.example.gym_coleman_application.data.room

import androidx.room.*

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: User)

    // ✅ NUEVO: Buscar solo por nombre de usuario
    @Query("SELECT * FROM users WHERE username = :username LIMIT 1")
    suspend fun getUserByUsername(username: String): User?

    // Para login
    @Query("""
        SELECT * FROM users 
        WHERE username = :username 
        AND password = :password 
        LIMIT 1
    """)
    suspend fun login(username: String, password: String): User?

    // Para obtener todos (sin parámetros)
    @Query("SELECT * FROM users")
    suspend fun getAllUsers(): List<User>



    @Delete
    suspend fun deleteUser(user: User)
}


