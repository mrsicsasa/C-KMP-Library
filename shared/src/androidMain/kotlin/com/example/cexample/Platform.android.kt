package com.example.cexample

class AndroidPlatform : Platform {
    init {
        System.loadLibrary("native-lib")
    }
    override val name: String = stringFromJNI()
}

actual fun getPlatform(): Platform = AndroidPlatform()
external fun stringFromJNI(): String
