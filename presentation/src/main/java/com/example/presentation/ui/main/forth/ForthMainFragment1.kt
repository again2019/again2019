package com.example.presentation.ui.main.forth

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.domain.util.UiState
import com.example.presentation.adapter.RankRecyclerViewAdapter1
import com.example.presentation.ui.base.BaseFragment
import com.example.domain.util.toast
import com.example.presentation.NetworkManager
import com.example.presentation.R
import com.example.presentation.databinding.FragmentForthMain1Binding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ForthMainFragment1 : BaseFragment<FragmentForthMain1Binding>() {
    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentForthMain1Binding {
        return FragmentForthMain1Binding.inflate(inflater, container, false)
    }

    val viewModel : ForthViewModel by viewModels()
    val adapter by lazy {
        RankRecyclerViewAdapter1(
            onItemClicked = { destinationUid ->
                val intent = Intent(requireContext(), RankActivity1::class.java)
                intent.putExtra("destinationUid", destinationUid)
                startActivity(intent)
            }
        )
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.forth1Recyclerview.layoutManager = LinearLayoutManager(requireContext())
        binding.forth1Recyclerview.adapter = adapter
        binding.forth1RefreshLayout.setOnRefreshListener {
            binding.forth1RefreshLayout.isRefreshing = false
            if (!NetworkManager.checkNetworkState(requireContext())) {
                toast(requireContext(), getString(R.string.network_fail))
            } else {
                observer1()
            }
        }


    }

    override fun onResume() {
        super.onResume()

        if (!NetworkManager.checkNetworkState(requireContext())) {
            toast(requireContext(), getString(R.string.network_fail))
        } else {
            observer1()
        }
    }

    // 랭킹을 받아오는 코드
    private fun observer1() {
        viewModel.getSaveTimeMonthInfo()
        viewModel.newSaveTimeMonth.observe(viewLifecycleOwner) { state ->
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