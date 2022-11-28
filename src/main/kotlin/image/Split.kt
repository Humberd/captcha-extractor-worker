package image

import org.opencv.core.Mat
import org.opencv.core.Point
import org.opencv.core.Rect

private const val IMG_HEIGHT = 230
private const val IMG_WIDTH = 400
private const val LEGEND_BAR_HEIGHT = 30

fun splitLegendBar(src: Mat): SplitImage {
    require(src.height() == IMG_HEIGHT && src.width() == IMG_WIDTH)
    val legendBarRect = Rect(
        Point(0.0, (IMG_HEIGHT - LEGEND_BAR_HEIGHT).toDouble()),
        Point(IMG_WIDTH.toDouble(), (IMG_HEIGHT).toDouble())
    )
    val pictureRect = Rect(
        Point(0.0, 0.0),
        Point(IMG_WIDTH.toDouble(), (IMG_HEIGHT - LEGEND_BAR_HEIGHT).toDouble())
    )
    return SplitImage(
        pictureImage = Mat(src, pictureRect),
        legendBarImage = Mat(src, legendBarRect)
    )
}

data class SplitImage(
    val pictureImage: Mat,
    val legendBarImage: Mat
)
