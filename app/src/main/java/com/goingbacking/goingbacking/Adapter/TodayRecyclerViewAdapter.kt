package com.goingbacking.goingbacking.Adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.goingbacking.goingbacking.databinding.ItemTodayListBinding

class TodayRecyclerViewAdapter
    (val context: Context,
     val todayWhatToDo : List<String>,
     val todayWhatToDoTime: List<String>)
    : RecyclerView.Adapter<TodayRecyclerViewAdapter.MyViewHolder>() {

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
            var startMinute = startEndItem[0].toInt() % 60
            val endHour = startEndItem[1].toInt() / 60
            val endMinute = startEndItem[1].toInt() % 60

            binding.todayDurationTextView.text = "${startHour}:${startMinute}-${endHour}:${endMinute}"
            binding.todayWhatToDoTextView.text = todayWhatToDoItem
            }
    }


    override fun getItemCount(): Int {
        return todayWhatToDo.size
    }



}