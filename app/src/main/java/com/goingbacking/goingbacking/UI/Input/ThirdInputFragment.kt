package com.goingbacking.goingbacking.UI.Input

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.children
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.goingbacking.goingbacking.R
import com.goingbacking.goingbacking.UI.Base.BaseFragment
import com.goingbacking.goingbacking.UI.Tutorial.TutorialActivity
import com.goingbacking.goingbacking.ViewModel.InputViewModel
import com.goingbacking.goingbacking.databinding.FragmentThirdInputBinding
import com.goingbacking.goingbacking.util.UiState
import com.google.android.material.chip.Chip
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ThirdInputFragment : BaseFragment<FragmentThirdInputBinding>() {
    val viewModel: InputViewModel by viewModels()

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentThirdInputBinding {
        return FragmentThirdInputBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ThirdInputObserver()
        onClick()
    }


    private fun ThirdInputObserver() {
        viewModel.updateThirdInput.observe(viewLifecycleOwner) {
                state ->
            when(state) {
                is UiState.Failure -> {
                    Toast.makeText(requireActivity(), "fail", Toast.LENGTH_SHORT).show()
                }
                is UiState.Success -> {
                    Toast.makeText(requireActivity(), "success", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun onClick() = with(binding) {
        ThirdInputButton1.setOnClickListener {
            findNavController().navigate(R.id.action_thirdInputFragment_to_secondInputFragment)
        }
        ThirdInputButton2.setOnClickListener {
            val selected = binding.chipGroup.children.toList()
                .filter{ (it as Chip).isChecked}.joinToString(",")
                {(it as Chip).text}

            viewModel.updateThirdInput(selected)
            val intent = Intent(activity, TutorialActivity::class.java)
            startActivity(intent)

        findNavController().navigate(R.id.action_thirdInputFragment_to_tutorialActivity)
        }

    }




}