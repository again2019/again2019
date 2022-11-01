package com.goingbacking.goingbacking.UI.Main.Forth

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.goingbacking.goingbacking.Adapter.RankRecyclerViewAdapter1
import com.goingbacking.goingbacking.R
import com.goingbacking.goingbacking.Repository.ForthRepository
import com.goingbacking.goingbacking.UI.Base.BaseFragment
import com.goingbacking.goingbacking.ViewModel.ForthViewModel
import com.goingbacking.goingbacking.bottomsheet.RankBottomSheet
import com.goingbacking.goingbacking.databinding.FragmentForthMain1Binding
import com.goingbacking.goingbacking.util.UiState
import com.goingbacking.goingbacking.util.toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ForthMainFragment1 : BaseFragment<FragmentForthMain1Binding>() {
    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentForthMain1Binding {
        return FragmentForthMain1Binding.inflate(inflater, container, false)
    }

    val forthRepository = ForthRepository(FirebaseAuth.getInstance().currentUser, FirebaseFirestore.getInstance())
    val viewModel : ForthViewModel by viewModels()
    val adapter by lazy {
        RankRecyclerViewAdapter1(
            forthRepository,
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

        binding.forth1Recyclerview.layoutManager = LinearLayoutManager(requireContext())
        binding.forth1Recyclerview.adapter = adapter
        binding.forth1RefreshLayout.setOnRefreshListener {
            binding.forth1RefreshLayout.isRefreshing = false
            observer()
        }
        observer()

    }



    private fun observer() {
        viewModel.getSaveTimeMonthInfo()
        viewModel.newSaveTimeMonth.observe(viewLifecycleOwner) { state ->
            when(state) {
                is UiState.Success -> {
                    binding.progressCircular.hide()
                    adapter.updateList(state.data)
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