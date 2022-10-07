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
import kotlinx.android.synthetic.main.fragment_first_main.*
import kotlinx.android.synthetic.main.fragment_first_main.view.*
import java.util.*
import java.util.concurrent.TimeUnit

class FirstMainFragment : Fragment() {




    enum class TimerState {
        Stopped, Paused, Running
    }



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_first_main, container, false)

        val paperAdapter = PagerAdapter(childFragmentManager)
        view.view_pager.adapter = paperAdapter
        view.indicator.setViewPager(view.view_pager)

        var calendar = Calendar.getInstance()
        calendar.timeInMillis = System.currentTimeMillis()










//        view.tmp.setOnClickListener {
//            var sharedPreferences = requireActivity().getSharedPreferences("time",
//                AppCompatActivity.MODE_PRIVATE
//            )
//            var exp1 :Int? = sharedPreferences.getInt("TodayTime", 0)
//            var exp2 : String? = sharedPreferences.getString("TodayStrTime", "")
//            var exp2_split = exp2!!.split(',')
//
//
//            Log.d("AAAAAAAA", exp1.toString())
//            Log.d("AAAAAAAA", exp2!!)
//            Log.d("AAAAAAAA", exp2_split!!.toString())
//        }




        view.tmpTimeButton.setOnClickListener {
            val intent = Intent(requireContext(), TmpTimeActivity::class.java)
            startActivity(intent)
        }
        return view
    }








    override fun onPause() {
        super.onPause()


    }
}