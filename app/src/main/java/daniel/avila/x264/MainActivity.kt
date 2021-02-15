@file:Suppress("DEPRECATION")

package daniel.avila.x264

import android.Manifest
import android.graphics.ImageFormat.NV21
import android.hardware.Camera
import android.hardware.Camera.PreviewCallback
import android.os.Bundle
import android.os.Handler
import android.view.SurfaceHolder
import android.view.SurfaceView
import androidx.appcompat.app.AppCompatActivity
import daniel.avila.x264.databinding.ActivityMainBinding
import daniel.avila.x264.encoder.H264EncoderImp
import daniel.avila.x264.util.YUVRotateUtil
import daniel.avila.x264encoder.jni.X264Encoder
import daniel.avila.yuvutils.Key
import daniel.avila.yuvutils.YuvUtils
import java.io.BufferedOutputStream
import java.io.File
import java.io.FileOutputStream

class MainActivity : AppCompatActivity(), SurfaceHolder.Callback, PreviewCallback {
    private val requestPermission = 1

    private val widthPreview = 640
    private val heightPreview = 480
    private lateinit var camera: Camera
    private val fps = 15
    private val bitrate = 900000

    private val widthOut = heightPreview / 2
    private val heightOut = widthPreview / 2

    private val cameraRotation = 90

    private lateinit var surfaceHolder: SurfaceHolder
    private lateinit var surfaceView: SurfaceView

    private val h264Encoder = H264EncoderImp(X264Encoder())
    private val yuvUtils = YuvUtils()
    private val yuvRotateUtil = YUVRotateUtil()

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    private lateinit var outputStream: BufferedOutputStream

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        surfaceView = binding.surfaceview
        surfaceHolder = surfaceView.holder
        surfaceHolder.addCallback(this)

        createFile()
    }

    override fun onPreviewFrame(data: ByteArray, camera: Camera) {
        val rotated = yuvUtils.nV21Rotate(
            data,
            widthPreview,
            heightPreview,
            cameraRotation,
            true
        )

        val rotatedNScaled = yuvUtils.nV21Scale(
            rotated,
            heightPreview,
            widthPreview,
            widthOut,
            heightOut,
            Key.SCALE_MODE_LINEAR,
        )

        h264Encoder.nV21ToH264(rotatedNScaled) { h264Frame ->
            outputStream.write(h264Frame, 0, h264Frame.size)
        }
    }

    override fun surfaceCreated(p0: SurfaceHolder) {
        when (cameraRotation) {
            90, 270 -> h264Encoder.init(widthOut, heightOut, fps, bitrate)
            0, 180 -> h264Encoder.init(heightOut, widthOut, fps, bitrate)
            else -> throw IllegalArgumentException("Camera rotation must be 0, 90, 180, 270")
        }
        if (isPermissionGranted(listOf(Manifest.permission.CAMERA))) {
            init()
        } else {
            requestPermission(listOf(Manifest.permission.CAMERA), requestPermission)
        }
    }

    override fun surfaceChanged(p0: SurfaceHolder, p1: Int, p2: Int, p3: Int) {}

    override fun surfaceDestroyed(p0: SurfaceHolder) {}

    override fun onStop() {
        camera.stopPreview()
        Handler().postDelayed({
            camera.run {
                setPreviewDisplay(null)
                release()
            }
            h264Encoder.release()
        }, 200)
        super.onStop()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            requestPermission -> {
                if (grantResults.isPermissionsGranted()) {
                    init()
                } else {
                    finish()
                }
            }
        }
    }

    private fun init() {
        camera = getCamera()
        startCamera(camera)
    }

    private fun startCamera(camera: Camera) {
        val parameters = camera.parameters
        parameters.previewFormat = NV21
        parameters.previewFrameRate = 15
        parameters.setPreviewSize(widthPreview, heightPreview)
        parameters.supportedFocusModes.run {
            when {
                this.contains(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE) -> parameters.focusMode =
                    Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE
                this.contains(Camera.Parameters.FOCUS_MODE_CONTINUOUS_VIDEO) -> parameters.focusMode =
                    Camera.Parameters.FOCUS_MODE_CONTINUOUS_VIDEO
                this.contains(Camera.Parameters.FOCUS_MODE_AUTO) -> parameters.focusMode =
                    Camera.Parameters.FOCUS_MODE_AUTO
            }
        }

        camera.setPreviewCallback(this)
        camera.setDisplayOrientation(cameraRotation)
        camera.parameters = parameters
        camera.setPreviewDisplay(surfaceHolder)
        camera.startPreview()
    }

    private fun rotatePreview(data: ByteArray, rotation: Int): ByteArray =
        when (rotation) {
            0 -> data
            90 -> yuvRotateUtil.rotateYUV420Degree90(data, widthPreview, heightPreview)
            180 -> yuvRotateUtil.rotateYUV420Degree180(data, widthPreview, heightPreview)
            270 -> yuvRotateUtil.rotateYUV420Degree270(data, widthPreview, heightPreview)
            else -> throw IllegalArgumentException("preview rotation must be 0, 90, 180 or 270")

        }

    private fun getCamera(): Camera = Camera.open(0)

    private fun createFile() {
        val path = "storage/emulated/0/Download/sample.h264"
        val file = File(path)
        if (file.exists()) {
            file.delete()
        }
        outputStream = BufferedOutputStream(FileOutputStream(path))
    }
}