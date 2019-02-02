package com.openland.open.modules

import android.util.Log
import com.openland.open.MentalMethod
import com.openland.open.MentalModule
import com.openland.open.MentalNativeModule
import java.util.concurrent.Executors

@MentalModule
class ConsoleModule : MentalNativeModule("Console") {

    private val executor = Executors.newSingleThreadExecutor()

    @MentalMethod
    fun log(msg: String) {
        executor.submit {
            Log.i("MentalJS", msg)
        }
    }

    @MentalMethod
    fun debug(msg: String) {
        executor.submit {
            Log.d("MentalJS", msg)
        }
    }

    @MentalMethod
    fun error(msg: String) {
        executor.submit {
            Log.e("MentalJS", msg)
        }
    }

    @MentalMethod
    fun warn(msg: String) {
        executor.submit {
            Log.w("MentalJS", msg)
        }
    }
}