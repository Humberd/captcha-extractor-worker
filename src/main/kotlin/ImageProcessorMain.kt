import db.DbConnector
import db.models.CaptchaChallengeDb
import image.SplitImage
import image.base64ToMat
import image.meanSquaredError
import image.splitLegendBar
import mu.KotlinLogging
import org.bytedeco.javacpp.Loader
import org.bytedeco.javacv.CanvasFrame
import org.bytedeco.javacv.OpenCVFrameConverter.ToMat
import org.bytedeco.opencv.opencv_java
import org.ktorm.dsl.from
import org.ktorm.dsl.limit
import org.ktorm.dsl.select
import java.awt.event.KeyEvent

private val logger = KotlinLogging.logger {}

suspend fun main() {
    // https://github.com/bytedeco/javacpp-presets/tree/master/opencv#documentation
    Loader.load(opencv_java::class.java)
    val frame = CanvasFrame("Hello")
    val converter = ToMat()

    val images = getAndConvertImagesFromDb(30)

//    findClusters(images)
    val foo = meanSquaredError(images[16].pictureImage, images[299].pictureImage)
    val foo2 = meanSquaredError(images[16].pictureImage, images[298].pictureImage)

    var displayedIndex = 0
    while (frame.isVisible) {
        logger.info { "image index: $displayedIndex" }
        if (displayedIndex >= images.size) {
            displayedIndex = 0
        } else if (displayedIndex < 0) {
            displayedIndex = images.size - 1
        }
        val imageToDisplay = images[displayedIndex]
//        frame.showImage(converter.convert(foo))
        frame.showImage(converter.convert(imageToDisplay.originalRawImage))
        val keyType = frame.waitKey()
        if (keyType.keyCode == KeyEvent.VK_RIGHT) {
            displayedIndex++
        } else if (keyType.keyCode == KeyEvent.VK_LEFT) {
            displayedIndex--
        }
    }

    frame.dispose()
}

fun getAndConvertImagesFromDb(limit: Int = -1): MutableList<SplitImage> {
    val images = mutableListOf<SplitImage>()

    for (queryRowSet in DbConnector.database.from(CaptchaChallengeDb).select().limit(limit)) {
        val image = initialImagePrepare(queryRowSet[CaptchaChallengeDb.imageBase64Src]!!)
        images.add(image)
    }

    return images
}

fun initialImagePrepare(base64ImgSrc: String): SplitImage {
    val decodedRawImage = base64ToMat(base64ImgSrc)
    return splitLegendBar(decodedRawImage, base64ImgSrc)
}
