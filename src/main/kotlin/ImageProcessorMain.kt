import db.DbConnector
import db.models.CaptchaChallengeDb
import image.SplitImage
import image.base64ToMat
import image.splitLegendBar
import kotlinx.coroutines.delay
import mu.KotlinLogging
import org.bytedeco.javacpp.Loader
import org.bytedeco.javacv.CanvasFrame
import org.bytedeco.javacv.OpenCVFrameConverter.ToMat
import org.bytedeco.opencv.opencv_java
import org.bytedeco.opencv.opencv_quality.QualityMSE
import org.ktorm.dsl.from
import org.ktorm.dsl.limit
import org.ktorm.dsl.select
import types.MatJavaCV

private val logger = KotlinLogging.logger {}

suspend fun main() {
    // https://github.com/bytedeco/javacpp-presets/tree/master/opencv#documentation
    Loader.load(opencv_java::class.java)
    val frame = CanvasFrame("Hello")
    val converter = ToMat()

    val images = mutableListOf<SplitImage>()

    for (queryRowSet in DbConnector.database.from(CaptchaChallengeDb).select().limit(5)) {
        val image = initialImagePrepare(queryRowSet[CaptchaChallengeDb.imageBase64Src]!!)
        images.add(image)
    }

//    findClusters(images)
//    val foo = meanSquaredError(images[1].pictureImage, images[0].pictureImage)

    var displayedIndex = 0
    while (frame.isVisible) {
        if (displayedIndex >= images.size) {
            displayedIndex = 0
        }
        val imageToDisplay = images[displayedIndex]
//        frame.showImage(converter.convert(foo))
        frame.showImage(converter.convert(imageToDisplay.originalRawImage))
        delay(500)
        displayedIndex++
    }

    frame.dispose()
}

fun initialImagePrepare(base64ImgSrc: String): SplitImage {
    val decodedRawImage = base64ToMat(base64ImgSrc)
    return splitLegendBar(decodedRawImage)
}

fun findClusters(images: List<SplitImage>) {
    logger.info { images.size }
}


fun meanSquaredError(img1: MatJavaCV, img2: MatJavaCV): MatJavaCV {
    val diff = MatJavaCV()
    QualityMSE.compute(img1, img2, diff)
    return diff
}
