#include <jni.h>
#include "/Users/sasamrsic/AndroidStudioProjects/Cexample/shared/cpp/hello.h"

extern "C"
JNIEXPORT jstring JNICALL
Java_com_example_cexample_Platform_1androidKt_stringFromJNI(JNIEnv *env, jclass clazz) {
    return env->NewStringUTF(hello_from_c());
}
