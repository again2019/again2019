package com.goingbacking.goingbacking.UI.Main.Fifth

import android.os.Bundle
import androidx.navigation.navArgs
import com.goingbacking.goingbacking.UI.Base.BaseActivity
import com.goingbacking.goingbacking.databinding.ActivityChangeInfoBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChangeInfoActivity : BaseActivity<ActivityChangeInfoBinding>({
    ActivityChangeInfoBinding.inflate(it)
}) {
    private val args :ChangeInfoActivityArgs by navArgs<ChangeInfoActivityArgs>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)




    }
}