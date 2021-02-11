package daniel.avila.domain

import daniel.avila.domain.model.PreviewImage

interface IH264Encoder {
    fun init(width: Int, height: Int, fps: Int, bitrate: Int)
    fun yuv420spToH264(previewImage: PreviewImage, h264Frame: (ByteArray) -> Unit)
    fun release()
}