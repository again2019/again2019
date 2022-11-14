package com.goingbacking.goingbacking.UI.Main.Third

import android.app.TimePickerDialog
import android.os.Bundle
import android.view.*
import androidx.annotation.NonNull
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.goingbacking.goingbacking.Model.Event
import com.goingbacking.goingbacking.R
import com.goingbacking.goingbacking.UI.Base.BaseFragment
import com.goingbacking.goingbacking.UI.Input.SecondInputFragmentArgs
import com.goingbacking.goingbacking.databinding.FragmentScheduleInput2Binding
import com.goingbacking.goingbacking.databinding.FragmentThirdMainBinding
import com.goingbacking.goingbacking.util.toast
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.*


@AndroidEntryPoint
class ScheduleInputFragment2 : BaseFragment<FragmentScheduleInput2Binding>() {
    private val viewModel : ThirdViewModel by viewModels()
    private var home1time: Int? = null
    private var home2time: Int? = null
    private var dest1time: Int? = null
    private var dest2time: Int? = null
    private val cal = Calendar.getInstance()
    private var date = arrayOf<String>()
    private var yearMonth = ""

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentScheduleInput2Binding {
        return FragmentScheduleInput2Binding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val menuHost: MenuHost = requireActivity()

        (activity as AppCompatActivity).setSupportActionBar(binding.toolbar)
        (activity as AppCompatActivity).supportActionBar?.setDisplayShowTitleEnabled(false)
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)

        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                when(menuItem.itemId) {
                    android.R.id.home -> {
                        findNavController().navigate(R.id.action_scheduleInputFragment2_to_scheduleInputFragment1)
                        return true
                    }
                }
                return true
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)


        val args : ScheduleInputFragment2Args by navArgs()
        date = args.dateList
        yearMonth = args.yearMonth

        onClick()
    }

        private fun onClick() = with(binding) {
            home1Button.setOnClickListener {
                val timeSetListener =
                    TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
                        home1time = (hourOfDay * 60) + minute
                        binding.home1Button.text = String.format("%d-%d", hourOfDay, minute)
                    }
                TimePickerDialog(
                    requireContext(),
                    timeSetListener,
                    cal.get(Calendar.HOUR_OF_DAY),
                    cal.get(Calendar.MINUTE),
                    false
                ).show()
            }
            home2Button.setOnClickListener {
                val timeSetListener =
                    TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
                        home2time = (hourOfDay * 60) + minute
                        binding.home2Button.text = String.format("%d-%d", hourOfDay, minute)
                    }
                TimePickerDialog(
                    requireContext(),
                    timeSetListener,
                    cal.get(Calendar.HOUR_OF_DAY),
                    cal.get(Calendar.MINUTE),
                    false
                ).show()
            }
            dest1Button.setOnClickListener {
                val timeSetListener =
                    TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
                        dest1time = (hourOfDay * 60) + minute
                        binding.dest1Button.text = String.format("%d-%d", hourOfDay, minute)
                    }
                TimePickerDialog(
                    requireContext(),
                    timeSetListener,
                    cal.get(Calendar.HOUR_OF_DAY),
                    cal.get(Calendar.MINUTE),
                    false
                ).show()
            }
            dest2Button.setOnClickListener {
                val timeSetListener =
                    TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
                        dest2time = (hourOfDay * 60) + minute
                        binding.dest2Button.text = String.format("%d-%d", hourOfDay, minute)
                    }
                TimePickerDialog(
                    requireContext(),
                    timeSetListener,
                    cal.get(Calendar.HOUR_OF_DAY),
                    cal.get(Calendar.MINUTE),
                    false
                ).show()
            }

            finishBtn.setOnClickListener {
            if (destinationPlaceEditText.text.equals("")) {
                toast(requireActivity(), getString(R.string.destination_input_fail))
//            } else if (list == null) {
//                Toast.makeText(this@ScheduleInputActivity, R.string.date_list_input_fail, Toast.LENGTH_SHORT).show()
//            } else if (home1time == null) {
//                Toast.makeText(this@ScheduleInputActivity, R.string.first_start_time_fail, Toast.LENGTH_SHORT).show()
//            } else if (home2time == null) {
//                Toast.makeText(this@ScheduleInputActivity, R.string.first_end_time_fail, Toast.LENGTH_SHORT).show()
//            } else if (dest1time == null) {
//                Toast.makeText(this@ScheduleInputActivity, R.string.second_start_time_fail, Toast.LENGTH_SHORT).show()
//            } else if (dest2time == null) {
//                Toast.makeText(this@ScheduleInputActivity, R.string.second_end_time_fail, Toast.LENGTH_SHORT).show()
//            } else if (monday || tuesday || wednesday || thursday || friday || saturday || sunday) {
//                Toast.makeText(this@ScheduleInputActivity, R.string.day_select_fail, Toast.LENGTH_SHORT).show()
            } else {
                for (day in date) {
                    val event = Event()
                    event.dest = binding.destinationPlaceEditText.text.toString()
                    event.date = day
                    event.start = home2time
                    event.end = dest1time
                    event.start_t = home2time!!-home1time!!
                    event.end_t = dest2time!! - dest1time!!

                    val path2 = convertDateToTimeStamp(day + "-" + binding.home2Button.text.toString()).toString()
                    viewModel.addScheduleEventInfo(yearMonth, path2, event)
                }
            }
        }

        fun convertDateToTimeStamp(date: String) : Long {
            val sdf = SimpleDateFormat("yyyy-MM-dd-hh-mm", Locale.getDefault())
            return sdf.parse(date).time
        }


}