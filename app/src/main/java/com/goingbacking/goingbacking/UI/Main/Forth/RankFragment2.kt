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
import com.goingbacking.goingbacking.ViewModel.RankViewModel
import com.goingbacking.goingbacking.databinding.FragmentRank2Binding
import com.goingbacking.goingbacking.util.UiState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RankFragment2(
    val destinationUid : String
) : BaseFragment<FragmentRank2Binding>(), AAChartView.AAChartViewCallBack {
    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentRank2Binding {
        return FragmentRank2Binding.inflate(inflater, container, false)
    }
    var chartType: String = ""

    val viewModel : RankViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //Log.d("experiment", destinationUid!!)

        yearObserver(destinationUid)
        monthObserver(destinationUid)
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

        val aaChartModel = AAChartModel.Builder(requireActivity())

            .setChartType(chartTypeEnum)
            .setBackgroundColor("#4b2b7f")
            .setDataLabelsEnabled(true)
            .setYAxisGridLineWidth(0f)
            .setLegendEnabled(false)
            .setTouchEventEnabled(true)

            .setSeries(
                AASeriesElement()
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



    fun monthObserver(destinationUid : String) {
        viewModel.getSecondWhatToDoMonthInfo(destinationUid)
        viewModel.secondwhatToDoMonthDTOs.observe(viewLifecycleOwner) { state ->
            when(state) {
                is UiState.Success -> {
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
                    Log.e("experiment", state.error.toString())
                }
            }

        }
    }

    fun yearObserver(destinationUid : String) {
        viewModel.getSecondWhatToDoYearInfo(destinationUid)
        viewModel.secondwhatToDoYearDTOs.observe(viewLifecycleOwner) { state ->
            when(state) {
                is UiState.Success -> {
                    val whatToDoYearDTOList = arrayListOf<Any>()
                    for (data in state.data) {
                        val tmpList = arrayListOf<Any>()
                        tmpList.add(data.whatToDo!!)
                        tmpList.add(data.count!!)
                        tmpList.toTypedArray()
                        whatToDoYearDTOList.add(tmpList)
                    }

                    setUpAAChartView(binding.AAChartView2, whatToDoYearDTOList.toTypedArray())

                }
                is UiState.Failure -> {
                    Log.e("experiment", state.error.toString())
                }
            }
        }
    }


}