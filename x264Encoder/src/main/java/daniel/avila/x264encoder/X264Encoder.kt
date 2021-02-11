package daniel.avila.x264encoder

import android.util.Log
import java.nio.ByteBuffer

class X264Encoder {
    private lateinit var bus: (ByteArray) -> Unit

    private var mVideoBuffer: ByteBuffer? = null

    private var timeStamp = 0L

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
        this@X264Encoder.timeStamp = System.currentTimeMillis()
        encoderH264(buffer.size, time)
    }

    private fun callbackEncoder(buffer: ByteArray, length: Int) {
        Log.d("ccc", "Time to encode: ${System.currentTimeMillis() - timeStamp}")
        bus(buffer)
    }

    companion object {
        init {
            System.loadLibrary("x264encoder")
        }
    }
}