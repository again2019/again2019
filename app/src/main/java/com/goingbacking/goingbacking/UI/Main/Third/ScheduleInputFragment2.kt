package com.goingbacking.goingbacking.UI.Main.Third

import android.annotation.SuppressLint
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
import com.goingbacking.goingbacking.Model.DateDTO
import com.goingbacking.goingbacking.Model.Event
import com.goingbacking.goingbacking.R
import com.goingbacking.goingbacking.UI.Base.BaseFragment
import com.goingbacking.goingbacking.UI.Input.SecondInputFragmentArgs
import com.goingbacking.goingbacking.databinding.FragmentScheduleInput2Binding
import com.goingbacking.goingbacking.databinding.FragmentThirdMainBinding
import com.goingbacking.goingbacking.util.convertDateToTimeStamp
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
    private var home2ButtonText = ""
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

        @SuppressLint("ResourceAsColor")
        private fun onClick() = with(binding) {
            home1Button.setOnClickListener {
                val timeSetListener =
                    TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
                        home1time = (hourOfDay * 60) + minute
                        var hourOfDay_str = hourOfDay.toString()
                        var minute_str = minute.toString()
                        if (hourOfDay / 10 == 0) {
                            hourOfDay_str = '0' + hourOfDay.toString()
                        }
                        if (minute / 10 == 0 ) {
                            minute_str = '0' + minute.toString()
                        }
                        binding.home1Button.text = String.format("%s:%s", hourOfDay_str, minute_str)
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
                        var hourOfDay_str = hourOfDay.toString()
                        var minute_str = minute.toString()
                        if (hourOfDay / 10 == 0) {
                            hourOfDay_str = '0' + hourOfDay.toString()
                        }
                        if (minute / 10 == 0 ) {
                            minute_str = '0' + minute.toString()
                        }
                        binding.home2Button.text = String.format("%s:%s", hourOfDay_str, minute_str)
                        home2ButtonText = String.format("%s-%s", hourOfDay_str, minute_str)
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
                        var hourOfDay_str = hourOfDay.toString()
                        var minute_str = minute.toString()
                        if (hourOfDay / 10 == 0) {
                            hourOfDay_str = '0' + hourOfDay.toString()
                        }
                        if (minute / 10 == 0 ) {
                            minute_str = '0' + minute.toString()
                        }
                        binding.dest1Button.text = String.format("%s:%s", hourOfDay_str, minute_str)
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
                        var hourOfDay_str = hourOfDay.toString()
                        var minute_str = minute.toString()
                        if (hourOfDay / 10 == 0) {
                            hourOfDay_str = '0' + hourOfDay.toString()
                        }
                        if (minute / 10 == 0 ) {
                            minute_str = '0' + minute.toString()
                        }
                        binding.dest2Button.text = String.format("%s:%s", hourOfDay_str, minute_str)
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
                if (destinationPlaceEditText.text.isNullOrEmpty()) {
                    toast(requireActivity(), getString(R.string.destination_input_fail))
                } else if (home1time == null) {
                        toast(requireActivity(), getString(R.string.first_start_time_fail))
                } else if (home2time == null) {
                        toast(requireActivity(), getString(R.string.first_end_time_fail))
                } else if (dest1time == null) {
                        toast(requireActivity(), getString(R.string.second_start_time_fail))
                } else if (dest2time == null) {
                        toast(requireActivity(), getString(R.string.second_end_time_fail))
                } else {
                    for (day in date) {
                        val event = Event(
                            dest = binding.destinationPlaceEditText.text.toString(),
                            date = day,
                            start = home2time,
                            end = dest1time,
                            start_t = home2time!! - home1time!!,
                            end_t = dest2time!! - dest1time!!
                        )

                        val path2 =
                            convertDateToTimeStamp(day + "-" + home2ButtonText).toString()

                        val dateDTO = DateDTO(
                            dateList = date.toList()
                        )
                        viewModel.addDateInfo(yearMonth, dateDTO)
                        viewModel.addScheduleEventInfo(yearMonth, path2, event)
                        requireActivity().finish()
                    }
                }
            }
        }
}


