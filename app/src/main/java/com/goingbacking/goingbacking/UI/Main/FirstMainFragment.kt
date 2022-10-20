package com.goingbacking.goingbacking.UI.Main


import android.os.Bundle

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager

import com.goingbacking.goingbacking.*
import com.goingbacking.goingbacking.Adapter.TodayRecyclerViewAdapter
import com.goingbacking.goingbacking.UI.Base.BaseFragment
import com.goingbacking.goingbacking.databinding.FragmentFirstMainBinding
import com.goingbacking.goingbacking.util.PrefUtil
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class FirstMainFragment : BaseFragment<FragmentFirstMainBinding>() {
    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentFirstMainBinding {
        return FragmentFirstMainBinding.inflate(inflater, container, false)
    }




    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tmpTimeButton.setOnClickListener {
            findNavController().navigate(R.id.action_firstMainFragment_to_tmpTimeActivity)
        }

        binding.todayTime.text = PrefUtil.getTodayTotalTime(requireContext()).toString()

        val todayWhatToDo = PrefUtil.getTodayWhatToDo(requireActivity()).toString()
            .replace("[", "").replace("]", "").split(", ")
        val todayWhatToDoTime = PrefUtil.getTodayWhatToDoTime(requireActivity()).toString()
            .replace("[", "").replace("]", "").split(", ")

        val adapter = TodayRecyclerViewAdapter(requireActivity(), todayWhatToDo, todayWhatToDoTime)
        binding.todayRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.todayRecyclerView.adapter = adapter



    }



    }




