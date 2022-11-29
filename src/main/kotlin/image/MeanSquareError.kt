package image

import mu.KotlinLogging
import org.bytedeco.opencv.global.opencv_imgproc
import org.bytedeco.opencv.opencv_quality.QualityMSE
import types.MatJavaCV

private val logger = KotlinLogging.logger {}

fun meanSquaredError(img1: MatJavaCV, img2: MatJavaCV): MatJavaCV {
    val img1Gray = MatJavaCV()
    opencv_imgproc.cvtColor(img1, img1Gray, opencv_imgproc.COLOR_RGB2GRAY)

    val img2Gray = MatJavaCV()
    opencv_imgproc.cvtColor(img2, img2Gray, opencv_imgproc.COLOR_RGB2GRAY)

    val diff = MatJavaCV(img1.size())
    val scalar = QualityMSE.compute(img1Gray, img2Gray, diff)
    logger.info {scalar}
    return img1Gray
}
