package com.goingbacking.goingbacking.UI.Main.Forth

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.github.aachartmodel.aainfographics.aachartcreator.*
import com.goingbacking.goingbacking.R
import com.goingbacking.goingbacking.UI.Base.BaseFragment
import com.goingbacking.goingbacking.ViewModel.ForthViewModel
import com.goingbacking.goingbacking.ViewModel.MainViewModel
import com.goingbacking.goingbacking.ViewModel.RankViewModel
import com.goingbacking.goingbacking.databinding.FragmentRank1Binding
import com.goingbacking.goingbacking.util.UiState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RankFragment1 : BaseFragment<FragmentRank1Binding>(), AAChartView.AAChartViewCallBack  {
    var chartType: String = ""
    val viewModel: RankViewModel by viewModels()

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentRank1Binding {
        return FragmentRank1Binding.inflate(inflater, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        yearObserver()
        monthObserver()
        dayObserver()
    }

    private fun setUpAAChartView(aaCharView: AAChartView, list1: ArrayList<Int>, list2: ArrayList<String>) {
        aaCharView.setBackgroundColor(0)
        aaCharView.callBack = this
        val aaChartModel = configureAAChartModel(list1, list2)
        aaCharView.aa_drawChartWithChartModel(aaChartModel)
    }

    private fun configureAAChartModel(DTOList:ArrayList<Int>, categoryList: ArrayList<String>): AAChartModel {
        chartType = AAChartType.Column.value
        val chartTypeEnum = AAChartType.Column

        val aaChartModel = AAChartModel.Builder(requireActivity())

            .setChartType(chartTypeEnum)
            .setBackgroundColor("#4b2b7f")
            .setDataLabelsEnabled(true)
            .setYAxisGridLineWidth(0f)
            .setLegendEnabled(false)
            .setTouchEventEnabled(true)

            .setSeries(
                AASeriesElement()
                    .data(DTOList.toTypedArray()),
            )
            .build()

        aaChartModel.categories(categoryList.toTypedArray())

        return aaChartModel
    }

    override fun chartViewDidFinishLoad(aaChartView: AAChartView) {
    }

    override fun chartViewMoveOverEventMessage(
        aaChartView: AAChartView,
        messageModel: AAMoveOverEventMessageModel
    ) {
    }


    fun yearObserver() {
        viewModel.getSecondSaveYearInfo()
        viewModel.secondSaveYearDTOs.observe(viewLifecycleOwner) { state ->
            when(state) {
                is UiState.Success -> {
                    val saveTimeYearDTOList = arrayListOf<Int>()
                    val saveCategoryList1 = arrayListOf<String>()
                    for (data in state.data) {
                        saveTimeYearDTOList.add(data.count!!)
                        saveCategoryList1.add(data.year!!.toString())
                    }
                    setUpAAChartView(binding.AAChartView1, saveTimeYearDTOList, saveCategoryList1)

                }
                is UiState.Failure -> {
                    Log.e("experiment", state.error.toString())
                }
            }

        }
    }

    fun monthObserver() {
        viewModel.getSecondSaveMonthInfo()
        viewModel.secondSaveMonthDTOs.observe(viewLifecycleOwner) { state ->
            when(state) {
                is UiState.Success -> {
                    val saveTimeMonthDTOList = arrayListOf<Int>()
                    val saveCategoryList2 = arrayListOf<String>()
                    for (data in state.data) {
                        saveTimeMonthDTOList.add(data.count!!)
                        saveCategoryList2.add(data.month!!.toString())
                    }

                    Log.e("experiment", "month: " + saveTimeMonthDTOList.toString())
                    setUpAAChartView(binding.AAChartView2, saveTimeMonthDTOList, saveCategoryList2)

                }
                is UiState.Failure -> {
                    Log.e("experiment", state.error.toString())
                }
            }

        }
    }

    fun dayObserver() {
        viewModel.getSecondSaveDayInfo()
        viewModel.secondSaveDayDTOs.observe(viewLifecycleOwner) { state ->
            when(state) {
                is UiState.Success -> {
                    val saveTimeDayDTOList = arrayListOf<Int>()
                    val saveCategoryList3 = arrayListOf<String>()
                    for (data in state.data) {
                        saveTimeDayDTOList.add(data.count!!)
                        saveCategoryList3.add(data.month!!.toString())
                    }

                    setUpAAChartView(binding.AAChartView3, saveTimeDayDTOList, saveCategoryList3)

                }
                is UiState.Failure -> {
                    Log.e("experiment", state.error.toString())
                }
            }

        }
    }

}



