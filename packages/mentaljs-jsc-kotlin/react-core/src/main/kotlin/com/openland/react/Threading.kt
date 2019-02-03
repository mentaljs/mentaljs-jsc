package com.openland.react

interface TimerLoop {
    fun setTimeout(id: Int, delay: Int)
    fun clearTimeout(id: Int)
}