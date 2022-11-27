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
import org.opencv.imgcodecs.Imgcodecs
import java.util.*


private val logger = KotlinLogging.logger {}

suspend fun main() {
    // https://github.com/bytedeco/javacpp-presets/tree/master/opencv#documentation
    Loader.load(opencv_java::class.java)
    val canvasFrame = CanvasFrame("Hello")
    val converter = ToMat()

    for (queryRowSet in DbConnector.database.from(CaptchaChallengeDb).select().limit(1)) {
        val imgBase64 = queryRowSet[CaptchaChallengeDb.imageBase64Src]!!
//        logger.info { imgBase64 }
        val imgBytes = base64ToBytes(imgBase64)
        val image = Imgcodecs.imdecode(MatOfByte(*imgBytes), Imgcodecs.IMREAD_COLOR)
        canvasFrame.showImage(converter.convert(image))
    }

    while(canvasFrame.isVisible) {
        delay(1)
    }

    canvasFrame.dispose()
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

