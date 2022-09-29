package com.goingbacking.goingbacking

import android.Manifest
import android.app.AlarmManager
import android.app.AlertDialog
import android.app.PendingIntent
import android.app.PendingIntent.FLAG_MUTABLE
import android.content.*
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.work.*
import com.goingbacking.goingbacking.MainActivityPackage.*
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

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

        //notification 구현 완료!! 합치기만 함녀돼
        //앞에 설정한 값으로 보여주기
        // 없으면 디폴트 값은 현재 시간
        var sharedPreferences = getSharedPreferences("daily alarm", MODE_PRIVATE)
        var millis :Long = sharedPreferences.getLong("nextNotifyTime", Calendar.getInstance().timeInMillis)
        Log.d("TTTT", millis.toString())

        var date_text :String = SimpleDateFormat("yyyy년 MM월 dd일 EE요일 a hh시 mm분", Locale.getDefault()).format(millis)
        Toast.makeText(this,"[처음 실행시] 다음 알람은 " + date_text + "으로 알람이 설정되었습니다!", Toast.LENGTH_SHORT).show()

        var calendar = Calendar.getInstance()
        calendar.timeInMillis = System.currentTimeMillis()
        calendar.set(Calendar.HOUR_OF_DAY, 12)
        calendar.set(Calendar.MINUTE, 43)
        calendar.set(Calendar.SECOND, 0)

        if (calendar.before(Calendar.getInstance())) {
            calendar.add(Calendar.DATE, 1)
        }
        date_text = SimpleDateFormat("yyyy년 MM월 dd일 EE요일 a hh시 mm분", Locale.getDefault()).format(calendar.timeInMillis)
        Toast.makeText(this, date_text + "으로 알람이 설정되었습니다!", Toast.LENGTH_SHORT).show()



        val editor :SharedPreferences.Editor = getSharedPreferences("daily alarm", MODE_PRIVATE).edit()
        editor.putLong("nextNotifyTime", calendar.timeInMillis)
        editor.apply()

        diaryNotification(calendar)
    }

    private fun diaryNotification (calendar: Calendar) {
        var dailyNotify = true

        var pm : PackageManager = this.packageManager
        var receiver = ComponentName(this, DeviceBootReceiver::class.java)
        var alarmIntent  = Intent(this, AlarmReceiver::class.java)
        var pendingIntent : PendingIntent = PendingIntent.getBroadcast(this, 0, alarmIntent, PendingIntent.FLAG_MUTABLE)
        var alarmManager :AlarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager

        if(dailyNotify) {
            if(alarmManager != null) {
                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, AlarmManager.INTERVAL_DAY, pendingIntent)

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pendingIntent)
                }
            }
            // 뷩후 실행되는 리시버
            pm.setComponentEnabledSetting(receiver, PackageManager.COMPONENT_ENABLED_STATE_ENABLED,PackageManager.DONT_KILL_APP)
        }
    }
}


