# x264 & libyuv for Android

Preview frame scale & encoder to h264 using native libraries
* [x264]
* [libyuv]
### Usage

```
override fun onPreviewFrame(data: ByteArray, camera: Camera) {
    val rotated = yuvUtils.nV21Rotate(
        data,
        widthPreview,
        heightPreview,
        Key.ROTATE_90,
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

* execute build_x264.sh
This script clone x264 repository and build different ABIs for android

```
# ./build_x264.sh
```

This project is based in: [sszhangpengfei encoder] and [doggycoder AndroidLibyuv]

[sszhangpengfei encoder]: https://github.com/sszhangpengfei/android_x264_encoder
[doggycoder AndroidLibyuv]: https://github.com/doggycoder/AndroidLibyuv
[x264]: https://www.videolan.org/developers/x264.html
[libyuv]: https://chromium.googlesource.com/libyuv/libyuv/
[VLC for Android]: https://play.google.com/store/apps/details?id=org.videolan.vlc&hl=es_CO
