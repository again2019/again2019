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
import com.goingbacking.goingbacking.Adapter.CheerRecyclerViewAdapter
import com.goingbacking.goingbacking.R
import com.goingbacking.goingbacking.ViewModel.ForthViewModel
import com.goingbacking.goingbacking.databinding.BottomSheetCheerBinding
import com.goingbacking.goingbacking.util.UiState
import com.goingbacking.goingbacking.util.toast
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class CheerBottomSheet : BottomSheetDialogFragment() {
    private lateinit var binding : BottomSheetCheerBinding
    private val viewModel : ForthViewModel by activityViewModels()
    private val cheerAdapter = CheerRecyclerViewAdapter(
        onDeleteClick = { hostUid, original_cheer ->
            viewModel.deleteCheerInfo(hostUid, original_cheer)
            observer1(hostUid)
        }
    )
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = BottomSheetCheerBinding.inflate(inflater, container, false)

        val destinationUid = arguments?.getString("destinationUid")
        //Log.d("experiment", destinationUid.toString())

        if (destinationUid == null) {
            toast(requireContext(), getString(R.string.no_information))
        } else {
            binding.cheerRecyclerView.apply {
                layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
                adapter = cheerAdapter
                addItemDecoration(DividerItemDecoration(requireContext(), RecyclerView.VERTICAL))
            }
            observer1(destinationUid)
            binding.cheerOkayButton.setOnClickListener {
                val message = binding.cheerEditText.text.toString()
                if (message.equals("")) {
                    toast(requireContext(), getString(R.string.cheer_message))
                } else {
                    observer2(destinationUid, message)
                    binding.cheerEditText.setText("")
                }
            }
        }

        return binding.root

    }

    private fun observer1(destinationUid :String) = with(binding) {

        viewModel.getCheerInfo(destinationUid)
        viewModel.cheerInfo.observe(viewLifecycleOwner) { state ->
            when(state) {
                is UiState.Success -> {
                    progressCircular.hide()
                    updateAdapterForDate(state.data, destinationUid)
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

    private fun observer2(destinationUid :String, message:String) = with(binding) {
        viewModel.addCheerInfo(destinationUid, message)
        viewModel.addCheerInfo.observe(viewLifecycleOwner) { state ->
            when(state) {
                is UiState.Success -> {
                    binding.progressCircular.hide()
                    Log.d("experiment", state.data.toString())
                    updateAdapterForDate(state.data, destinationUid)
                    state.data.toTypedArray()
                }
                is UiState.Failure -> {
                    binding.progressCircular.hide()
                }
                is UiState.Loading -> {
                    binding.progressCircular.show()
                }
            }
        }
    }

    private fun updateAdapterForDate(list: List<String>, destinationUid :String) {
        cheerAdapter.apply {
            events = list
            hostUid = destinationUid
            notifyDataSetChanged()
            }
        }
    }
