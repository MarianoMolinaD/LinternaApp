package com.portafoliowebmariano.flashlight.view.fragments

import android.os.Bundle
import android.util.Log
import android.view.GestureDetector
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.portafoliowebmariano.flashlight.R
import com.portafoliowebmariano.flashlight.databinding.FragmentLampBinding
import com.portafoliowebmariano.flashlight.model.BrightnessControl

class LampFragment : Fragment(), GestureDetector.OnGestureListener, View.OnTouchListener {
    private lateinit var binding: FragmentLampBinding
    private lateinit var gestureDetector: GestureDetector
    private lateinit var brightnessControl: BrightnessControl

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLampBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        brightnessControl = BrightnessControl(requireActivity())
        // Inicializar GestureDetector y establecer el fragmento como oyente de toques
        gestureDetector = GestureDetector(requireContext(), this)
        view.setOnTouchListener(this)
        initUI()
        controller()
    }

    private fun initUI() {
        toast("Deslice hacia arriba o hacia abajo para ajustar el brillo de la pantalla")
    }

    private fun controller() {
        binding.ivFlashligh.setOnClickListener {
            findNavController().navigate(R.id.flashlightFragment)
        }
    }

    override fun onDown(e: MotionEvent): Boolean {
        return true
    }

    override fun onShowPress(e: MotionEvent) {

    }

    override fun onSingleTapUp(e: MotionEvent): Boolean {
        return true
    }

    override fun onScroll(
        e1: MotionEvent?,
        e2: MotionEvent,
        distanceX: Float,
        distanceY: Float
    ): Boolean {
        val porcentaje: Int = binding.tvPorcentaje.text.toString().toInt()

        if (distanceY > 0) {
            var currentPorcentaje = porcentaje + 1
            if (currentPorcentaje in 1..100) {
                binding.tvPorcentaje.text = "$currentPorcentaje"
                setScreenBrightness(currentPorcentaje.toFloat() / 100)

            }


        } else if (distanceY < 0) {
            var currentPorcentaje = porcentaje - 1
            if (currentPorcentaje in 1..100) {
                binding.tvPorcentaje.text = "$currentPorcentaje"
                setScreenBrightness(currentPorcentaje.toFloat() / 100)

            }
        }
        return true
    }

    override fun onLongPress(e: MotionEvent) {

    }

    override fun onFling(
        e1: MotionEvent?,
        e2: MotionEvent,
        velocityX: Float,
        velocityY: Float
    ): Boolean {
        return true
    }

    override fun onTouch(v: View?, event: MotionEvent?): Boolean {
        return gestureDetector.onTouchEvent(event!!)
    }

    private fun toast(text: String) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
    }

    private fun setScreenBrightness(brightness: Float) {
        brightnessControl.setBrightness(brightness)
    }
}