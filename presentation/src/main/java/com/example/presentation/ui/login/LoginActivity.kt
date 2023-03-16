package com.example.presentation.ui.login


import android.os.Bundle
import androidx.core.content.ContextCompat
import com.example.presentation.R
import com.example.presentation.databinding.ActivityLoginBinding
import com.example.presentation.ui.base.BaseActivity
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
