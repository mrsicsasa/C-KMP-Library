package com.example.cexample

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform