package com.example.presentation.ui.main.forth

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import com.example.domain.util.Constants
import com.example.domain.util.UiState
import com.example.domain.util.makeInVisible
import com.example.presentation.PrefUtil
import com.example.presentation.R
import com.example.presentation.ui.bottomsheet.CheerBottomSheet
import com.example.presentation.databinding.ActivityRank1Binding
import com.github.aachartmodel.aainfographics.aachartcreator.*
import com.example.presentation.ui.base.BaseActivity

import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RankActivity1 : BaseActivity<ActivityRank1Binding>({
   ActivityRank1Binding.inflate(it)
}), AAChartView.AAChartViewCallBack {
    private var likeState = true
    var chartType: String = ""
    val viewModel: RankViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val destinationUid = intent.getStringExtra("destinationUid")!!

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        binding.backbtn.setOnClickListener {
            finish()
        }

        onClick(destinationUid)
        observer1(destinationUid)
        observer2(destinationUid)
        observer3(destinationUid)
    }

    private fun onClick(destinationUid :String) = with(binding) {
        cheer.setOnClickListener {
            val bottom = CheerBottomSheet()
            val bundle = Bundle()
            bundle.putString("destinationUid", destinationUid)
            bottom.arguments = bundle
            bottom.show(supportFragmentManager, bottom.tag)

            supportFragmentManager.executePendingTransactions()

            bottom.dialog!!.setOnDismissListener {
                viewModel.getFifthUserInfo(destinationUid)
                viewModel.userInfoDTO.observe(this@RankActivity1) { state ->
                    when (state) {
                        is UiState.Success -> {
                            progressCircular.hide()
                            cheerCount.text = state.data.cheers.size.toString()
                        }
                        is UiState.Failure -> {
                            progressCircular.hide()

                        }
                        is UiState.Loading -> {
                            progressCircular.show()

                        }
                    }
                }
            }
            }


        likeButton.setOnClickListener {
            if (likeState == false) {
                likeButton.setMinAndMaxProgress(0f, 1f)
                likeButton.playAnimation()
                likeState = true

                viewModel.likeButtonInfo(destinationUid, "plus")


                viewModel.likeButtonInfo.observe(this@RankActivity1) { state ->
                    when(state) {
                        is UiState.Success -> {
                            progressCircular.hide()
                            likeCount.text = state.data
                        }
                        is UiState.Failure -> {
                            progressCircular.hide()
                        }
                        is UiState.Loading -> {
                            progressCircular.show()
                        }
                    }

                }
            } else {
                likeButton.setMinAndMaxProgress(0f, 0f)
                likeButton.playAnimation()
                likeState = false

                viewModel.likeButtonInfo(destinationUid, "minus")
                viewModel.likeButtonInfo.observe(this@RankActivity1) { state ->
                    when(state) {
                        is UiState.Success -> {
                            progressCircular.hide()
                            likeCount.text = state.data
                        }
                        is UiState.Failure -> {
                            progressCircular.hide()
                        }
                        is UiState.Loading -> {
                            progressCircular.show()
                        }
                    }

                }
            }
        }


    }

    private fun observer1(destinationUid: String) = with(binding) {
        viewModel.getFifthUserInfo(destinationUid)
        viewModel.userInfoDTO.observe(this@RankActivity1) { state ->
            when(state) {
                is UiState.Success -> {
                    progressCircular.hide()
                    likeCount.text = state.data.likes.size.toString()
                    cheerCount.text = state.data.cheers.size.toString()

                    val likes = state.data.likes
                    if (likes.contains(PrefUtil.getCurrentUid(this@RankActivity1))) {
                        likeButton.setMinAndMaxProgress(1f, 1f)
                        likeButton.playAnimation()
                        likeState = true
                    } else {
                        likeButton.setMinAndMaxProgress(0f,0f)
                        likeButton.playAnimation()
                        likeState = false
                    }
                    nickName.text = state.data.userNickName
                    type.text = state.data.userType
                    val whattodo = state.data.whatToDoList
                    if (whattodo.size == 1) {
                        chip1.text = whattodo.get(0)
                        chip2.makeInVisible()
                        chip3.makeInVisible()
                    } else if (whattodo.size == 2) {
                        chip1.text = whattodo.get(0)
                        chip2.text = whattodo.get(1)
                        chip3.makeInVisible()
                    } else if (whattodo.size == 3) {
                        chip1.text = whattodo.get(0)
                        chip2.text = whattodo.get(1)
                        chip3.text = whattodo.get(2)
                    }

                }
                is UiState.Failure -> {
                    progressCircular.hide()

                }
                is UiState.Loading -> {
                    progressCircular.show()

                }
            }
        }
    }

    private fun observer2(destinationUid: String) {
        viewModel.getSecondSaveDayInfo(destinationUid)
        viewModel.secondSaveDayDTOs.observe(this) { state ->
            when(state) {
                is UiState.Success -> {
                    binding.progressCircular.hide()
                    val saveTimeDayDTOList = arrayListOf<Int>()
                    val saveCategoryList3 = arrayListOf<String>()
                    for (data in state.data) {
                        saveTimeDayDTOList.add(data.count!!)
                        saveCategoryList3.add(data.day!!.toString())
                    }

                    setUpAAChartView1(binding.AAChartView1, saveTimeDayDTOList, saveCategoryList3)

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

    private fun observer3(destinationUid: String) {
        viewModel.getSecondWhatToDoMonthInfo(destinationUid)
        viewModel.secondwhatToDoMonthDTOs.observe(this) { state ->
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

                    setUpAAChartView2(binding.AAChartView2, whatToDoMonthDTOList.toTypedArray())

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

    private fun setUpAAChartView1(aaCharView: AAChartView, list1: ArrayList<Int>, list2: ArrayList<String>) {
        aaCharView.setBackgroundColor(0)
        aaCharView.callBack = this
        val aaChartModel = configureAAChartModel1(list1, list2)
        aaCharView.aa_drawChartWithChartModel(aaChartModel)
    }

    private fun configureAAChartModel1(DTOList:ArrayList<Int>, categoryList: ArrayList<String>): AAChartModel {
        chartType = AAChartType.Column.value
        val chartTypeEnum = AAChartType.Column

        Constants.colorArray.shuffle()
        val aaChartModel = AAChartModel.Builder(this)

            .setChartType(chartTypeEnum)
            .setBackgroundColor(R.color.white)
            .setDataLabelsEnabled(true)
            .setYAxisLabelsEnabled(false)
            .setYAxisGridLineWidth(0f)
            .setYAxisTitle("")
            .setAxesTextColorRes(R.color.titleMain)
            .setColorsTheme(Constants.colorArray)
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


    private fun setUpAAChartView2(aaCharView: AAChartView, array: Array<Any>) {
        aaCharView.setBackgroundColor(0)
        aaCharView.callBack = this
        val aaChartModel = configureAAChartModel2(array)
        aaCharView.aa_drawChartWithChartModel(aaChartModel)
    }
    private fun configureAAChartModel2(array: Array<Any>): AAChartModel {
        chartType = AAChartType.Pie.value
        val chartTypeEnum = AAChartType.Pie

        val aaChartModel = AAChartModel.Builder(this)

            .setChartType(chartTypeEnum)
            .setBackgroundColor(R.color.white)
            .setDataLabelsEnabled(true)
            .setAxesTextColorRes(R.color.titleMain)
            .setColorsTheme(Constants.colorArray)
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
}