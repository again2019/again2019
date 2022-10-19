package com.goingbacking.goingbacking.UI.Main.Second

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.github.aachartmodel.aainfographics.aachartcreator.*
import com.goingbacking.goingbacking.ViewModel.MainViewModel
import com.goingbacking.goingbacking.databinding.FragmentSecondMain2Binding
import com.goingbacking.goingbacking.util.UiState
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SecondMainFragment2 : Fragment(), AAChartView.AAChartViewCallBack {
    var chartType: String = ""

    lateinit var binding : FragmentSecondMain2Binding
    val viewModel: MainViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSecondMain2Binding.inflate(layoutInflater)

        if (this::binding.isInitialized) {
            return binding.root
        } else {
            binding = FragmentSecondMain2Binding.inflate(layoutInflater)
            return binding.root
        }
    }
    fun newInstance() : SecondMainFragment2 {
        val args = Bundle()
        val frag = SecondMainFragment2()
        frag.arguments = args

        return frag
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        yearObserver()
        monthObserver()
    }

    private fun setUpAAChartView(aaCharView: AAChartView, array: Array<Any>) {
        aaCharView?.setBackgroundColor(0)
        aaCharView?.callBack = this
        val aaChartModel = configureAAChartModel(array)
        aaCharView?.aa_drawChartWithChartModel(aaChartModel)
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



    fun monthObserver() {
        viewModel.getSecondWhatToDoMonthInfo()
        viewModel.secondwhatToDoMonthDTOs.observe(viewLifecycleOwner) { state ->
            when(state) {
                is UiState.Success -> {
                    var whatToDoMonthDTOList = arrayListOf<Any>()
                    for (data in state.data) {
                        var tmpList = arrayListOf<Any>()
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

    fun yearObserver() {
        viewModel.getSecondWhatToDoYearInfo()
        viewModel.secondwhatToDoYearDTOs.observe(viewLifecycleOwner) { state ->
            when(state) {
                is UiState.Success -> {
                    var whatToDoYearDTOList = arrayListOf<Any>()
                    for (data in state.data) {
                        var tmpList = arrayListOf<Any>()
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





