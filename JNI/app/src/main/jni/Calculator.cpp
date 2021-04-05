//
// Created by SungBo on 2021-04-02.
//
#include <Calculator.h>
// JNIEXPORT <리턴타입> JNICALL Java_<패키지명>_<클래스명>_<함수명>
JNIEXPORT jint JNICALL Java_com_sungbo_jni_MainActivity_getSum

(JNIEnv *env, jobject thiz, jint num1, jint num2) {

return num1 + num2;

}