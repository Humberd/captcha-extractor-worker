package ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.*
import androidx.compose.ui.res.loadImageBitmap
import androidx.compose.ui.unit.dp
import image.IMG_HEIGHT
import image.IMG_WIDTH
import image.SplitImage
import image.base64ToBytes
import mu.KotlinLogging
import types.pxToDp
import java.io.ByteArrayInputStream

private val logger = KotlinLogging.logger {}

@Composable
fun CaptchaImage(image: SplitImage) {
    val bytes = base64ToBytes(image.base64ImgSrc)
    Image(
        bitmap = loadImageBitmap(ByteArrayInputStream(bytes)),
        contentDescription = "CaptchaImage",
        modifier = Modifier
            .width(IMG_WIDTH.pxToDp())
            .height(IMG_HEIGHT.pxToDp())
    )
}

@ExperimentalComposeUiApi
@Composable
fun SelectableCaptchaImage(
    image: SplitImage,
    index: Int,
    onIndexChange: (newIndex: Int) -> Unit,
    isSelected: Boolean,
    onSelectChange: () -> Unit,
    name: String
) {
    Column {
        Box(modifier = Modifier
            .clickable {
                onSelectChange()
            }
            .then(
                if (isSelected) {
                    Modifier.border(2.dp, Color.Red)
                } else {
                    Modifier
                }
            )
            .onKeyEvent {
                if (!isSelected || it.type != KeyEventType.KeyUp) {
                    return@onKeyEvent false
                }
                if (it.key == Key.DirectionRight) {
                    onIndexChange(index + 1)
                    return@onKeyEvent true
                } else if (it.key == Key.DirectionLeft) {
                    onIndexChange(index - 1)
                    return@onKeyEvent true
                }
                return@onKeyEvent false
            }

        ) {
            CaptchaImage(image)
        }
        Text("Index: $index")
    }
}

@Composable
fun SelectableCaptchaImageState(index: Int) {
    var selectedIndex by remember { mutableStateOf(index) }
}

