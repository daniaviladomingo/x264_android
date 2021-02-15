#include <jni.h>

#ifndef NELEM
# define NELEM(x) ((int) (sizeof(x) / sizeof((x)[0])))
#endif

#define YUVBUFFER_IN_JAVA_OBJ_NAME   "mVideoBuffer"

static const char* const kClassPathName = "daniel/avila/x264encoder/jni/X264Encoder";

extern "C" 
{
	static void Java_daniel_avila_x264encoder_jni_init(JNIEnv *env, jobject jobj,jint width, jint height, jint fps, jint bite);

	static void Java_daniel_avila_x264encoder_jni_encoderH264(JNIEnv *env, jobject jobj,jint lenght, jlong time);

	static void Java_daniel_avila_x264encoder_jni_release(JNIEnv *env, jobject jobj);
}