package com.goingbacking.goingbacking.UI.Main.Third

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.goingbacking.goingbacking.R
import com.goingbacking.goingbacking.UI.Base.BaseFragment
import com.goingbacking.goingbacking.UI.Input.SecondInputFragmentArgs
import com.goingbacking.goingbacking.databinding.FragmentScheduleInput2Binding
import com.goingbacking.goingbacking.databinding.FragmentThirdMainBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ScheduleInputFragment2 : BaseFragment<FragmentScheduleInput2Binding>() {


    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentScheduleInput2Binding {
        return FragmentScheduleInput2Binding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val menuHost: MenuHost = requireActivity()

        (activity as AppCompatActivity).setSupportActionBar(binding.toolbar)
        (activity as AppCompatActivity).supportActionBar?.setDisplayShowTitleEnabled(false)
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)

        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                when(menuItem.itemId) {
                    android.R.id.home -> {
                        findNavController().navigate(R.id.action_scheduleInputFragment2_to_scheduleInputFragment1)
                        return true
                    }
                }
                return true
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)


        val args : ScheduleInputFragment2Args by navArgs()
        val date = args.date
        val yearMonth = args.yearMonth




    }


}