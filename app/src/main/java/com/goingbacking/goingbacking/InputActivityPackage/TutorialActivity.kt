package com.goingbacking.goingbacking.InputActivityPackage

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.goingbacking.goingbacking.Adapter.TutorialViewPagerAdapter
import com.goingbacking.goingbacking.MainActivity
import com.goingbacking.goingbacking.R
import com.goingbacking.goingbacking.TutorialActivityPackage.Tutorial1Fragment
import com.goingbacking.goingbacking.TutorialActivityPackage.Tutorial2Fragment
import com.goingbacking.goingbacking.databinding.ActivityTutorialBinding
import kotlinx.android.synthetic.main.activity_tutorial.*

class TutorialActivity : AppCompatActivity() {
    private lateinit var tutorialViewPagerAdapter : TutorialViewPagerAdapter
    private lateinit var binding : ActivityTutorialBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityTutorialBinding.inflate(layoutInflater)
        initAdapter()
        setContentView(binding.root)


        TutorialButton.setOnClickListener {
            var intent: Intent? = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun initAdapter() {
        val fragmentList = listOf(Tutorial1Fragment(), Tutorial2Fragment())
        tutorialViewPagerAdapter = TutorialViewPagerAdapter(this)
        tutorialViewPagerAdapter.fragments.addAll(fragmentList)

        binding.TutorialViewPager.adapter = tutorialViewPagerAdapter

    }
}