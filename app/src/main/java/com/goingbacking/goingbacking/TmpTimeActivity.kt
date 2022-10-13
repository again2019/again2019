package com.goingbacking.goingbacking

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.goingbacking.goingbacking.Adapter.TmpTimeRecyclerViewAdapter
import com.goingbacking.goingbacking.Model.SaveTimeDayDTO
import com.goingbacking.goingbacking.Model.TmpTimeDTO
import com.goingbacking.goingbacking.ViewModel.MainViewModel
import com.goingbacking.goingbacking.ViewModel.TmpTimeViewModel
import com.goingbacking.goingbacking.databinding.ActivityTmpTimeBinding
import com.goingbacking.goingbacking.util.UiState
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.item_tmp.view.*
import java.text.SimpleDateFormat

@AndroidEntryPoint
class TmpTimeActivity : AppCompatActivity() {
    var auth : FirebaseAuth? = null
    var firebaseFirestore : FirebaseFirestore? = null
    var userId : String? = null
    var tmpTimeDTO : TmpTimeDTO? = null

    var tmpTimeDTOList : ArrayList<TmpTimeDTO> = arrayListOf()
    private val binding : ActivityTmpTimeBinding by lazy {
        ActivityTmpTimeBinding.inflate(layoutInflater)
    }

    val adapter by lazy {
        TmpTimeRecyclerViewAdapter()
    }
    val viewModel : TmpTimeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        TmpTimeOberver()
        init()
        binding.TmprecyclerView.layoutManager = LinearLayoutManager(this)
        binding.TmprecyclerView.adapter = adapter


    }

//    inner class TmpTimeRecyclerViewAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
//        init {
//
//            TmpTimeOberver()
//            notifyDataSetChanged()
//        }
//
//        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
//            var view = LayoutInflater.from(parent.context).inflate(R.layout.item_tmp,parent,false)
//            return MyViewHolder(view)
//        }
//        inner class MyViewHolder(view : View) : RecyclerView.ViewHolder(view)
//
//        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
//            var view = (holder as MyViewHolder).itemView

//            val simpleDate = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
//            val simpleDate2 = SimpleDateFormat("yyyy-MM")
//            val simpleDate3 = SimpleDateFormat("dd")
//            val simpleDate4 = SimpleDateFormat("MM")
//            val simpleDate5 = SimpleDateFormat("yyyy")
//            view.nowSeconds.text = tmpTimeDTOList[position].nowSeconds.toString()
//            view.startTime.text = simpleDate.format(tmpTimeDTOList[position].startTime).toString()
//            view.wakeUpTime.text = simpleDate.format(tmpTimeDTOList[position].wakeUpTime).toString()
//            view.saveButton.setOnClickListener {
//
//
//                var saveTimeDayDTO : SaveTimeDayDTO? = SaveTimeDayDTO()
//                saveTimeDayDTO!!.count = tmpTimeDTOList[position].nowSeconds?.toInt()
//                saveTimeDayDTO!!.day = simpleDate3.format(tmpTimeDTOList[position].wakeUpTime).toInt()
//                saveTimeDayDTO!!.month = simpleDate4.format(tmpTimeDTOList[position].wakeUpTime).toInt()
//                saveTimeDayDTO!!.year = simpleDate5.format(tmpTimeDTOList[position].wakeUpTime).toInt()
//
//                firebaseFirestore?.collection("SaveTimeInfo")?.document(userId!!)
//                    ?.collection("Day")?.document(simpleDate2.format(tmpTimeDTOList[position].wakeUpTime).toString())
//                    ?.collection(simpleDate3.format(tmpTimeDTOList[position].wakeUpTime).toString())?.document(userId!! + simpleDate3.format(tmpTimeDTOList[position].wakeUpTime).toString())
//                    ?.update("count", FieldValue.increment(tmpTimeDTOList[position].nowSeconds?.toDouble()!!))
//
//                firebaseFirestore?.collection("SaveTimeInfo")?.document(userId!!)
//                    ?.collection("Month")?.document(simpleDate5.format(tmpTimeDTOList[position].wakeUpTime).toString())
//                    ?.collection(simpleDate4.format(tmpTimeDTOList[position].wakeUpTime).toString())?.document(userId!! + simpleDate4.format(tmpTimeDTOList[position].wakeUpTime).toString())
//                    ?.update("count", FieldValue.increment(tmpTimeDTOList[position].nowSeconds?.toDouble()!!))
//
//
//                firebaseFirestore?.collection("SaveTimeInfo")?.document(userId!!)
//                    ?.collection("Year")?.document(simpleDate5.format(tmpTimeDTOList[position].wakeUpTime).toString())
//                    ?.update("count", FieldValue.increment(tmpTimeDTOList[position].nowSeconds?.toDouble()!!))
//
//            }


//        }

//        override fun getItemCount(): Int {
//            return tmpTimeDTOList.size        }
//
//    }



        fun init() {
        auth = FirebaseAuth.getInstance()
        firebaseFirestore = FirebaseFirestore.getInstance()
        userId = auth?.currentUser?.uid
        tmpTimeDTO = TmpTimeDTO()
    }

    private fun TmpTimeOberver(){
        viewModel.getTmpTimeInfo()
        viewModel.tmpTimeDTOs.observe(this) { state ->
            when(state){
                is UiState.Success -> {
                    adapter.updateList(state.data)
                    Log.d("experiment", state.data.toString())
                }
                is UiState.Failure -> {
                }

            }
        }
    }

}