package com.goingbacking.goingbacking.UI.Main.Second

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.github.aachartmodel.aainfographics.aachartcreator.*
import com.goingbacking.goingbacking.R
import com.goingbacking.goingbacking.UI.Base.BaseFragment
import com.goingbacking.goingbacking.databinding.FragmentSecondMain2Binding
import com.goingbacking.goingbacking.util.Constants.Companion.colorArray
import com.goingbacking.goingbacking.util.UiState
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SecondMainFragment2 : BaseFragment<FragmentSecondMain2Binding>(), AAChartView.AAChartViewCallBack {
    var chartType: String = ""

    val viewModel: SecondViewModel by viewModels()

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentSecondMain2Binding {
       return FragmentSecondMain2Binding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        yearObserver()
        monthObserver()
    }

    private fun setUpAAChartView(aaCharView: AAChartView, array: Array<Any>) {
        aaCharView.setBackgroundColor(0)
        aaCharView.callBack = this
        val aaChartModel = configureAAChartModel(array)
        aaCharView.aa_drawChartWithChartModel(aaChartModel)
    }
    private fun configureAAChartModel(array: Array<Any>): AAChartModel {
        chartType = AAChartType.Pie.value
        val chartTypeEnum = AAChartType.Pie
        colorArray.shuffle()
        val aaChartModel = AAChartModel.Builder(requireActivity())

            .setChartType(chartTypeEnum)
            .setBackgroundColor(R.color.colorBackGround)
            .setDataLabelsEnabled(true)
            .setAxesTextColorRes(R.color.titleMain)
            .setColorsTheme(colorArray)
            .setTooltipEnabled(false)
            .setTouchEventEnabled(false)
            .setLegendEnabled(false)
            .setSeries(
                AASeriesElement()
                    .colorByPoint(true)
                    .data(array)
            )
            .build()


        return aaChartModel
    }

    override fun chartViewDidFinishLoad(aaChartView: AAChartView) {
    }

    override fun chartViewMoveOverEventMessage(
        aaChartView: AAChartView,
        messageModel: AAMoveOverEventMessageModel
    ) {
    }



    fun monthObserver() {
        viewModel.getSecondWhatToDoMonthInfo()
        viewModel.secondwhatToDoMonthDTOs.observe(viewLifecycleOwner) { state ->
            when(state) {
                is UiState.Success -> {
                    binding.progressCircular.hide()
                    val whatToDoMonthDTOList = arrayListOf<Any>()
                    for (data in state.data) {
                        val tmpList = arrayListOf<Any>()
                        tmpList.add(data.whatToDo!!)
                        tmpList.add(data.count!!)
                        tmpList.toTypedArray()
                        whatToDoMonthDTOList.add(tmpList)
                    }

                    setUpAAChartView(binding.AAChartView1, whatToDoMonthDTOList.toTypedArray())

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

    fun yearObserver() {
        viewModel.getSecondWhatToDoYearInfo()
        viewModel.secondwhatToDoYearDTOs.observe(viewLifecycleOwner) { state ->
            when(state) {
                is UiState.Success -> {
                    binding.progressCircular.hide()
                    val whatToDoYearDTOList = arrayListOf<Any>()
                    for (data in state.data) {
                        val tmpList = arrayListOf<Any>()
                        Log.d("experiment", "aaa" + tmpList.toString())
                        tmpList.add(data.whatToDo!!)
                        tmpList.add(data.count!!)
                        tmpList.toTypedArray()
                        whatToDoYearDTOList.add(tmpList)
                    }

                    setUpAAChartView(binding.AAChartView2, whatToDoYearDTOList.toTypedArray())

                }
                is UiState.Failure -> {
                    binding.progressCircular.hide()
                    Log.e("experiment", "this " + state.error.toString())
                }
                is UiState.Loading -> {
                    binding.progressCircular.show()
                }
            }
        }
    }



}





