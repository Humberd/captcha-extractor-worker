import db.DbConnector
import db.models.CaptchaChallengeDb
import image.base64ToMat
import kotlinx.coroutines.delay
import mu.KotlinLogging
import org.bytedeco.javacpp.Loader
import org.bytedeco.javacv.CanvasFrame
import org.bytedeco.javacv.OpenCVFrameConverter.ToMat
import org.bytedeco.opencv.opencv_java
import org.ktorm.dsl.from
import org.ktorm.dsl.limit
import org.ktorm.dsl.select

private val logger = KotlinLogging.logger {}

suspend fun main() {
    // https://github.com/bytedeco/javacpp-presets/tree/master/opencv#documentation
    Loader.load(opencv_java::class.java)
    val frame = CanvasFrame("Hello")
    val converter = ToMat()

    for (queryRowSet in DbConnector.database.from(CaptchaChallengeDb).select().limit(1)) {
//        frame.showImage(converter.convert(image))

//        val grayImage = decodedImage.clone()
//        val detectedImage = decodedImage.clone()
//        Imgproc.cvtColor(decodedImage, grayImage, Imgproc.COLOR_RGB2GRAY)
//        Imgproc.blur(grayImage, detectedImage, Size(3.0, 3.0))
//        val threshold = 20.0
//        Imgproc.Canny(detectedImage, detectedImage, threshold, threshold * 3, 3, false)
//        frame.showImage(converter.convert(detectedImage))
        val decodedImage = base64ToMat(queryRowSet[CaptchaChallengeDb.imageBase64Src]!!)


    }

    while (frame.isVisible) {
        delay(1)
    }

    frame.dispose()
}

