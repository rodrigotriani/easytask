package com.rtriani.beautytasklist

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform