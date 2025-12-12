package com.example.gym_coleman_application

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.navigation.compose.rememberNavController
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.gym_coleman_application.view.ProductListScreen
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ProductListScreenTest {

    @get:Rule
    val composeRule = createAndroidComposeRule<ComponentActivity>()

    /**
     * Test 1:
     * Verifica que los productos se renderizan correctamente en pantalla
     */
    @Test
    fun los_productos_se_muestran_en_pantalla() {

        // Arrange + Act
        composeRule.setContent {
            ProductListScreen(
                navController = rememberNavController()
            )
        }

        composeRule.waitForIdle()

        // Assert
        composeRule.onNodeWithText("Creatina", useUnmergedTree = true)
            .assertExists()
            .assertIsDisplayed()

        composeRule.onNodeWithText("Proteína Whey", useUnmergedTree = true)
            .assertExists()
            .assertIsDisplayed()

        composeRule.onNodeWithText("Pre-entreno", useUnmergedTree = true)
            .assertExists()
            .assertIsDisplayed()
    }

    /**
     * Test 2:
     * Verifica que al hacer click en un producto, la interacción funciona
     * y no produce errores de navegación
     */
    @Test
    fun click_en_producto_dispara_interaccion() {

        composeRule.setContent {
            ProductListScreen(
                navController = rememberNavController() // fake
            )
        }

        composeRule.waitForIdle()

        // Act: click sobre el producto
        composeRule.onNodeWithText("Creatina", useUnmergedTree = true)
            .performClick()

        // Assert:
        // Si llega acá sin crashear → interacción OK
        composeRule.onNodeWithText("Creatina")
            .assertExists()
    }

    @Test
    fun lista_de_productos_no_esta_vacia() {

        composeRule.setContent {
            ProductListScreen(navController = rememberNavController())
        }

        composeRule.onNodeWithText("Creatina")
            .assertExists()
    }
}

