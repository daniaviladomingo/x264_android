package daniel.avila.x264.encoder

import daniel.avila.x264encoder.jni.X264Encoder


class H264EncoderImp(
    private val h264Encoder: X264Encoder,
    private val yuvRotateUtil: YUVRotateUtil
) : IH264Encoder {

    private var isReleased = true
    private var timeStamp = 0
    private var time = 0L

    private var width = 0
    private var height = 0

    override fun init(width: Int, height: Int, fps: Int, bitrate: Int) {
        this.width = width
        this.height = height

        if (!isReleased) {
            release()
        }
        timeStamp = bitrate / fps
        h264Encoder.init(width, height, fps, bitrate)
        isReleased = false
    }

    override fun release() {
        time = 0
        h264Encoder.release()
        isReleased = true
    }

    override fun yuv420spToH264(data: ByteArray, rotate: Int, h264Frame: (ByteArray) -> Unit){
        time += timeStamp

        val rotatedYuv420sp = when(rotate){
            0-> yuvRotateUtil.rotateYUV420Degree180(data, width, height)
            90 -> yuvRotateUtil.rotateYUV420Degree90(data, height, width)
            180 -> yuvRotateUtil.rotateYUV420Degree180(data, width, height)
            270 -> yuvRotateUtil.rotateYUV420Degree270(data, height, width)
            else -> throw IllegalArgumentException("preview rotation must be 0, 90, 180 or 270")
        }

        val yuv420 = yuv420spToYuv420(rotatedYuv420sp, width, height)
        h264Encoder.encodeFromYUV420(yuv420, time) {
            h264Frame(it)
        }
    }

    private fun yuv420spToYuv420(yuv420sp: ByteArray, width: Int, height: Int): ByteArray {
        val yuv420 = ByteArray(width * height * 3 / 2)
        val frameSize = width * height
        //copy y
        var i = 0
        while (i < frameSize) {
            yuv420[i] = yuv420sp[i]
            i++
        }
        i = 0
        var j = 0
        while (j < frameSize / 2) {
            yuv420[i + frameSize * 5 / 4] = yuv420sp[j + frameSize]
            i++
            j += 2
        }
        i = 0
        j = 1
        while (j < frameSize / 2) {
            yuv420[i + frameSize] = yuv420sp[j + frameSize]
            i++
            j += 2
        }
        return yuv420
    }
}