# x264 for Android

Preview frame encoder to h264 using native library [x264]

### Usage

```
override fun onPreviewFrame(data: ByteArray, camera: Camera) {
    h264Encoder.yuv420spToH264(data, cameraRotation) { h264Frame ->
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

This project is based in: [sszhangpengfei encoder]

[sszhangpengfei encoder]: https://github.com/sszhangpengfei/android_x264_encoder
[x264]: https://www.videolan.org/developers/x264.html
[VLC for Android]: https://play.google.com/store/apps/details?id=org.videolan.vlc&hl=es_CO
