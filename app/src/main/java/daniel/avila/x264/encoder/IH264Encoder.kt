package daniel.avila.x264.encoder


interface IH264Encoder {
    fun init(width: Int, height: Int, fps: Int, bitrate: Int)
    fun yuv420spToH264(data: ByteArray, rotate: Int, h264Frame: (ByteArray) -> Unit)
    fun release()
}