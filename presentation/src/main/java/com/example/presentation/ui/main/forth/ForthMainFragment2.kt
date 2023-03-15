package com.example.presentation.ui.main.forth

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.domain.util.UiState

import com.example.presentation.ui.base.BaseFragment

import com.example.domain.util.toast
import com.example.presentation.NetworkManager
import com.example.presentation.R
import com.example.presentation.adapter.RankRecyclerViewAdapter2
import com.example.presentation.databinding.FragmentForthMain2Binding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ForthMainFragment2 : BaseFragment<FragmentForthMain2Binding>() {

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentForthMain2Binding {
        return FragmentForthMain2Binding.inflate(inflater, container, false)
    }
    val viewModel : ForthViewModel by viewModels()
    val adapter by lazy {
        RankRecyclerViewAdapter2(
            onItemClicked = { destinationUid ->
                val intent = Intent(requireContext(), RankActivity2::class.java)
                intent.putExtra("destinationUid", destinationUid)
                startActivity(intent)
            }
        )
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.forth2Recyclerview.layoutManager = LinearLayoutManager(requireContext())
        binding.forth2Recyclerview.adapter = adapter
        binding.forth2RefreshLayout.setOnRefreshListener {
            binding.forth2RefreshLayout.isRefreshing = false
            if (!NetworkManager.checkNetworkState(requireContext())) {
                toast(requireContext(), getString(R.string.network_fail))
            } else {
                observer()
            }
        }
    }

    override fun onResume() {
        super.onResume()

        if (!NetworkManager.checkNetworkState(requireContext())) {
            toast(requireContext(), getString(R.string.network_fail))
        } else {
            observer()
        }
    }

    private fun observer() {
        viewModel.getSaveTimeYearInfo()
        viewModel.newSaveTimeYear.observe(viewLifecycleOwner) { state ->
            when(state) {
                is UiState.Success -> {
                    binding.progressCircular.hide()
                    adapter.submitList(state.data)
                }
                is UiState.Failure -> {
                    binding.progressCircular.hide()
                    toast(requireContext(), getString(R.string.rank_update_fail))
                }
                is UiState.Loading -> {
                    binding.progressCircular.show()

                }
            }
        }
    }



}