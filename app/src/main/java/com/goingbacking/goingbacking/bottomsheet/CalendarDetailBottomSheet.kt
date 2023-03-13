package com.goingbacking.goingbacking.bottomsheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.model.ScheduleModel
import com.example.domain.util.UiState
import com.goingbacking.goingbacking.adapter.CalendarEventAdapter2
import com.goingbacking.goingbacking.model.Event
import com.goingbacking.goingbacking.ui.main.third.ThirdViewModel
import com.goingbacking.goingbacking.databinding.BottomSheetCalendarDetailBinding
import com.goingbacking.goingbacking.util.makeGONE
import com.goingbacking.goingbacking.util.makeVisible
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDate

@AndroidEntryPoint
class CalendarDetailBottomSheet : BottomSheetDialogFragment() {
  private lateinit var binding : BottomSheetCalendarDetailBinding
  private val viewModel : ThirdViewModel by activityViewModels()
    private var schedules = mutableMapOf<LocalDate, List<ScheduleModel>>()
    private val eventsAdapter = CalendarEventAdapter2()
    private var schedulesList = mutableListOf<ScheduleModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = BottomSheetCalendarDetailBinding.inflate(inflater, container, false)

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
            adapter = eventsAdapter
        }
        binding.xBtn.setOnClickListener {
            dismiss()
        }
        binding.detailDate.text = arguments?.getString("date")
        val year_month = arguments?.getString("date")!!.substring(0 until 7)

        observer(year_month, arguments?.getString("date")!!)


        return binding.root
    }

    private fun observer(yearMonth : String, date : String) {
        viewModel.getSelectedDateInfo(yearMonth, date)
        viewModel.thirdSelectedDateDTOs.observe(viewLifecycleOwner) { state ->
            when(state) {
                is UiState.Success -> {
                    binding.progressCircular.hide()
                    schedulesList = state.data.toMutableList()
                    if (schedulesList.size == 0) {
                        binding.recyclerView.makeGONE()
                        binding.noScheduleTextView.makeVisible()
                    } else {
                        eventsAdapter.submitList(schedulesList)
                        binding.recyclerView.makeVisible()
                        binding.noScheduleTextView.makeGONE()
                    }

                }
                is UiState.Loading -> {
                    binding.progressCircular.show()
                }
                is UiState.Failure -> {
                    binding.progressCircular.hide()

                }
            }

        }

    }
}