package com.goingbacking.goingbacking.Adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.goingbacking.goingbacking.Model.TmpTimeDTO
import com.goingbacking.goingbacking.databinding.ItemTmpBinding
import com.google.firebase.firestore.FieldValue
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class TmpTimeRecyclerViewAdapter(
    val onItemClicked: (String, String, String, String, FieldValue, Double) -> Unit
): RecyclerView.Adapter<TmpTimeRecyclerViewAdapter.MyViewHolder>() {
    private var tmpTimeDTOList : ArrayList<TmpTimeDTO> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = ItemTmpBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(itemView)

    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = tmpTimeDTOList[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return tmpTimeDTOList.size
    }

    fun updateList(list: ArrayList<TmpTimeDTO>) {
        this.tmpTimeDTOList = list
        notifyDataSetChanged()
    }

    inner class MyViewHolder(val binding: ItemTmpBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: TmpTimeDTO) {

            val hour = item.nowSeconds!!.toInt() / 60
            val minute = item.nowSeconds!!.toInt() % 60

            binding.nowSeconds.text = String.format("%d시간 %d분", hour, minute)
            val simpleDate1 = SimpleDateFormat("yyyy년 MM월 dd일", Locale.getDefault()).format(item.wakeUpTime)
            Log.d("experiment", item.startTime.toString() + item.wakeUpTime.toString())
            val simpleDate2 = SimpleDateFormat("a HH시 mm분", Locale.getDefault()).format(item.startTime)
            val simpleDate3 = SimpleDateFormat("a HH시 mm분", Locale.getDefault()).format(item.wakeUpTime)

            binding.wakeUpDate.text = simpleDate1
            binding.startTime.text = simpleDate2
            binding.wakeUpTime.text = simpleDate3

            val wakeUpTime1 = SimpleDateFormat("yyyy-MM", Locale.getDefault()).format(item.wakeUpTime!!)
            val wakeUpTime2 = SimpleDateFormat("dd", Locale.getDefault()).format(item.wakeUpTime!!)
            val wakeUpTime3 = SimpleDateFormat("yyyy", Locale.getDefault()).format(item.wakeUpTime!!)
            val wakeUpTime4 = SimpleDateFormat("MM", Locale.getDefault()).format(item.wakeUpTime!!)

            val count = FieldValue.increment(item.nowSeconds!!.toDouble())


            binding.saveButton.setOnClickListener {
                onItemClicked.invoke(wakeUpTime1, wakeUpTime2, wakeUpTime3, wakeUpTime4, count, item.nowSeconds!!.toDouble())
                
            }
        }
    }
}