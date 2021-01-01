#include <jni.h>
#include <cstring>
#include <cstdlib>

extern "C"
JNIEXPORT jstring JNICALL
Java_id_ac_ui_cs_mobileprogramming_adhytia_carifi_profile_ProfileActivity_getNativeString(
        JNIEnv *env, jobject obj, jstring arg) {
    jstring hello  = env->NewStringUTF("Hello ");

    const char *hellox  = env->GetStringUTFChars(hello, nullptr);
    const char *argx  = env->GetStringUTFChars(arg, nullptr);

    char *sall  = static_cast<char *>(malloc(strlen(hellox) + strlen(argx) + 1));
    strcpy(sall, hellox);
    strcat(sall, argx);

    jstring retval = env->NewStringUTF(sall);

    env->ReleaseStringUTFChars(hello, hellox);
    env->ReleaseStringUTFChars(arg, argx);
    free(sall);

    return retval;
}