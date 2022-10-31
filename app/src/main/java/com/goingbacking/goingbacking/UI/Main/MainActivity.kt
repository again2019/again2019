package com.goingbacking.goingbacking.UI.Main


import android.app.AlarmManager
import android.app.PendingIntent
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.goingbacking.goingbacking.BR.CountReceiver
import com.goingbacking.goingbacking.BR.DeviceBootReceiver
import com.goingbacking.goingbacking.R
import com.goingbacking.goingbacking.UI.Base.BaseActivity
import com.goingbacking.goingbacking.UI.Tutorial.TutorialActivity
import com.goingbacking.goingbacking.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import java.util.*


@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>({
    ActivityMainBinding.inflate(it)
}) {

    companion object {
        private const val ID = "id"
        private const val TYPE = "type"
        private const val CHANNEL = "channel"
        private const val VALUE = 3000
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
         initBottomNavigation()
         notification()
    }

    private fun initBottomNavigation() {
        NavigationUI.setupWithNavController(binding.bottomNavigation, findNavController(R.id.nav_host))
    }

    private fun notification() {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = System.currentTimeMillis()
        calendar.set(Calendar.HOUR_OF_DAY, 0)
        calendar.set(Calendar.MINUTE, 5)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)

        if (calendar.before(Calendar.getInstance())) {
            calendar.add(Calendar.DATE, 1)
        }

        val pm: PackageManager = this.packageManager
        val receiver = ComponentName(this, DeviceBootReceiver::class.java)
        val alarmIntent = Intent(this, CountReceiver::class.java)
        alarmIntent.putExtra(ID, VALUE)
        alarmIntent.putExtra(TYPE, CHANNEL)

        val pendingIntent: PendingIntent = PendingIntent.getBroadcast(
            this, VALUE, alarmIntent, PendingIntent.FLAG_IMMUTABLE
        )

        val alarmManager: AlarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            pendingIntent
        )

        pm.setComponentEnabledSetting(
            receiver,
            PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
            PackageManager.DONT_KILL_APP
        )
    }
}


