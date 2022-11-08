package com.goingbacking.goingbacking.UI.Input

import android.os.Bundle
import android.view.*
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.goingbacking.goingbacking.R
import com.goingbacking.goingbacking.UI.Base.BaseFragment
import com.goingbacking.goingbacking.databinding.FragmentSecondInputBinding
import com.goingbacking.goingbacking.util.UiState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SecondInputFragment : BaseFragment<FragmentSecondInputBinding>() {
    val viewModel: InputViewModel by viewModels()

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentSecondInputBinding {
        return FragmentSecondInputBinding.inflate(layoutInflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val args : SecondInputFragmentArgs by navArgs()
        binding.nickname.text = args.nickname
        binding.progressBar.setMinAndMaxProgress(0.05f, 0.355f)
        binding.progressBar.playAnimation()

        onClick()

        val menuHost: MenuHost = requireActivity()

        (activity as AppCompatActivity).setSupportActionBar(binding.toolbar)
        (activity as AppCompatActivity).supportActionBar?.setDisplayShowTitleEnabled(false)
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)

        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                when(menuItem.itemId) {
                    android.R.id.home -> {
                        findNavController().navigate(R.id.action_secondInputFragment_to_firstInputFragment)
                        return true
                    }
                }
                return true
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)

    }


    private fun onClick() = with(binding) {

        loginbtn.setOnClickListener {
            viewModel.updateSecondInput(typeEdittext.toString())
            findNavController().navigate(R.id.action_secondInputFragment_to_thirdInputFragment)
        }
    }


}