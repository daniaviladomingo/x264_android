# x264 & libyuv for Android

Preview frame scale & encoder to h264 using native libraries
* [x264]
* [libyuv]
### Usage

```
override fun onPreviewFrame(data: ByteArray, camera: Camera) {
    val i420Rotated = yuvUtils.nV21ToI420Rotate(
        data,
        widthPreview,
        heightPreview,
        Key.ROTATE_90,
        true
    )

    val i420RotatedScaled = yuvUtils.nV21Scale(
        i420Rotated,
        heightPreview,
        widthPreview,
        widthOut,
        heightOut,
        Key.SCALE_MODE_LINEAR,
    )
    
    h264Encoder.yuv420spToH264(data) { h264Frame ->
        // process h264Frame
    }
}
```

This example record a video h264 and save in
``` 
storage/emulated/0/Download/sample.h264
```
To reproduce this video you can use [VLC for Android]

### Build x264 lib for Android ABIs (armeabi-v7a, arm64-v8a, x86, x86_64)

* clone x264 repository in ~/jni folder

```
# git clone https://code.videolan.org/videolan/x264.git
```

Go to ~jni/compile_script and execute all script (configure your ndk path and others)

```
# ./arm64-v8a.sh
# ./armeabi-v7a.sh
# ./x86.sh
# ./x86_64.sh
```

This project is based in: [sszhangpengfei encoder] and [doggycoder AndroidLibyuv]

[sszhangpengfei encoder]: https://github.com/sszhangpengfei/android_x264_encoder
[doggycoder AndroidLibyuv]: https://github.com/doggycoder/AndroidLibyuv
[x264]: https://www.videolan.org/developers/x264.html
[libyuv]: https://chromium.googlesource.com/libyuv/libyuv/
[VLC for Android]: https://play.google.com/store/apps/details?id=org.videolan.vlc&hl=es_CO
