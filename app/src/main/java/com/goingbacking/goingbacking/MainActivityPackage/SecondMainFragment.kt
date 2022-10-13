package com.goingbacking.goingbacking.MainActivityPackage

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.github.aachartmodel.aainfographics.aachartcreator.*
import com.goingbacking.goingbacking.Model.SaveTimeDayDTO
import com.goingbacking.goingbacking.Model.SaveTimeMonthDTO
import com.goingbacking.goingbacking.Model.SaveTimeYearDTO
import com.goingbacking.goingbacking.ViewModel.MainViewModel

import com.goingbacking.goingbacking.databinding.FragmentSecondMainBinding
import com.goingbacking.goingbacking.util.UiState
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
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

    var chartType: String = ""



    lateinit var binding : FragmentSecondMainBinding
    val viewModel: MainViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding = FragmentSecondMainBinding.inflate(layoutInflater)

        init()

//        FirebaseFirestore.getInstance().collection("SaveTimeInfo").document(userId!!)
//            ?.collection("Year")?.addSnapshotListener { querySnapshot, _ ->
//                saveTimeYearDTOList.clear()
//                if(querySnapshot == null) return@addSnapshotListener
//                for(snapshot in querySnapshot!!.documents){
//
//                    Toast.makeText(requireActivity(), snapshot.toObject(SaveTimeYearDTO::class.java)?.count!!.toString(), Toast.LENGTH_SHORT).show()
//                    saveTimeYearDTOList.add(snapshot.toObject(SaveTimeYearDTO::class.java)?.count!!)
//                }
//
//                setUpAAChartView(binding.AAChartView1, saveTimeDayDTOList)
//                //setUpAAChartViewYear(binding)
//            }

        FirebaseFirestore.getInstance().collection("SaveTimeInfo").document(userId!!)
            ?.collection("Month")?.document("2022")
            ?.collection("09")?.addSnapshotListener { querySnapshot, _ -> //"09" 부분을 userId로
                saveTimeMonthDTOList.clear()
                if(querySnapshot == null) return@addSnapshotListener
                for(snapshot in querySnapshot!!.documents){

                    Toast.makeText(requireActivity(), snapshot.toObject(SaveTimeMonthDTO::class.java)?.count!!.toString(), Toast.LENGTH_SHORT).show()
                    saveTimeMonthDTOList.add(snapshot.toObject(SaveTimeMonthDTO::class.java)?.count!!)
                }
                //setUpAAChartView(binding.AAChartView2, saveTimeMonthDTOList)

                //setUpAAChartViewMonth(binding)
            }

        FirebaseFirestore.getInstance().collection("SaveTimeInfo").document(userId!!)
            ?.collection("Day")?.document("2022-09")
            ?.collection("22")?.addSnapshotListener { querySnapshot, _ -> //"22" 2022-09로 바꾸기
                saveTimeDayDTOList.clear()
                if(querySnapshot == null) return@addSnapshotListener
                for(snapshot in querySnapshot!!.documents){

                    Toast.makeText(requireActivity(), snapshot.toObject(SaveTimeMonthDTO::class.java)?.count!!.toString(), Toast.LENGTH_SHORT).show()
                    saveTimeDayDTOList.add(snapshot.toObject(SaveTimeDayDTO::class.java)?.count!!)
                }
                //setUpAAChartView(binding.AAChartView3, saveTimeYearDTOList)
                //setUpAAChartViewDay(binding)
            }


        if (this::binding.isInitialized) {
            return binding.root
        } else {
            binding = FragmentSecondMainBinding.inflate(layoutInflater)
            return binding.root
        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        yearObserver()
    }

    private fun setUpAAChartView(aaCharView: AAChartView, list1: ArrayList<Int>, list2: ArrayList<String>) {
        //aaChartView3 = binding.AAChartView3
        aaCharView?.setBackgroundColor(0)
        aaCharView?.callBack = this
        val aaChartModel = configureAAChartModel(list1, list2)
        aaCharView?.aa_drawChartWithChartModel(aaChartModel)
    }


    private fun configureAAChartModel(DTOList:ArrayList<Int>, categoryList: ArrayList<String>): AAChartModel {
        chartType = AAChartType.Column.value
        val chartTypeEnum = AAChartType.Column

        Log.d("TTTT", "DTOList" + DTOList.toString())
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

    fun init() {
        auth = FirebaseAuth.getInstance()
        firebaseFirestore = FirebaseFirestore.getInstance()
        userId = auth?.currentUser?.uid
        saveTimeYearDTO = SaveTimeYearDTO()
        saveTimeMonthDTO = SaveTimeMonthDTO()
        saveTimeDayDTO = SaveTimeDayDTO()
    }

    fun yearObserver() {
        viewModel.getSecondSaveYearInfo()
        viewModel.secondSaveYearDTOs.observe(viewLifecycleOwner) { state ->
            when(state) {
                is UiState.Success -> {
                    var saveTimeYearDTOList = arrayListOf<Int>()
                    var saveCategoryList = arrayListOf<String>()
                    for (data in state.data) {
                        saveTimeYearDTOList.add(data.count!!)
                        saveCategoryList.add(data.year!!.toString())
                    }
                    setUpAAChartView(binding.AAChartView1, saveTimeYearDTOList, saveCategoryList)

                }
                is UiState.Failure -> {
                    Log.e("experiment", state.error.toString())
                }
            }

        }



    }

}