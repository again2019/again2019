package com.goingbacking.goingbacking.UI.Main.Second

import android.hardware.camera2.params.RggbChannelVector.RED
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.github.aachartmodel.aainfographics.aachartcreator.*
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.goingbacking.goingbacking.R
import com.goingbacking.goingbacking.UI.Base.BaseFragment
import com.goingbacking.goingbacking.ViewModel.MainViewModel
import com.goingbacking.goingbacking.databinding.FragmentSecondMain1Binding
import com.goingbacking.goingbacking.util.UiState
import com.google.firebase.database.collection.LLRBNode
import com.google.type.Color
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SecondMainFragment1 : BaseFragment<FragmentSecondMain1Binding>(), AAChartView.AAChartViewCallBack {

    var chartType: String = ""
    val viewModel: MainViewModel by viewModels()
    val colorArray = arrayOf<Any>(
        "#76CEC2",
        "#6D7BF5",
        "#A47CF6",
        "#76CEC2",
        "#E385F3",
        "#01D0B6",
        "#50B478",
        "#B8C6FF",
        "#FEDE8B",
        "#D9F3EF",
        "#b2ebf2",
        "#FFF78B",
        "#FFD38D",
        "#8CEBFF",
        "#FF8E9C",
        "#C6FF8C",
        "#509CF2",
        "#EAA2B7",
        "#A9C7E3",
        "#DCE0AC",
        "#DEC0AE",
        "#ABE0E1",
        "#D8B4D9",
        "#98F4A1",
        "#FDB98F",
        "#9C98F4",
        "#C4C7C8",
        "#C9CCC0",
        "#D4B8B8")
    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentSecondMain1Binding {
        return FragmentSecondMain1Binding.inflate(inflater, container, false)
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
            .setBackgroundColor(R.color.colorBackGround)
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
                        saveCategoryList3.add(data.day!!.toString() + "월")
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