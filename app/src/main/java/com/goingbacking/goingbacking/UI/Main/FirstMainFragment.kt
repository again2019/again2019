package com.goingbacking.goingbacking.UI.Main


import android.content.Intent
import android.os.Bundle
import android.util.Log

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager

import com.goingbacking.goingbacking.*
import com.goingbacking.goingbacking.Adapter.TodayRecyclerViewAdapter
import com.goingbacking.goingbacking.UI.Base.BaseFragment
import com.goingbacking.goingbacking.UI.Main.First.TmpTimeActivity
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
            moveTmpTimePage()
        }

        binding.todayTime.text = PrefUtil.getTodayTotalTime(requireContext()).toString()

        val todayWhatToDo = PrefUtil.getTodayWhatToDo(requireActivity()).toString()
        val todayWhatToDoTime = PrefUtil.getTodayWhatToDoTime(requireActivity()).toString()

       if(!(todayWhatToDo.equals("") || todayWhatToDoTime.equals(""))) {
            val todayWhatToDo2 = todayWhatToDo.replace("[", "").replace("]", "").split(", ")
            val todayWhatToDoTime2 = todayWhatToDoTime.replace("[", "").replace("]", "").split(", ")
            val adapter = TodayRecyclerViewAdapter(requireActivity(), todayWhatToDo2, todayWhatToDoTime2)
            binding.todayRecyclerView.layoutManager = LinearLayoutManager(requireContext())
            binding.todayRecyclerView.adapter = adapter
        }
    }

    private fun moveTmpTimePage() {
        val intent = Intent(requireActivity(), TmpTimeActivity::class.java)
        startActivity(intent)
    }

}




