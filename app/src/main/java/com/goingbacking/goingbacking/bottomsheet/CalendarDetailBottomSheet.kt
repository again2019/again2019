package com.goingbacking.goingbacking.bottomsheet

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.goingbacking.goingbacking.Adapter.CalendarEventAdapter
import com.goingbacking.goingbacking.Adapter.CalendarEventAdapter2
import com.goingbacking.goingbacking.Model.Event
import com.goingbacking.goingbacking.R
import com.goingbacking.goingbacking.ViewModel.MainViewModel
import com.goingbacking.goingbacking.databinding.BottomSheetCalendarDetailBinding
import com.goingbacking.goingbacking.util.UiState
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@AndroidEntryPoint
class CalendarDetailBottomSheet : BottomSheetDialogFragment() {
  private lateinit var binding : BottomSheetCalendarDetailBinding
  private val viewModel : MainViewModel by activityViewModels()
    private var eventss = mutableMapOf<LocalDate, List<Event>>()
    private val eventsAdapter = CalendarEventAdapter2()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = BottomSheetCalendarDetailBinding.inflate(inflater, container, false)

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
            adapter = eventsAdapter
            addItemDecoration(DividerItemDecoration(requireContext(), RecyclerView.VERTICAL))
        }

        binding.detailDate.text = arguments?.getString("date")
        val year_month = arguments?.getString("date")!!.substring(0 until 7)
        Log.d("experiment", "year_month " + year_month)

        observer(year_month, arguments?.getString("date")!!)


        return binding.root
    }

    private fun observer(year_month : String, date : String) {
        viewModel.getSelectedDateInfo(year_month, date)
        viewModel.thirdSelectedDateDTOs.observe(viewLifecycleOwner) { state ->
            when(state) {
                is UiState.Success -> {
                    binding.progressCircular.hide()
                    val date = LocalDate.parse(date, DateTimeFormatter.ISO_DATE)

                    eventss = state.data
                    updateAdapterForDate(date)

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

    private fun updateAdapterForDate(date: LocalDate) {
        eventsAdapter.apply {
            events.clear()
            events.addAll(eventss[date].orEmpty())
            notifyDataSetChanged()
        }
    }



}