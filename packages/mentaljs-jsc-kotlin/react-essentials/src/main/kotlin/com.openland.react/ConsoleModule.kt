package com.openland.react

interface ConsoleHandler {
    fun log(msg: String)
    fun debug(msg: String)
    fun error(msg: String)
    fun warn(msg: String)
}

@MentalModule
class ConsoleModule(private val handler: ConsoleHandler) : NativeModule("Console") {


    @MentalMethod
    fun log(msg: String) {
        handler.log(msg)
    }

    @MentalMethod
    fun debug(msg: String) {
        handler.debug(msg)
    }

    @MentalMethod
    fun error(msg: String) {
        handler.error(msg)
    }

    @MentalMethod
    fun warn(msg: String) {
        handler.warn(msg)
    }
}