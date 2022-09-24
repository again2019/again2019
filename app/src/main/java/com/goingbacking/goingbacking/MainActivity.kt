package com.goingbacking.goingbacking

import android.Manifest
import android.app.AlarmManager
import android.app.AlertDialog
import android.app.PendingIntent
import android.app.PendingIntent.FLAG_MUTABLE
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.core.content.ContextCompat
import com.goingbacking.goingbacking.MainActivityPackage.*
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {
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
                val builder : AlertDialog.Builder? = AlertDialog.Builder(this)
                builder?.setTitle("안내")?.setMessage("서비스 준비중입니다.")
                builder?.show()
//                var forthMainFragment = ForthMainFragment()
//                supportFragmentManager.beginTransaction().replace(R.id.main_content,forthMainFragment).commit()


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

        //매일 0시가 되면 데이터 베이스를 초기화하는 코드
        var sharedPreferences1 :SharedPreferences? = getSharedPreferences("daily", MODE_PRIVATE)
        var milis :Long? = sharedPreferences1?.getLong("nextNotifyTime", Calendar.getInstance().timeInMillis)

        var c = Calendar.getInstance()
        c.set(Calendar.HOUR_OF_DAY, 14) //시
        c.set(Calendar.MINUTE, 39)//분
        c.set(Calendar.SECOND, 0)//초

        startAlarm(c)
        Log.d("TTTT", c.timeInMillis.toString())
        Log.d("TTTT", System.currentTimeMillis().toString())
    }

    //알람 설정
    private fun startAlarm(c: Calendar){

        //알람매니저 선언
        var alarmManager: AlarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager

        var intent = Intent(this, AlertReceiver:: class.java)

        var pendingIntent = PendingIntent.getBroadcast(this, 1, intent, FLAG_MUTABLE)



        alarmManager.setExact(AlarmManager.RTC_WAKEUP, c.timeInMillis, pendingIntent)
    }
}