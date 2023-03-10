package com.goingbacking.goingbacking.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.goingbacking.goingbacking.R
import com.goingbacking.goingbacking.databinding.ItemTodayListBinding
import com.goingbacking.goingbacking.util.calendarSchedule


class TodayRecyclerViewAdapter
    (val context: Context,
     val todayWhatToDo : List<String>,
     val todayWhatToDoTime: List<String>)
    : RecyclerView.Adapter<TodayRecyclerViewAdapter.MyViewHolder>() {

    private lateinit var startHourStr :String
    private lateinit var startMinuteStr :String
    private lateinit var endHourStr :String
    private lateinit var endMinuteStr :String


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



            val startEndItem = todayWhatToDoTimeItem.split('-')
            val startHour = startEndItem[0].toInt() / 60
            if (startHour / 10 == 0) {
                startHourStr = "0" + startHour.toString()
            } else {
                startHourStr = startHour.toString()
            }

            val startMinute = startEndItem[0].toInt() % 60
            if (startMinute / 10 == 0) {
                startMinuteStr = "0" + startMinute.toString()
            } else {
                startMinuteStr = startMinute.toString()
            }

            val endHour = startEndItem[1].toInt() / 60
            if (endHour / 10 == 0) {
                endHourStr = "0" + endHour.toString()
            } else {
                endHourStr = endHour.toString()
            }

            val endMinute = startEndItem[1].toInt() % 60
            if (endMinute / 10 == 0) {
                endMinuteStr = "0" + endMinute.toString()
            } else {
                endMinuteStr = endMinute.toString()
            }

            val current = System.currentTimeMillis()

            val start = calendarSchedule(startHour, startMinute, 0, 0).timeInMillis
            val end = calendarSchedule(endHour, endMinute, 0, 0).timeInMillis

            if (start <= current && end >= current) {
                binding.todayDurationTextView.setTextColor(ContextCompat.getColor(context, R.color.colorMain))
                binding.todayWhatToDoTextView.setTextColor(ContextCompat.getColor(context, R.color.colorMain))
                binding.todayDot.setImageResource(R.drawable.dot4)
            }


            binding.todayDurationTextView.text = String.format("%s:%s ~ %s:%s", startHourStr, startMinuteStr, endHourStr, endMinuteStr)
            binding.todayWhatToDoTextView.text = todayWhatToDoItem
            }
    }

    override fun getItemCount(): Int {
        return todayWhatToDo.size
    }

}