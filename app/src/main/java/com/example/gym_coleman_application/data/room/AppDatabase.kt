package com.example.gym_coleman_application.data.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    // 1. AQUI AGREGAMOS LAS NUEVAS TABLAS
    entities = [User::class, ProductCart::class, CartItem::class],
    version = 3, // 2. SUBIMOS VERSION
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao
    abstract fun cartDao(): CartDao // 3. AGREGAMOS EL DAO

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "gym_database" // Asegúrate de que este nombre sea igual al que ya tenías
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}