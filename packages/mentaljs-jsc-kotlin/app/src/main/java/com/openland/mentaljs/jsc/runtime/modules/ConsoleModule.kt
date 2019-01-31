package com.openland.mentaljs.jsc.runtime.modules

import android.util.Log
import com.openland.mentaljs.jsc.runtime.MentalMethod
import com.openland.mentaljs.jsc.runtime.MentalNativeModule

class ConsoleModule : MentalNativeModule("Console") {

    @MentalMethod
    fun log(msg: String) {
        Log.i("MentalJS", msg)
    }

    @MentalMethod
    fun debug(msg: String) {
        Log.d("MentalJS", msg)
    }
}