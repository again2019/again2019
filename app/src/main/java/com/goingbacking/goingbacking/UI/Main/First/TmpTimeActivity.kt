package com.goingbacking.goingbacking.UI.Main.First

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem

import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.goingbacking.goingbacking.Adapter.TmpTimeRecyclerViewAdapter
import com.goingbacking.goingbacking.R
import com.goingbacking.goingbacking.UI.Base.BaseActivity

import com.goingbacking.goingbacking.bottomsheet.WhatToDoSaveBottomSheet
import com.goingbacking.goingbacking.databinding.ActivityTmpTimeBinding
import com.goingbacking.goingbacking.util.UiState
import com.goingbacking.goingbacking.util.toast
import com.google.firebase.firestore.FieldValue
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TmpTimeActivity : BaseActivity<ActivityTmpTimeBinding>({
    ActivityTmpTimeBinding.inflate(it)
}) {

    val adapter by lazy {
        TmpTimeRecyclerViewAdapter(
            onItemClicked = { wakeUpTime1, wakeUpTime2, wakeUpTime3, wakeUpTime4,count_double, simpleFormat1, simpleFormat2, simpleFormat3, simpleFormat4 ->
                val bottom  = WhatToDoSaveBottomSheet()
                val bundle = Bundle()
                if (count_double.equals(0.0)) {
                    toast(this, getString(R.string.no_time_input))
                } else {
                    bundle.putDouble("count_double", count_double)
                    bundle.putString("simpleFormat1", simpleFormat1)
                    bundle.putString("simpleFormat2", simpleFormat2)
                    bundle.putString("simpleFormat3", simpleFormat3)
                    bundle.putString("simpleFormat4", simpleFormat4)


                    bundle.putString("wakeUpTime1", wakeUpTime1)
                    bundle.putString("wakeUpTime2", wakeUpTime2)
                    bundle.putString("wakeUpTime3", wakeUpTime3)
                    bundle.putString("wakeUpTime4", wakeUpTime4)

                    binding.progressCircular.hide()
                    bottom.arguments = bundle
                    bottom.show(supportFragmentManager, bottom.tag)
                }


            }

        )
    }
    val viewModel : FirstViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val menuHost: MenuHost = this
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                when(menuItem.itemId) {
                    android.R.id.home -> {
                        finish()
                        return true
                    }
                }
                return true
            }
        }, this, Lifecycle.State.RESUMED)





        TmpTimeOberver()

        binding.TmprecyclerView.layoutManager = LinearLayoutManager(this)
        binding.TmprecyclerView.adapter = adapter

    }


    private fun TmpTimeOberver(){
        viewModel.getTmpTimeInfo()
        viewModel.tmpTimeDTOs.observe(this) { state ->
            when(state){
                is UiState.Success -> {
                    binding.progressCircular.hide()
                    adapter.submitList(state.data)
//                    adapter.updateList(state.data)
                }
                is UiState.Loading -> {
                    binding.progressCircular.show()
                }
                is UiState.Failure -> {
                    binding.progressCircular.hide()
                    toast(this, getString(R.string.load_tmpTime_fail))
                }

            }
        }
    }



}