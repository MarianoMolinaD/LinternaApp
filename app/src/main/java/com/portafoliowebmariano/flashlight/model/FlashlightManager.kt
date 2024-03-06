package com.portafoliowebmariano.flashlight.model

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.hardware.camera2.CameraAccessException
import android.hardware.camera2.CameraCharacteristics
import android.hardware.camera2.CameraManager
import android.os.Build

class FlashlightManager (private val context: Context){
    private val cameraManager: CameraManager =
        context.getSystemService(Context.CAMERA_SERVICE) as CameraManager

    // Verifica si el dispositivo tiene flash
    fun hasFlash(): Boolean {
        return context.packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH)
    }

    // Enciende el flash
    @SuppressLint("MissingPermission")
    fun turnOnFlash() {
        if (hasFlash()) {
            try {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    val cameraId = getCameraIdWithFlash()
                    cameraManager.setTorchMode(cameraId, true)
                }
            } catch (e: CameraAccessException) {
                e.printStackTrace()
            }
        }
    }

    // Apaga el flash
    @SuppressLint("MissingPermission")
    fun turnOffFlash() {
        if (hasFlash()) {
            try {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    val cameraId = getCameraIdWithFlash()
                    cameraManager.setTorchMode(cameraId, false)
                }
            } catch (e: CameraAccessException) {
                e.printStackTrace()
            }
        }
    }

    // Obtiene el ID de la cámara con flash
    @SuppressLint("MissingPermission")
    private fun getCameraIdWithFlash(): String {
        val cameraIds = cameraManager.cameraIdList
        for (cameraId in cameraIds) {
            val characteristics = cameraManager.getCameraCharacteristics(cameraId)
            val flashAvailable = characteristics.get(CameraCharacteristics.FLASH_INFO_AVAILABLE)
            val lensFacing = characteristics.get(CameraCharacteristics.LENS_FACING)

            if (flashAvailable == true && lensFacing == CameraCharacteristics.LENS_FACING_BACK) {
                return cameraId
            }
        }
        // Si no se encuentra ninguna cámara trasera con flash, se devuelve la primera cámara
        return cameraIds[0]
    }
}