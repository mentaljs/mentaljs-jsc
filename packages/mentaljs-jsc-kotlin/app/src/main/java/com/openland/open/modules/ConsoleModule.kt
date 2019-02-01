package com.openland.open.modules

import android.util.Log
import com.openland.mentaljs.jsc.modules.MentalMethod
import com.openland.mentaljs.jsc.modules.MentalNativeModule

class ConsoleModule : MentalNativeModule("Console") {

    @MentalMethod
    fun log(msg: String) {
        Log.i("MentalJS", msg)
    }

    @MentalMethod
    fun debug(msg: String) {
        Log.d("MentalJS", msg)
    }

    @MentalMethod
    fun error(msg: String) {
        Log.e("MentalJS", msg)
    }

    @MentalMethod
    fun warn(msg: String) {
        Log.w("MentalJS", msg)
    }
}