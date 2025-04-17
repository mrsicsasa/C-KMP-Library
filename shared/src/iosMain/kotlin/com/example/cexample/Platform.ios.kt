package com.example.cexample

import kotlinx.cinterop.*
import hello.hello_from_c

class IOSPlatform: Platform {
    @OptIn(ExperimentalForeignApi::class)
    override val name: String = hello_from_c()?.toKString() ?: "Problem"
}

actual fun getPlatform(): Platform = IOSPlatform()

