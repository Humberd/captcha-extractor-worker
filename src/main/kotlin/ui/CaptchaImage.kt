package ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.loadImageBitmap
import image.IMG_HEIGHT
import image.IMG_WIDTH
import image.SplitImage
import image.base64ToBytes
import types.pxToDp
import java.io.ByteArrayInputStream

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
