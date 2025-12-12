package com.example.gym_coleman_application.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.gym_coleman_application.data.dao.ProductoDao
import com.example.gym_coleman_application.data.model.Producto
import com.example.gym_coleman_application.data.room.User
import com.example.gym_coleman_application.data.room.UserDao

@Database(
    entities = [
        Producto::class,
        User::class  // ← NUEVO
    ],
    version = 2,              // IMPORTANTE subir versión
    exportSchema = false
)
abstract class ProductoDataBase : RoomDatabase() {
    abstract fun productoDao(): ProductoDao
    abstract fun userDao(): UserDao

}
