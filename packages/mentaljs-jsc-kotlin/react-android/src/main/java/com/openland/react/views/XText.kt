package com.openland.react.views

import android.graphics.Color
import com.openland.react.MentalProps
import com.openland.react.ViewCallback

@MentalProps
class XTextProps {

    var fontSize: Float? = null
    var lineHeight: Float? = null
    var fontWeight: String? = null
    var color: Int = Color.BLACK
    var numberOfLines: Int? = null
    var underline: Boolean = false
    // var textAlign: String? = null

    var onPress: ViewCallback? = null
}