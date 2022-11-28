import db.DbConnector
import db.models.CaptchaChallengeDb
import kotlinx.coroutines.delay
import mu.KotlinLogging
import org.bytedeco.javacpp.Loader
import org.bytedeco.javacv.CanvasFrame
import org.bytedeco.javacv.OpenCVFrameConverter.ToMat
import org.bytedeco.opencv.opencv_java
import org.ktorm.dsl.from
import org.ktorm.dsl.limit
import org.ktorm.dsl.select
import org.opencv.core.MatOfByte
import org.opencv.core.Size
import org.opencv.imgcodecs.Imgcodecs
import org.opencv.imgproc.Imgproc
import java.util.*


private val logger = KotlinLogging.logger {}

suspend fun main() {
    // https://github.com/bytedeco/javacpp-presets/tree/master/opencv#documentation
    Loader.load(opencv_java::class.java)
    val frame = CanvasFrame("Hello")
    val converter = ToMat()

    for (queryRowSet in DbConnector.database.from(CaptchaChallengeDb).select().limit(1)) {
        val imgBase64 = queryRowSet[CaptchaChallengeDb.imageBase64Src]!!
//        logger.info { imgBase64 }
        val imgBytes = base64ToBytes(imgBase64)
        val image = Imgcodecs.imdecode(MatOfByte(*imgBytes), Imgcodecs.IMREAD_COLOR)
//        frame.showImage(converter.convert(image))

        val grayImage = image.clone()
        val detectedImage = image.clone()
        Imgproc.cvtColor(image, grayImage, Imgproc.COLOR_RGB2GRAY)
        Imgproc.blur(grayImage, detectedImage, Size(3.0, 3.0))
        val threshold = 20.0
        Imgproc.Canny(detectedImage, detectedImage, threshold, threshold * 3, 3, false)
        frame.showImage(converter.convert(detectedImage))
    }

    while (frame.isVisible) {
        delay(1)
    }

    frame.dispose()
}

fun base64ToBytes(data: String): ByteArray {
    return Base64.getDecoder().decode(cleanBase64(data))
}

fun cleanBase64(data: String): String {
    val partSeparator = ","
    if (!data.contains(partSeparator)) {
        return data
    }
    return data.split(partSeparator)[1]
}

