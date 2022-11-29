package types

import org.opencv.core.Mat

typealias MatOpenCV = Mat
typealias MatJavaCV = org.bytedeco.opencv.opencv_core.Mat

// https://github.com/bytedeco/javacv-examples/blob/master/OpenCV_Cookbook/README.md
fun MatJavaCV.height() = this.arrayHeight()
fun MatJavaCV.width() = this.arrayWidth()
