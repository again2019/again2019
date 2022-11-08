package com.goingbacking.goingbacking.UI.Login


import com.goingbacking.goingbacking.UI.Base.BaseActivity
import com.goingbacking.goingbacking.databinding.ActivityLoginBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : BaseActivity<ActivityLoginBinding>({
    ActivityLoginBinding.inflate(it)
}) {
    interface onBackPressedListener {
        fun onBackPressed()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val fragmentList = supportFragmentManager.fragments
        for (fragment in fragmentList) {
            if (fragment is onBackPressedListener) {
                (fragment as onBackPressedListener).onBackPressed()
                return
            }
        }
    }

}
