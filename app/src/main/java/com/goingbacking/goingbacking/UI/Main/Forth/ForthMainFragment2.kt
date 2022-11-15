package com.goingbacking.goingbacking.UI.Main.Forth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.goingbacking.goingbacking.Adapter.RankRecyclerViewAdapter2
import com.goingbacking.goingbacking.R
import com.goingbacking.goingbacking.UI.Base.BaseFragment
import com.goingbacking.goingbacking.bottomsheet.RankBottomSheet
import com.goingbacking.goingbacking.databinding.FragmentForthMain2Binding
import com.goingbacking.goingbacking.util.UiState
import com.goingbacking.goingbacking.util.toast
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
            viewModel,
            onItemClicked = { destinationUid ->
                val bottom  = RankBottomSheet()
                val bundle = Bundle()
                bundle.putString("destinationUid", destinationUid)
                bottom.arguments = bundle
                bottom.show(childFragmentManager, bottom.tag)
            }
        )
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.forth2Recyclerview.layoutManager = LinearLayoutManager(requireContext())
        binding.forth2Recyclerview.adapter = adapter
        binding.forth2RefreshLayout.setOnRefreshListener {
            binding.forth2RefreshLayout.isRefreshing = false
            observer()
        }
    }

    override fun onResume() {
        super.onResume()

        observer()
    }

    private fun observer() {
        viewModel.getSaveTimeYearInfo()
        viewModel.newSaveTimeYear.observe(viewLifecycleOwner) { state ->
            when(state) {
                is UiState.Success -> {
                    binding.progressCircular.hide()
                    adapter.apply {
                        newSaveTimeYearDTOList = state.data
                        notifyDataSetChanged()
                    }
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