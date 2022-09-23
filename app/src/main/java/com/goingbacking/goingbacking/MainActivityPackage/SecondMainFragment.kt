package com.goingbacking.goingbacking.MainActivityPackage

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.github.aachartmodel.aainfographics.aachartcreator.*
import com.goingbacking.goingbacking.Model.SaveTimeDayDTO
import com.goingbacking.goingbacking.Model.SaveTimeMonthDTO
import com.goingbacking.goingbacking.Model.SaveTimeYearDTO
import com.goingbacking.goingbacking.Model.TmpTimeDTO
import com.goingbacking.goingbacking.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_second_main.*
import kotlinx.android.synthetic.main.fragment_second_main.view.*

class SecondMainFragment : Fragment(), AAChartView.AAChartViewCallBack {
    var auth : FirebaseAuth? = null
    var firebaseFirestore : FirebaseFirestore? = null
    var userId : String? = null
    var saveTimeYearDTO : SaveTimeYearDTO? = null
    var saveTimeYearDTOList = arrayListOf<Int>()

    var saveTimeMonthDTO : SaveTimeMonthDTO? = null
    var saveTimeMonthDTOList = arrayListOf<Int>()

    var saveTimeDayDTO : SaveTimeDayDTO? = null
    var saveTimeDayDTOList = arrayListOf<Int>()

    var aaChartModel = AAChartModel()
    var aaChartView: AAChartView? = null
    var chartType: String = ""

    var aaChartModel2 = AAChartModel()
    var aaChartView2: AAChartView? = null
    var chartType2: String = ""


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_second_main, container, false)

        init()


        FirebaseFirestore.getInstance().collection("SaveTimeInfo").document(userId!!)
            ?.collection("Year")?.addSnapshotListener { querySnapshot, _ ->
                saveTimeYearDTOList.clear()
                if(querySnapshot == null) return@addSnapshotListener
                for(snapshot in querySnapshot!!.documents){

                    Toast.makeText(requireActivity(), snapshot.toObject(SaveTimeYearDTO::class.java)?.count!!.toString(), Toast.LENGTH_SHORT).show()
                    saveTimeYearDTOList.add(snapshot.toObject(SaveTimeYearDTO::class.java)?.count!!)
                }

                setUpAAChartViewYear(view)
            }

        FirebaseFirestore.getInstance().collection("SaveTimeInfo").document(userId!!)
            ?.collection("Month")?.document("2022")
            ?.collection("09")?.addSnapshotListener { querySnapshot, _ -> //"09" 부분을 userId로
                saveTimeMonthDTOList.clear()
                if(querySnapshot == null) return@addSnapshotListener
                for(snapshot in querySnapshot!!.documents){

                    Toast.makeText(requireActivity(), snapshot.toObject(SaveTimeMonthDTO::class.java)?.count!!.toString(), Toast.LENGTH_SHORT).show()
                    saveTimeMonthDTOList.add(snapshot.toObject(SaveTimeMonthDTO::class.java)?.count!!)
                }

                setUpAAChartViewMonth(view)
            }





        return view
    }

    private fun setUpAAChartViewYear(view: View) {
        aaChartView = view.AAChartView1
        aaChartView?.setBackgroundColor(0)
        aaChartView?.callBack = this
        aaChartModel = configureAAChartModel(saveTimeYearDTOList)
        aaChartView?.aa_drawChartWithChartModel(aaChartModel)

    }

    private fun setUpAAChartViewMonth(view: View) {
        aaChartView2 = view.AAChartView2
        aaChartView2?.setBackgroundColor(0)
        aaChartView2?.callBack = this
        aaChartModel2 = configureAAChartModel(saveTimeMonthDTOList)
        aaChartView2?.aa_drawChartWithChartModel(aaChartModel2)

    }

    private fun setUpAAChartViewDay(view: View) {
        aaChartView = view.AAChartView3
        aaChartView?.setBackgroundColor(0)
        aaChartView?.callBack = this
        aaChartModel = configureAAChartModel(saveTimeMonthDTOList)
        aaChartView?.aa_drawChartWithChartModel(aaChartModel)

    }


    private fun configureAAChartModel(DTOList:ArrayList<Int>): AAChartModel {
        chartType = AAChartType.Column.value
        val chartTypeEnum = AAChartType.Column

        Log.d("TTTT", "DTOList" + DTOList.toString())
        val aaChartModel = AAChartModel.Builder(requireActivity())
            .setChartType(chartTypeEnum)
            .setBackgroundColor("#4b2b7f")
            .setDataLabelsEnabled(true)
            .setYAxisGridLineWidth(0f)
            .setLegendEnabled(true)
            .setTouchEventEnabled(true)
            .setSeries(
                AASeriesElement()
                    .name("Tokyo")
                    .data(DTOList.toTypedArray()),


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

    fun init() {
        auth = FirebaseAuth.getInstance()
        firebaseFirestore = FirebaseFirestore.getInstance()
        userId = auth?.currentUser?.uid
        saveTimeYearDTO = SaveTimeYearDTO()
        saveTimeMonthDTO = SaveTimeMonthDTO()
        saveTimeDayDTO = SaveTimeDayDTO()
    }

}