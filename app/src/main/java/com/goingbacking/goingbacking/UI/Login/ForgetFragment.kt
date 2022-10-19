package com.goingbacking.goingbacking.UI.Login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.goingbacking.goingbacking.R
import com.goingbacking.goingbacking.UI.Base.BaseFragment
import com.goingbacking.goingbacking.ViewModel.LoginViewModel
import com.goingbacking.goingbacking.databinding.FragmentForgetBinding
import com.goingbacking.goingbacking.util.UiState
import com.goingbacking.goingbacking.util.isValidEmail
import dagger.hilt.android.AndroidEntryPoint

<<<<<<< Updated upstream
// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ForgetFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ForgetFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }
=======
@AndroidEntryPoint
class ForgetFragment : BaseFragment<FragmentForgetBinding>() {
    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentForgetBinding {
        return FragmentForgetBinding.inflate(inflater, container, false)
    }

    val viewModel :LoginViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        forgotPasswordObserver()
        binding.forgotPassBtn.setOnClickListener {
            if(validation()) {
                viewModel.emailForgetPassword(binding.emailEt.text.toString())
                Toast.makeText(requireActivity(), "이메일 링크가 발송되었습니다.", Toast.LENGTH_SHORT).show()
                findNavController().navigate(R.id.action_forgotFragment_to_loginFragment)
            }
        }
    }

    private fun forgotPasswordObserver() = with(binding) {
        viewModel.forgotPassword.observe(viewLifecycleOwner) {
            state ->
            when(state) {
                is UiState.Loading -> {

                }
                is UiState.Failure -> {
                }
                is UiState.Success -> {
>>>>>>> Stashed changes

                }
            }
        }
    }

<<<<<<< Updated upstream
    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ForgetFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ForgetFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
=======
    fun validation(): Boolean  = with(binding) {
        var isValid = true

        if (emailEt.text.isNullOrEmpty()){
            isValid = false
        }else{
            if (!emailEt.text.toString().isValidEmail()){
                isValid = false
            }
        }

        return isValid
    }


>>>>>>> Stashed changes
}