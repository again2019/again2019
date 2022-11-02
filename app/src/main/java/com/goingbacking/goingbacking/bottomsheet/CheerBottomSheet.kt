package com.goingbacking.goingbacking.bottomsheet

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.goingbacking.goingbacking.Adapter.CheerRecyclerViewAdapter
import com.goingbacking.goingbacking.R
import com.goingbacking.goingbacking.ViewModel.ForthViewModel
import com.goingbacking.goingbacking.databinding.BottomSheetCheerBinding
import com.goingbacking.goingbacking.util.UiState
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.time.LocalDate

class CheerBottomSheet : BottomSheetDialogFragment() {
    private lateinit var binding : BottomSheetCheerBinding
    private val viewModel : ForthViewModel by activityViewModels()
    private val cheerAdapter = CheerRecyclerViewAdapter()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = BottomSheetCheerBinding.inflate(inflater, container, false)

        val destinationUid = arguments?.getString("destinationUid")
        Log.d("experiment", destinationUid.toString())

        if (destinationUid == null) {

        } else {
            binding.cheerRecyclerView.apply {
                layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
                adapter = cheerAdapter
                addItemDecoration(DividerItemDecoration(requireContext(), RecyclerView.VERTICAL))

            }

            observer(destinationUid)
        }








        return binding.root

    }

    private fun observer(destinationUid :String) = with(binding) {
        viewModel.getCheerInfo(destinationUid)
        viewModel.cheerInfo.observe(viewLifecycleOwner) { state ->
            when(state) {
                is UiState.Success -> {
                    progressCircular.hide()
                    Log.d("experiment", state.data.toString())
                    updateAdapterForDate(state.data)
                    state.data.toTypedArray()
                }
                is UiState.Failure -> {
                    progressCircular.hide()
                }
                is UiState.Loading -> {
                    progressCircular.show()
                }
            }

        }
    }

    private fun updateAdapterForDate(list: List<String>) {
        cheerAdapter.apply {
            events = list
            notifyDataSetChanged()
        }
    }

}