package image

import org.bytedeco.opencv.opencv_core.Point
import org.bytedeco.opencv.opencv_core.Rect
import types.MatJavaCV
import types.height
import types.width

const val IMG_HEIGHT = 230
const val IMG_WIDTH = 400
const val LEGEND_BAR_HEIGHT = 30

fun splitLegendBar(src: MatJavaCV, base64ImgSrc: String): SplitImage {
    require(src.height() == IMG_HEIGHT, {
        "Image should have ${IMG_HEIGHT}px height, but has ${src.height()}"
    })
    require(src.width() == IMG_WIDTH, {
        "Image should have ${IMG_WIDTH}px width, but has ${src.width()}"
    })
    val legendBarRect = Rect(
        Point(0, IMG_HEIGHT - LEGEND_BAR_HEIGHT),
        Point(IMG_WIDTH, IMG_HEIGHT)
    )
    val pictureRect = Rect(
        Point(0, 0),
        Point(IMG_WIDTH, IMG_HEIGHT - LEGEND_BAR_HEIGHT)
    )
    return SplitImage(
        pictureImage = MatJavaCV(src, pictureRect),
        legendBarImage = MatJavaCV(src, legendBarRect),
        originalRawImage = src,
        base64ImgSrc = base64ImgSrc
    )
}

data class SplitImage(
    val pictureImage: MatJavaCV,
    val legendBarImage: MatJavaCV,
    val originalRawImage: MatJavaCV,
    val base64ImgSrc: String
)
