package com.goingbacking.goingbacking.UI.Login


import android.os.Bundle
import androidx.core.content.ContextCompat
import com.goingbacking.goingbacking.R
import com.goingbacking.goingbacking.UI.Base.BaseActivity
import com.goingbacking.goingbacking.databinding.ActivityLoginBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : BaseActivity<ActivityLoginBinding>({
    ActivityLoginBinding.inflate(it)
}) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val window = getWindow()
        window.statusBarColor = ContextCompat.getColor(this, R.color.black)
    }

}
