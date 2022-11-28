package image

import org.opencv.core.Mat
import org.opencv.core.MatOfByte
import org.opencv.imgcodecs.Imgcodecs
import java.util.*

fun base64ToMat(data: String): Mat {
    val imgBytes = base64ToBytes(data)
    return Imgcodecs.imdecode(MatOfByte(*imgBytes), Imgcodecs.IMREAD_COLOR)
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

