package com.goingbacking.goingbacking.UI.Main.Forth

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.goingbacking.goingbacking.UI.Base.BaseActivity
import com.goingbacking.goingbacking.bottomsheet.CheerBottomSheet
import com.goingbacking.goingbacking.databinding.ActivityRankBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RankActivity : BaseActivity<ActivityRankBinding>({
   ActivityRankBinding.inflate(it)
}) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val destinationUid = intent.getStringExtra("destinationUid")!!
        onClick(destinationUid)

    }

    private fun onClick(destinationUid :String) = with(binding) {
        cheer.setOnClickListener {
            val bottom = CheerBottomSheet()
            val bundle = Bundle()
            bundle.putString("destinationUid", destinationUid)
            bottom.arguments = bundle
            bottom.show(supportFragmentManager, bottom.tag)
        }
    }

}