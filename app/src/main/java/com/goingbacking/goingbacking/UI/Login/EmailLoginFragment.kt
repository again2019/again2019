package com.goingbacking.goingbacking.UI.Login

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import com.goingbacking.goingbacking.R
import com.goingbacking.goingbacking.UI.Base.BaseFragment
import com.goingbacking.goingbacking.UI.Input.InputActivity
import com.goingbacking.goingbacking.databinding.FragmentEmailLoginBinding
import com.goingbacking.goingbacking.util.PrefUtil
import com.goingbacking.goingbacking.util.UiState
import com.goingbacking.goingbacking.util.isValidEmail
import com.goingbacking.goingbacking.util.toast
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class EmailLoginFragment : BaseFragment<FragmentEmailLoginBinding>() {
    private val viewModel : LoginViewModel by viewModels()

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentEmailLoginBinding {


        return FragmentEmailLoginBinding.inflate(inflater, container, false)
    }

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
                       findNavController().navigate(R.id.action_emailLoginFragment_to_loginFragment)

                       return true
                   }
               }



                return true
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)

        onClick()
    }




    private fun onClick() = with(binding) {
        loginButton.setOnClickListener {
            emailLogin()
        }
        forgotButton.setOnClickListener {
            findNavController().navigate(R.id.action_emailLoginFragment_to_forgotFragment)
        }
    }

    private fun emailLogin() = with(binding) {
        if (validation()) {
            viewModel.emailLogin(emailEdittext.text.toString(), passwordEdittext.text.toString())
            emailLoginObserver()

        }
    }

    private fun emailLoginObserver() {
        viewModel.emailLogin.observe(viewLifecycleOwner) { state ->
            when (state) {
                is UiState.Success -> {
                    binding.progressCircular.hide()
                    toast(requireActivity(), getString(R.string.login_success))
                    moveInputPage()
                }
                is UiState.Loading -> {
                    binding.progressCircular.isIndeterminate = true
                    binding.progressCircular.show()
                }
                is UiState.Failure -> {
                    binding.progressCircular.hide()
                    toast(requireActivity(), getString(R.string.login_fail))
                }
            }
        }
    }

    // email 로그인 코드
    // 유효한지를 판단하는 기준
    private fun validation(): Boolean = with(binding) {
            var isValid = true

            if (emailEdittext.text.isNullOrEmpty()){
                isValid = false
                toast(requireActivity(), getString(R.string.email_again))
            }else{
                if (!emailEdittext.text.toString().isValidEmail()){
                    isValid = false
                    toast(requireActivity(), getString(R.string.email_invalid))
                }
            }
            if (passwordEdittext.text.isNullOrEmpty()){
                isValid = false
                toast(requireActivity(), getString(R.string.password_again))

            }else{
                if (passwordEdittext.text.toString().length < 8){
                    isValid = false
                    toast(requireActivity(), getString(R.string.password_again))
                }
            }
            return isValid
        }


    private fun moveInputPage() {
        val intent = Intent(requireActivity(), InputActivity::class.java)
        startActivity(intent)
        requireActivity().finishAffinity()
    }
}