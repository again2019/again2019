package com.goingbacking.goingbacking.MainActivityPackage

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.aachartmodel.aainfographics.aachartcreator.*
import com.goingbacking.goingbacking.R
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_second_main.*
import kotlinx.android.synthetic.main.fragment_second_main.view.*

class SecondMainFragment : Fragment(), AAChartView.AAChartViewCallBack {

    var aaChartModel = AAChartModel()
    var aaChartView: AAChartView? = null
    var chartType: String = ""

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_second_main, container, false)

        aaChartView = view.AAChartView
        aaChartView?.setBackgroundColor(0)
        aaChartView?.callBack = this
        aaChartModel = configureAAChartModel()
        aaChartView?.aa_drawChartWithChartModel(aaChartModel)


        return view
    }



    private fun configureAAChartModel(): AAChartModel {
        chartType = AAChartType.Column.value
        val chartTypeEnum = AAChartType.Column

        val aaChartModel = AAChartModel.Builder(requireActivity())
            .setChartType(chartTypeEnum)
            .setBackgroundColor("#4b2b7f")
            .setDataLabelsEnabled(false)
            .setYAxisGridLineWidth(0f)
            .setLegendEnabled(false)
            .setTouchEventEnabled(true)
            .setSeries(
                AASeriesElement()
                    .name("Tokyo")
                    .data(arrayOf(7.0, 6.9, 9.5, 14.5, 18.2, 21.5, 25.2, 26.5, 23.3, 18.3, 13.9, 9.6)),


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

}