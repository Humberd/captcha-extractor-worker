package image

import mu.KotlinLogging
import org.opencv.core.Mat
import org.opencv.core.MatOfByte
import org.opencv.imgcodecs.Imgcodecs
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.util.*
import javax.imageio.ImageIO

private val logger = KotlinLogging.logger {}

fun base64ToMat(data: String): Mat {
    val imgBytes = base64ToBytes(data)
    return Imgcodecs.imdecode(MatOfByte(*imgBytes), Imgcodecs.IMREAD_COLOR)
}

fun base64ToBytes(data: String): ByteArray {
    val bytes = Base64.getDecoder().decode(cleanBase64(data))
    // OpenCV doesn't understand gif
    if (data.startsWith("data:image/gif")) {
        val bufferedImage = ImageIO.read(ByteArrayInputStream(bytes))
        val outputStream = ByteArrayOutputStream()
        ImageIO.write(bufferedImage, "png", outputStream)
        return outputStream.toByteArray()
    }
    return bytes
}

fun cleanBase64(data: String): String {
    val partSeparator = ","
    if (!data.contains(partSeparator)) {
        return data
    }
    return data.split(partSeparator)[1]
}

