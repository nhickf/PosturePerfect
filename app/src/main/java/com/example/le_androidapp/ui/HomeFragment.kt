package com.example.le_androidapp.ui

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Vibrator
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.le_androidapp.R
import com.example.le_androidapp.SettingsFragment
import com.example.le_androidapp.data.ConnectionState.Uninitialized
import com.example.le_androidapp.databinding.FragmentHomeBinding
import com.example.le_androidapp.domain.DeviceViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    private val binding get() = _binding!!

    private val deviceViewModel: DeviceViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewLifecycleOwner.lifecycleScope.launch {
            deviceViewModel.deviceReceiveManager.data.collect{
                deviceViewModel.deviceManagerChanges(it)
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            deviceViewModel.deviceState.collect(::handleStateChanges)
        }

        viewLifecycleOwner.lifecycleScope.launch {
            deviceViewModel.deviceEvent.collect(::handleEventChanges)
        }

        binding.settingsButton.setOnClickListener {
            val fr = requireFragmentManager().beginTransaction()
            fr.replace(R.id.container, SettingsFragment())
            fr.commit()
        }

    }

    private fun handleEventChanges(nothing : Unit) {
        //do vibrate when posture increment
        val vibrator = requireActivity().getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        vibrator.vibrate(1000L)
    }

    private fun handleStateChanges(deviceViewModelState: DeviceViewModel.DeviceViewModelState){
        Log.e("handleStateChanges","$deviceViewModelState")
        deviceViewModelState.readByDevice?.let {
                binding.badPostureCount.text =it.badPostureCount.toString()
                val roll = it.calibratedRoll
                val flex = it.calibratedFlexSensor

                binding.bendyGuy.setImageResource(
                    when {
                        roll >= 0 -> R.drawable.body90
                        roll >= -2 -> R.drawable.body85
                        roll >= -4 -> R.drawable.body80
                        roll >= -6 -> R.drawable.body75
                        roll >= -8 -> R.drawable.body70
                        roll >= -9 -> R.drawable.body65
                        roll >= -10 -> R.drawable.body60
                        roll >= -12 -> R.drawable.body55
                        roll >= -14 -> R.drawable.body50
                        roll >= -16 -> R.drawable.body45
                        else -> R.drawable.body40
                    }
                )

                val modeSelected = 2
                val minRoll = when (modeSelected){
                    2 -> -8
                    else -> -4
                }
                val maxRoll = -14
                val maxFlex = -425
        }
        binding.bleScanButton.setOnClickListener {
            deviceViewModel.dummyIncrement()
            if (deviceViewModelState.connectionState is Uninitialized) {
                deviceViewModel.initializeConnection()
                Log.e("HomeFragment", "InitializeConnection")
                Toast.makeText(activity, "Initializing connection", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(activity, "Recalibrating", Toast.LENGTH_SHORT).show()
                deviceViewModel.disconnect()
                Handler().postDelayed({ deviceViewModel.initializeConnection() }, 3000)
            }
        }
    }
}