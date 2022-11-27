import db.DbConnector
import db.models.CaptchaChallengeDb
import mu.KotlinLogging
import org.ktorm.dsl.from
import org.ktorm.dsl.limit
import org.ktorm.dsl.select
import org.opencv.core.Core
import org.opencv.core.MatOfByte
import org.opencv.imgcodecs.Imgcodecs
import java.util.*


private val logger = KotlinLogging.logger {}

fun main() {
    System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

    for (queryRowSet in DbConnector.database.from(CaptchaChallengeDb).select().limit(1)) {
        val imgBase64 = queryRowSet[CaptchaChallengeDb.imageBase64Src]!!
//        logger.info { imgBase64 }
        val imgBytes = base64ToBytes(imgBase64)
        val image = Imgcodecs.imdecode(MatOfByte(*imgBytes), Imgcodecs.IMREAD_UNCHANGED)
    }
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

