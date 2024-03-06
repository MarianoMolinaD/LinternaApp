package com.portafoliowebmariano.flashlight.model

import android.app.Activity

class BrightnessControl(private val activity: Activity) {

    fun setBrightness(brightness: Float) {
        val layoutParams = activity.window.attributes
        layoutParams.screenBrightness = brightness
        activity.window.attributes = layoutParams
    }
}
