package daniel.avila.x264.encoder

import daniel.avila.x264encoder.jni.X264Encoder

class H264EncoderImp(
    private val h264Encoder: X264Encoder
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

    override fun nV21ToH264(data: ByteArray, h264Frame: (ByteArray) -> Unit) {
        time += timeStamp

        h264Encoder.encodeFromYUV420(data, time) {
            h264Frame(it)
        }
    }
}