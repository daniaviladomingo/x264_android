package daniel.avila.x264.encoder


interface IH264Encoder {
    fun init(width: Int, height: Int, fps: Int, bitrate: Int)
    fun nV21ToH264(data: ByteArray, h264Frame: (ByteArray) -> Unit)
    fun release()
}