package com.example.gym_coleman_application

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.gym_coleman_application.data.room.AppDatabase
import com.example.gym_coleman_application.data.room.User
import com.example.gym_coleman_application.data.room.UserDao
import com.example.gym_coleman_application.repository.UserRepository
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class UserDaoTest {

    private lateinit var database: AppDatabase
    private lateinit var userDao: UserDao
    lateinit var userRepository:UserRepository

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AppDatabase::class.java
        ).allowMainThreadQueries().build()

        userDao = database.userDao()
        userRepository = UserRepository(userDao)
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun insertar_y_obtener_usuario() = runBlocking {
        val user = User(
            username = "admin",
            password = "1234"
        )

        userDao.insertUser(user)

        val usuarios = userDao.getAllUsers()

        assertEquals(1, usuarios.size)
        assertEquals("admin", usuarios[0].username)
        assertEquals("1234", usuarios[0].password)
        //Esta es una prueba unitaria de Room usando una base de datos en memoria.
        //Inserto un usuario mediante el DAO y luego verifico que pueda recuperarlo correctamente.
        //De esta forma valido que la capa de persistencia funcione sin depender de la base real
    }


    @Test
    //Qué valida: que el login funcione correctamente con datos válidos.
    fun login_devuelve_usuario_correcto() = runBlocking {
        // Arrange
        val user = User(
            username = "admin",
            password = "1234"
        )
        userDao.insertUser(user)

        // Act
        val resultado = userDao.login("admin", "1234")

        // Assert
        assertNotNull(resultado)
        assertEquals("admin", resultado?.username)
        //Verifico que el método login del DAO devuelva un usuario cuando las credenciales son correctas
    }

    @Test
    //Qué valida: que Room devuelva null cuando el login es incorrecto.
    fun login_falla_con_password_incorrecta() = runBlocking {
        // Arrange
        val user = User(
            username = "admin",
            password = "1234"
        )
        userDao.insertUser(user)

        // Act
        val resultado = userDao.login("admin", "0000")

        // Assert
        assertNull(resultado)
        //Valido que el login no entregue datos si las credenciales son incorrectas
    }

    @Test
    fun eliminar_usuario_lo_remueve_de_la_bd() = runBlocking {
        // Arrange
        val user = User(username = "admin", password = "1234")
        userDao.insertUser(user)

        // Act
        userDao.deleteUser(user)
        val usuarios = userDao.getAllUsers()

        // Assert
        assertTrue(usuarios.isEmpty())

        //Compruebo que el método deleteUser elimina correctamente el registro
    }

    @Test
    fun repository_inserta_usuario_correctamente() = runBlocking {
        // Arrange
        val user = User(username = "admin", password = "1234")

        // Act
        userRepository.insertUser(user)

        // Assert
        val usuarios = userDao.getAllUsers()
        assertEquals(1, usuarios.size)
        assertEquals("admin", usuarios[0].username)

        //Esta prueba valida la capa Repository, que actúa como intermediario entre ViewModel y Room
    }




}

