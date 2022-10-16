package com.goingbacking.goingbacking

import android.Manifest
import android.app.AlarmManager
import android.app.AlertDialog
import android.app.PendingIntent
import android.app.PendingIntent.FLAG_MUTABLE
import android.app.PendingIntent.FLAG_UPDATE_CURRENT
import android.content.*
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.work.*
import com.goingbacking.goingbacking.MainActivityPackage.*
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {
    var exp1 :Int? = null
    var exp2 : String? = null
    var sharedPreferences :SharedPreferences? = null

        override fun onNavigationItemSelected(p0: MenuItem): Boolean {
        when(p0.itemId){
            R.id.action_home ->{
                var firstMainFragment = FirstMainFragment()
                supportFragmentManager.beginTransaction().replace(R.id.main_content,firstMainFragment).commit()
                return true
            }
            R.id.action_stat ->{
                var secondMainFragment = SecondMainFragment()
                supportFragmentManager.beginTransaction().replace(R.id.main_content,secondMainFragment).commit()
                return true
            }
            R.id.action_plan ->{
                var thirdMainFragment = ThirdMainFragment()
                supportFragmentManager.beginTransaction().replace(R.id.main_content,thirdMainFragment).commit()

                return true
            }
            R.id.action_shop ->{
                var forthMainFragment = ForthMainFragment()
                supportFragmentManager.beginTransaction().replace(R.id.main_content,forthMainFragment).commit()


                return true
            }
            R.id.action_mypage ->{
                var fifthMainFragment = FifthMainFragment()

                supportFragmentManager.beginTransaction().replace(R.id.main_content,fifthMainFragment).commit()
                return true
            }
        }
        return false
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        bottom_navigation.setOnNavigationItemSelectedListener(this)
        bottom_navigation.selectedItemId = R.id.action_home

        //notification 구현 완료!! 합치기만 함녀돼
        //앞에 설정한 값으로 보여주기
        // 없으면 디폴트 값은 현재 시간
//        var calendar = Calendar.getInstance()
//        calendar.timeInMillis = System.currentTimeMillis()
//        calendar.set(Calendar.HOUR_OF_DAY, 17)
//        calendar.set(Calendar.MINUTE, 57)
//        calendar.set(Calendar.SECOND, 0)
//        calendar.set(Calendar.MILLISECOND, 0)
//
//        if (calendar.before(Calendar.getInstance())) {
//            calendar.add(Calendar.DATE, 1)
//        }
//        var dailyNotify = true
//
//        var pm : PackageManager = this.packageManager
//        var receiver = ComponentName(this, DeviceBootReceiver::class.java)
//        var alarmIntent  = Intent(this, CountReceiver::class.java)
//        alarmIntent.putExtra("id", 3000)
//        alarmIntent.putExtra("type", "channel")
//        alarmIntent.putExtra("repeat", true)
//        alarmIntent.putExtra("interval", 60)
//        var pendingIntent : PendingIntent = PendingIntent.getBroadcast(this, 3000, alarmIntent, FLAG_MUTABLE)
//        var alarmManager :AlarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
//        if(dailyNotify) {
//            if(alarmManager != null) {
//
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                    alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pendingIntent)
//
//                }
//            }
//            // 뷩후 실행되는 리시버
//            pm.setComponentEnabledSetting(receiver, PackageManager.COMPONENT_ENABLED_STATE_ENABLED,PackageManager.DONT_KILL_APP)
//        }







    }





}


