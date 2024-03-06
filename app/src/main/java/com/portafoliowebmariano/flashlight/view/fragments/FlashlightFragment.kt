package com.portafoliowebmariano.flashlight.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.portafoliowebmariano.flashlight.R
import com.portafoliowebmariano.flashlight.databinding.FragmentFlashlightBinding
import com.portafoliowebmariano.flashlight.model.FlashlightManager
import com.portafoliowebmariano.flashlight.viewmodel.FlashlightViewModel


class FlashlightFragment : Fragment() {
    private lateinit var binding: FragmentFlashlightBinding
    private lateinit var viewModel : FlashlightViewModel
    private lateinit var flashlightManager: FlashlightManager


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFlashlightBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(FlashlightViewModel::class.java)
        flashlightManager = FlashlightManager(requireContext())

        controller()
        observer()
    }

    private fun observer() {

        // Observa el estado del flash para actualizar la interfaz
        viewModel.flahsligthOn.observe(viewLifecycleOwner) { isFlashlightOn ->
            if (isFlashlightOn) {
                flashlightManager.turnOnFlash()
                binding.ivFlashlightOn.isVisible = isFlashlightOn
                binding.ivButtonOn.contentDescription = "Apagar Linterna"
            } else {
                //
                flashlightManager.turnOffFlash()
                binding.ivFlashlightOn.isVisible = isFlashlightOn
                binding.ivButtonOn.contentDescription = "Encender Linterna"
            }
        }
    }

    private fun controller() {
        //Click Cambiar a Lampara
        binding.ivLamp.setOnClickListener{
            findNavController().navigate(R.id.lampFragment)
        }

        //Click encender Linterna
        binding.ivButtonOn.setOnClickListener {
            viewModel.togglerFlashlight()

        }
    }


}