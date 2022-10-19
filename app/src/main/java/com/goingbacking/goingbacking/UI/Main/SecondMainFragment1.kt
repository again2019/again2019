package com.goingbacking.goingbacking.UI.Main

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.github.aachartmodel.aainfographics.aachartcreator.*
import com.goingbacking.goingbacking.ViewModel.MainViewModel
import com.goingbacking.goingbacking.databinding.FragmentSecondMain1Binding
import com.goingbacking.goingbacking.util.UiState
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SecondMainFragment1 : Fragment(), AAChartView.AAChartViewCallBack {
    var chartType: String = ""

    lateinit var binding: FragmentSecondMain1Binding
    val viewModel: MainViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSecondMain1Binding.inflate(layoutInflater)


        if (this::binding.isInitialized) {
            return binding.root
        } else {
            binding = FragmentSecondMain1Binding.inflate(layoutInflater)
            return binding.root
        }    }

    fun newInstance() : SecondMainFragment1 {
        val args = Bundle()
        val frag = SecondMainFragment1()
        frag.arguments = args

        return frag
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        yearObserver()
        monthObserver()
        dayObserver()
    }

    private fun setUpAAChartView(aaCharView: AAChartView, list1: ArrayList<Int>, list2: ArrayList<String>) {
        aaCharView?.setBackgroundColor(0)
        aaCharView?.callBack = this
        val aaChartModel = configureAAChartModel(list1, list2)
        aaCharView?.aa_drawChartWithChartModel(aaChartModel)
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

        aaChartModel!!.categories(categoryList.toTypedArray())

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
                    var saveTimeYearDTOList = arrayListOf<Int>()
                    var saveCategoryList1 = arrayListOf<String>()
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
                    var saveTimeMonthDTOList = arrayListOf<Int>()
                    var saveCategoryList2 = arrayListOf<String>()
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
                    var saveTimeDayDTOList = arrayListOf<Int>()
                    var saveCategoryList3 = arrayListOf<String>()
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