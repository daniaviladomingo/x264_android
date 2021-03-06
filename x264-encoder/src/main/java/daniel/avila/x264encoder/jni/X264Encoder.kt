package daniel.avila.x264encoder.jni

import java.nio.ByteBuffer

class X264Encoder {
    private lateinit var bus: (ByteArray) -> Unit

    private var mVideoBuffer: ByteBuffer? = null

    external fun init(width: Int, height: Int, fps: Int, bitrate: Int)

    external fun encoderH264(length: Int, time: Long): Int

    external fun release()

    fun encodeFromYUV420(frame: ByteArray, time: Long, onEncoded: (ByteArray) -> Unit) {
        this.bus = onEncoded
        encode(frame, time)
    }

    private fun encode(buffer: ByteArray, time: Long) {
        if (mVideoBuffer == null || mVideoBuffer!!.capacity() < buffer.size) {
            mVideoBuffer = ByteBuffer.allocateDirect((buffer.size / 1024 + 1) * 1024)
        }
        mVideoBuffer!!.rewind()
        mVideoBuffer!!.put(buffer, 0, buffer.size)
        encoderH264(buffer.size, time)
    }

    private fun callbackEncoder(buffer: ByteArray, length: Int) {
        bus(buffer)
    }

    companion object {
        init {
            System.loadLibrary("x264encoder")
        }
    }
}