package com.goingbacking.goingbacking.InputActivityPackage

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.goingbacking.goingbacking.Adapter.TutorialViewPagerAdapter
import com.goingbacking.goingbacking.MainActivity
import com.goingbacking.goingbacking.TutorialActivityPackage.Tutorial1Fragment
import com.goingbacking.goingbacking.TutorialActivityPackage.Tutorial2Fragment
import com.goingbacking.goingbacking.databinding.ActivityTutorialBinding

class TutorialActivity : AppCompatActivity() {
    private lateinit var tutorialViewPagerAdapter : TutorialViewPagerAdapter

    private val binding:  ActivityTutorialBinding by lazy {
        ActivityTutorialBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initAdapter()

        binding.TutorialButton.setOnClickListener {
            moveMainPage()
        }
    }

    private fun moveMainPage() {
        var intent: Intent? = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun initAdapter() {
        val fragmentList = listOf(Tutorial1Fragment(), Tutorial2Fragment())
        tutorialViewPagerAdapter = TutorialViewPagerAdapter(this)
        tutorialViewPagerAdapter.fragments.addAll(fragmentList)

        binding.TutorialViewPager.adapter = tutorialViewPagerAdapter

    }


}