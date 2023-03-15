package com.example.presentation.ui.main.second

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.domain.util.UiState
import com.github.aachartmodel.aainfographics.aachartcreator.*
import com.example.presentation.ui.base.BaseFragment
import com.example.domain.util.Constants.Companion.colorArray
import com.example.presentation.R
import com.example.presentation.databinding.FragmentSecondMain1Binding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SecondMainFragment1 : BaseFragment<FragmentSecondMain1Binding>(), AAChartView.AAChartViewCallBack {

    var chartType: String = ""
    val viewModel: SecondViewModel by viewModels()
    private var lifecycleState = true

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentSecondMain1Binding {
        return FragmentSecondMain1Binding.inflate(inflater, container, false)
    }

    override fun onResume() {
        super.onResume()

        if (!lifecycleState) {
            yearObserver()
            monthObserver()
            dayObserver()
        }
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        yearObserver()
        monthObserver()
        dayObserver()
    }

    override fun onStop() {
        super.onStop()

        lifecycleState = false
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

        colorArray.shuffle()
        val aaChartModel = AAChartModel.Builder(requireActivity())

            .setChartType(chartTypeEnum)
            .setBackgroundColor(R.color.white)
            .setDataLabelsEnabled(true)
            .setYAxisLabelsEnabled(false)
            .setYAxisGridLineWidth(0f)
            .setYAxisTitle("")
            .setAxesTextColorRes(R.color.titleMain)
            .setColorsTheme(colorArray)
            .setLegendEnabled(false)
            .setTouchEventEnabled(false)
            .setPolar(false)
            .setTooltipEnabled(false)
            .setSeries(
                AASeriesElement()
                    .colorByPoint(true)
                    .data(DTOList.toTypedArray())
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
                    binding.progressCircular.hide()
                    val saveTimeYearDTOList = arrayListOf<Int>()
                    val saveCategoryList1 = arrayListOf<String>()
                    for (data in state.data) {
                        saveTimeYearDTOList.add(data.count!!)
                        saveCategoryList1.add(data.year!!.toString() + "년")
                    }
                    setUpAAChartView(binding.AAChartView3, saveTimeYearDTOList, saveCategoryList1)

                }
                is UiState.Failure -> {
                    binding.progressCircular.hide()
                    Log.e("experiment", state.error.toString())
                }
                is UiState.Loading -> {
                    binding.progressCircular.show()
                }
            }

        }
    }

    fun monthObserver() {
        viewModel.getSecondSaveMonthInfo()
        viewModel.secondSaveMonthDTOs.observe(viewLifecycleOwner) { state ->
            when(state) {
                is UiState.Success -> {
                    binding.progressCircular.hide()
                    val saveTimeMonthDTOList = arrayListOf<Int>()
                    val saveCategoryList2 = arrayListOf<String>()
                    for (data in state.data) {
                        saveTimeMonthDTOList.add(data.count!!)
                        saveCategoryList2.add(data.month!!.toString() + "월")
                    }

                    Log.e("experiment", "month: " + saveTimeMonthDTOList.toString())
                    setUpAAChartView(binding.AAChartView2, saveTimeMonthDTOList, saveCategoryList2)

                }
                is UiState.Failure -> {
                    binding.progressCircular.hide()
                    Log.e("experiment", state.error.toString())
                }
                is UiState.Loading -> {
                    binding.progressCircular.show()
                }
            }

        }
    }

    fun dayObserver() {
        viewModel.getSecondSaveDayInfo()
        viewModel.secondSaveDayDTOs.observe(viewLifecycleOwner) { state ->
            when(state) {
                is UiState.Success -> {
                    binding.progressCircular.hide()
                    val saveTimeDayDTOList = arrayListOf<Int>()
                    val saveCategoryList3 = arrayListOf<String>()
                    for (data in state.data) {
                        saveTimeDayDTOList.add(data.count!!)
                        saveCategoryList3.add(data.day!!.toString() + "일")
                    }

                    setUpAAChartView(binding.AAChartView1, saveTimeDayDTOList, saveCategoryList3)

                }
                is UiState.Failure -> {
                    binding.progressCircular.hide()
                    Log.e("experiment", state.error.toString())
                }
                is UiState.Loading -> {
                    binding.progressCircular.show()
                }
            }

        }
    }




}