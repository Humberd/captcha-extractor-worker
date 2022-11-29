package ui

import androidx.compose.foundation.layout.Row
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import image.SplitImage
import mu.KotlinLogging

private val logger = KotlinLogging.logger {}

@Composable
fun MainWindow(images: List<SplitImage>) {
    if (images.size == 0) {
        return CircularProgressIndicator()
    }

    logger.info { "images: ${images.size}" }

    Row {
        CaptchaImage(images[0])
        CaptchaImage(images[1])
    }

//    val buffer = arrayOf<java.nio.Buffer>(images[0].pictureImage.createBuffer())
}

//@Preview
//@Composable
//fun MainWindowPreview(){
//    MainWindow(listOf(
//        SplitImage(
//        )
//    ))
//}
