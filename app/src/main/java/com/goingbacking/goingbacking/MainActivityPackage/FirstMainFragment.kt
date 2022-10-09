package com.goingbacking.goingbacking.MainActivityPackage

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.CountDownTimer
import android.preference.PreferenceManager
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.goingbacking.goingbacking.*
import com.goingbacking.goingbacking.Adapter.PagerAdapter
import com.goingbacking.goingbacking.databinding.FragmentFirstMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_first_main.*
import kotlinx.android.synthetic.main.fragment_first_main.view.*
import java.util.*
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
class FirstMainFragment : Fragment() {

    //임시 코드
    enum class TimerState {
        start, stop, pause
    }
    //임시 코드

    lateinit var binding :FragmentFirstMainBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFirstMainBinding.inflate(layoutInflater)

        val paperAdapter = PagerAdapter(childFragmentManager)
        binding.viewPager.adapter = paperAdapter
        binding.indicator.setViewPager(binding.viewPager)

        binding.tmpTimeButton.setOnClickListener {
            moveTmpActivity()
        }

        if (this:: binding.isInitialized) {
            return binding.root
        } else {
            binding = FragmentFirstMainBinding.inflate(layoutInflater)
            return binding.root
        }


    }

    private fun moveTmpActivity() {
        val intent = Intent(requireContext(), TmpTimeActivity::class.java)
        startActivity(intent)
    }

}