package com.portafoliowebmariano.flashlight.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class FlashlightViewModel : ViewModel() {

    val flahsligthOn = MutableLiveData(false)
    val levelBrightness = MutableLiveData<Int>()

    fun togglerFlashlight() {
        flahsligthOn.postValue(flahsligthOn.value?.not())
    }
}
