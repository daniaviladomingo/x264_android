package daniel.avila.yuvutils

class YuvUtils {
    fun nV21Rotate(
        data: ByteArray,
        width: Int,
        height: Int,
        rotation: Int,
        swapUV: Boolean
    ): ByteArray {
        val out = ByteArray(width * height * 3 / 2)
        NV21ToI420Rotate(data, width, height, out, rotation, swapUV)
        return out
    }

    fun nV21Scale(
        data: ByteArray,
        width: Int,
        height: Int,
        dstWidth: Int,
        dstHeight: Int,
        type: Int
    ): ByteArray {
        val out = ByteArray((dstWidth * dstHeight) * 3 / 2)
        NV21Scale(data, width, height, out, dstWidth, dstHeight, type)
        return out
    }

    external fun RgbaToI420(
        type: Int, rgba: ByteArray?, stride: Int, yuv: ByteArray?,
        y_stride: Int, u_stride: Int, v_stride: Int, width: Int, height: Int
    ): Int

    external fun RgbaToI420(
        type: Int,
        rgba: ByteArray?,
        yuv: ByteArray?,
        width: Int,
        height: Int
    ): Int


    external fun I420ToRgba(
        type: Int, yuv: ByteArray?, y_stride: Int, u_stride: Int, v_stride: Int,
        rgba: ByteArray?, stride: Int, width: Int, height: Int
    ): Int

    external fun I420ToRgba(
        type: Int,
        yuv: ByteArray?,
        rgba: ByteArray?,
        width: Int,
        height: Int
    ): Int

    external fun I420ToNV21(
        yuv420p: ByteArray?,
        yuv420sp: ByteArray?,
        width: Int,
        height: Int,
        swapUV: Boolean
    )

    external fun NV21ToI420(
        yuv420sp: ByteArray?,
        yuv420p: ByteArray?,
        width: Int,
        height: Int,
        swapUV: Boolean
    )

    external fun NV21Scale(
        src_data: ByteArray?, width: Int, height: Int, out: ByteArray?,
        dst_width: Int, dst_height: Int, type: Int
    )

    external fun I420Scale(
        src_data: ByteArray?, width: Int, height: Int, out: ByteArray?,
        dst_width: Int, dst_height: Int, type: Int, swapUV: Boolean
    )

    external fun RgbaScale(
        src_data: ByteArray?, width: Int, height: Int, out: ByteArray?,
        dst_width: Int, dst_height: Int, type: Int
    )

    external fun NV21ToI420Rotate(
        src: ByteArray?,
        width: Int,
        height: Int,
        dst: ByteArray?,
        de: Int,
        swapUV: Boolean
    )


    companion object {
        init {
            System.loadLibrary("yuv_utils")
        }
    }
}