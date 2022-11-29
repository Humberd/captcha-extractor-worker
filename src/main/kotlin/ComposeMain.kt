import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import image.SplitImage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import mu.KotlinLogging
import ui.MainWindow

private val logger = KotlinLogging.logger {}

fun main() = application {
    val coroutineScope = rememberCoroutineScope()
    var images by remember { mutableStateOf(listOf<SplitImage>()) }
    LaunchedEffect(Unit) {
        coroutineScope.launch(Dispatchers.IO) {
            images = getAndConvertImagesFromDb(10)
        }
    }

    val state = rememberWindowState(width = 800.dp, height = 400.dp)
    Window(
        onCloseRequest = ::exitApplication,
        title = "Compose for Desktop",
        state = state
    ) {
        MaterialTheme {
            MainWindow(images)
        }
    }
}
