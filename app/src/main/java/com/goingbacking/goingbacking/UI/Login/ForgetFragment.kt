package com.goingbacking.goingbacking.UI.Login

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import com.goingbacking.goingbacking.R
import com.goingbacking.goingbacking.UI.Base.BaseFragment
import com.goingbacking.goingbacking.databinding.FragmentForgetBinding
import com.goingbacking.goingbacking.util.UiState
import com.goingbacking.goingbacking.util.isValidEmail
import com.goingbacking.goingbacking.util.toast
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ForgetFragment : BaseFragment<FragmentForgetBinding>() {

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentForgetBinding {
        return FragmentForgetBinding.inflate(inflater, container, false)
    }

    val viewModel : LoginViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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
                        findNavController().navigate(R.id.action_forgotFragment_to_emailLoginFragment)

                        return true
                    }
                }
                return true
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)

        binding.forgotPassBtn.setOnClickListener {
            if(validation()) {
                observer()
            }
        }
    }

    private fun observer()  {
        viewModel.emailForgetPassword(binding.emailEdittext.text.toString())
        viewModel.forgotPassword.observe(viewLifecycleOwner) {
            state ->
            when(state) {
                is UiState.Loading -> {
                    binding.progressCircular.show()
                }
                is UiState.Failure -> {
                    binding.progressCircular.hide()
                    toast(requireContext(), getString(R.string.email_link_send_fail))
                }
                is UiState.Success -> {
                    binding.progressCircular.hide()
                    toast(requireContext(), getString(R.string.email_link_send_success))
                    findNavController().navigate(R.id.action_forgotFragment_to_emailLoginFragment)
                }
            }
        }
    }

    fun validation(): Boolean = with(binding) {
        var isValid = true

        if (emailEdittext.text.isNullOrEmpty()){
            isValid = false
            toast(requireContext(), getString(R.string.email_again))

        } else{
            if (!emailEdittext.text.toString().isValidEmail()){
                isValid = false
                toast(requireContext(), getString(R.string.email_again))
            }
        }

        return isValid
    }


}