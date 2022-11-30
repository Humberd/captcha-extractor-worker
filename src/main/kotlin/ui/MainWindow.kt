package ui

import androidx.compose.foundation.layout.Row
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import image.SplitImage
import mu.KotlinLogging

private val logger = KotlinLogging.logger {}

@ExperimentalComposeUiApi
@Composable
fun MainWindow(images: List<SplitImage>) {
    var image1Index by remember { mutableStateOf(0) }
    var image2Index by remember { mutableStateOf(1) }
    var selectedImage by remember { mutableStateOf(SelectedImage.Image1) }

    if (images.size == 0) {
        return CircularProgressIndicator()
    }

    Row {
        SelectableCaptchaImage(
            name = "img1",
            image = images[image1Index],
            index = image1Index,
            onIndexChange = {
                logger.info { "index changed: $it" }
                image1Index = wrapIndexAround(images, it)
            },
            isSelected = selectedImage == SelectedImage.Image1,
            onSelectChange = {
                selectedImage = SelectedImage.Image1
            }
        )
        SelectableCaptchaImage(
            name = "img2",
            image = images[image2Index],
            index = image2Index,
            onIndexChange = {
                image2Index = wrapIndexAround(images, it)
            },
            isSelected = selectedImage == SelectedImage.Image2,
            onSelectChange = {
                selectedImage = SelectedImage.Image2
            },
        )
    }

//    val buffer = arrayOf<java.nio.Buffer>(images[0].pictureImage.createBuffer())
}

fun wrapIndexAround(list: List<*>, index: Int): Int {
    return if (index < 0) {
        list.size - 1
    } else if (index >= list.size) {
        0
    } else {
        index
    }
}

enum class SelectedImage {
    Image1,
    Image2
}

//@Preview
//@Composable
//fun MainWindowPreview(){
//    MainWindow(listOf(
//        SplitImage(
//        )
//    ))
//}
