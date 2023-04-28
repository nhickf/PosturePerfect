package com.example.le_androidapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.le_androidapp.R
import com.example.le_androidapp.databinding.FragmentModeBinding
import com.example.le_androidapp.domain.ModeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ModeFragment : Fragment() {

    private var _binding : FragmentModeBinding? = null

    private val binding get() = _binding!!

    private val modeViewModel : ModeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentModeBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                modeViewModel.modeState.collect{
                    binding.modeChange.isChecked = it.workingMode
                }
            }
        }

        with(binding){
            modeChange.setOnClickListener {
                modeViewModel.updateModes(modeChange.isChecked)
            }

            faqButton.setOnClickListener {
                findNavController().navigate(R.id.actionModeToFaqs)
            }
        }
    }

}