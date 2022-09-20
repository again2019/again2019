package com.goingbacking.goingbacking

import android.Manifest
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.core.content.ContextCompat
import com.goingbacking.goingbacking.MainActivityPackage.*
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*

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

    }
}