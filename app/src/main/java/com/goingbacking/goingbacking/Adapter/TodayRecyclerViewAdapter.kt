package com.goingbacking.goingbacking.Adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.goingbacking.goingbacking.databinding.ItemTodayListBinding
import kotlin.concurrent.thread

class TodayRecyclerViewAdapter
    (val context: Context,
     val todayWhatToDo : List<String>,
     val todayWhatToDoTime: List<String>)
    : RecyclerView.Adapter<TodayRecyclerViewAdapter.MyViewHolder>() {


    var state = true
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = ItemTodayListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val todayWhatToDoItem = todayWhatToDo[position]
        val todayWhatToDoTimeItem = todayWhatToDoTime[position]
        holder.bind(todayWhatToDoItem, todayWhatToDoTimeItem)
    }

    inner class MyViewHolder(val binding: ItemTodayListBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(todayWhatToDoItem: String, todayWhatToDoTimeItem :String) {

//            thread(state) {
//                while(true) {
//                    val cur = System.currentTimeMillis()
//                    Log.d("experiment", cur.toString())
//
//                    Thread.sleep(1000)
//                }
//            }


            val startEndItem = todayWhatToDoTimeItem.split('-')
            val startHour = startEndItem[0].toInt() / 60
            var startHourStr = ""
            if (startHour / 10 == 0) {
                startHourStr = "0" + startHour.toString()
            } else {
                startHourStr = startHour.toString()
            }

            val startMinute = startEndItem[0].toInt() % 60
            var startMinuteStr = ""
            if (startMinute / 10 == 0) {
                startMinuteStr = "0" + startMinute.toString()
            } else {
                startMinuteStr = startMinute.toString()
            }

            val endHour = startEndItem[1].toInt() / 60
            var endHourStr = ""
            if (endHour / 10 == 0) {
                endHourStr = "0" + endHour.toString()
            } else {
                endHourStr = endHour.toString()
            }

            val endMinute = startEndItem[1].toInt() % 60
            var endMinuteStr = ""
            if (endMinute / 10 == 0) {
                endMinuteStr = "0" + endMinute.toString()
            } else {
                endMinuteStr = endMinute.toString()
            }


            binding.todayDurationTextView.text = startHourStr + ":" + startMinuteStr + " ~ " + endHourStr + ":" + endMinuteStr
            binding.todayWhatToDoTextView.text = todayWhatToDoItem
            }
    }

    override fun getItemCount(): Int {
        return todayWhatToDo.size
    }



}