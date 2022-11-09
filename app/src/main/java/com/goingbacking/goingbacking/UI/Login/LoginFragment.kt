package com.goingbacking.goingbacking.UI.Login

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat.finishAffinity
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.goingbacking.goingbacking.R
import com.goingbacking.goingbacking.UI.Base.BaseFragment
import com.goingbacking.goingbacking.UI.Input.InputActivity
import com.goingbacking.goingbacking.UI.Main.MainActivity
import com.goingbacking.goingbacking.databinding.FragmentLoginBinding
import com.goingbacking.goingbacking.util.PrefUtil
import com.goingbacking.goingbacking.util.UiState
import com.goingbacking.goingbacking.util.isValidEmail
import com.goingbacking.goingbacking.util.toast
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignIn.getClient
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding>() {
    val viewModel: LoginViewModel by viewModels()
    private lateinit var googleSignInClient: GoogleSignInClient

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentLoginBinding {
        return FragmentLoginBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        onClick()

    }

    // 버튼 이벤트 모음
    private fun onClick() = with(binding) {
        registerButton.setOnClickListener{
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }
        emailLoginButton.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_emailLoginFragment)

        }
        googleloginbtn.setOnClickListener {
            googleLoginObserver()
            toast(requireContext(), FirebaseAuth.getInstance().currentUser?.uid.toString())
        }
    }

    // 구글 로그인 코드
    private fun googleLoginObserver() {
        viewModel.getGSO()
        viewModel.gso.observe(viewLifecycleOwner) {
                state ->
            when (state) {
                is UiState.Success -> {
                    binding.progressCircular.hide()
                    googleSignInClient = getClient(requireActivity(), state.data)
                    launcher.launch(googleSignInClient.signInIntent)
                }
                is UiState.Failure -> {
                    binding.progressCircular.hide()
                    toast(requireActivity(), getString(R.string.login_fail))
                }
                is UiState.Loading -> {
                    binding.progressCircular.show()
                }

            }
        }
    }


    private val launcher : ActivityResultLauncher<Intent> = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
        if(result.resultCode == Activity.RESULT_OK) {
            val signInTask = GoogleSignIn.getSignedInAccountFromIntent(result.data)

            try {
                val account = signInTask.getResult(ApiException::class.java)
                onGoogleSignInAccount(account)
            } catch (e: ApiException) {
                toast(requireActivity(), getString(R.string.login_fail))
            }
        }
    }


    private fun onGoogleSignInAccount(account: GoogleSignInAccount?) {
        if (account != null) {
            viewModel.signInWithCredential(account.idToken!!)
            viewModel.loginCredential.observe(viewLifecycleOwner) { state ->
                when (state) {
                    is UiState.Success -> {
                        binding.progressCircular.hide()
                        toast(requireActivity(), getString(R.string.login_success))
                        moveInputPage()
                    }
                    is UiState.Loading -> {
                        binding.progressCircular.show()
                    }
                    is UiState.Failure -> {
                        binding.progressCircular.hide()
                        toast(requireActivity(), getString(R.string.login_fail))
                    }
                }
            }
        }
    }

    // email 로그인 코드


    // 만약 로그인을 미리 해놨었다면 바로 mainActivity로 넘어가는 코드
//    override fun onStart() {
//        super.onStart()
//
//        if (!(PrefUtil.getCurrentUid(requireContext()) == null)) {
//            if (PrefUtil.firebaseUid().equals(PrefUtil.getCurrentUid(requireContext()))) {
//                viewModel.getCurrentSession()
//                viewModel.currentSession.observe(viewLifecycleOwner) { state ->
//                    when (state) {
//                        is UiState.Success -> {
//                            binding.progressCircular.hide()
//                            moveMainPage()
//                        }
//                        is UiState.Loading -> {
//                            binding.progressCircular.show()
//                        }
//                        is UiState.Failure -> {
//                            binding.progressCircular.hide()
//                            toast(requireContext(), getString(R.string.auto_login_fail))
//                        }
//                    }
//                }
//            }
//        }
//    }

    private fun moveInputPage() {
        val intent = Intent(requireActivity(), InputActivity::class.java)
        startActivity(intent)
    }


    private fun moveMainPage() {
        val intent = Intent(requireActivity(), MainActivity::class.java)
        startActivity(intent)
        finishAffinity(requireActivity())
    }
}



