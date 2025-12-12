import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.gym_coleman_application.viewmodel.ExerciseViewModel

@Composable
fun ExerciseScreen(vm: ExerciseViewModel = viewModel()) {

    val exercises = vm.uiState.collectAsState().value

    LaunchedEffect(Unit) {
        vm.loadData()
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(all = 16.dp)
    ) {

        items(items = exercises) { item ->

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            ) {

                Column(Modifier.padding(all = 16.dp)) {

                    // 🔥 NOMBRE DEL EJERCICIO
                    Text(
                        text = "🔥 Ejercicio: ${item.name ?: "Sin nombre"}",
                        style = MaterialTheme.typography.titleMedium
                    )

                    // 🔥 CATEGORÍA
                    Text(
                        text = "📁 Categoría: ${item.category?.name ?: "Sin categoría"}"
                    )

                    // 🔥 DESCRIPCIÓN
                    Text(
                        text = "📝 Descripción: ${item.description ?: "Sin descripción"}"
                    )

                    // 🔥 MÚSCULOS PRIMARIOS
                    Text(
                        text = "💪 Músculos: ${
                            item.muscles?.joinToString { it.name ?: "" } ?: "N/A"
                        }"
                    )

                    // 🔥 MÚSCULOS SECUNDARIOS
                    Text(
                        text = "🦿 Secundarios: ${
                            item.muscles_secondary?.joinToString { it.name ?: "" } ?: "N/A"
                        }"
                    )

                    // 🔥 IMAGEN (si existe)
                    val img = item.images?.firstOrNull()?.image

                    if (img != null) {
                        AsyncImage(
                            model = img,
                            contentDescription = "Imagen del ejercicio",
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(200.dp)
                                .padding(top = 8.dp)
                        )
                    }

                }
                }
            }
        }
    }

