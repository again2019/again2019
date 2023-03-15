package com.example.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.model.ScheduleModel
import com.example.presentation.databinding.ItemEventBinding
import com.example.domain.util.makeGONE

class CalendarEventAdapter2
    : ListAdapter<ScheduleModel, CalendarEventAdapter2.MyViewHolder>(diffUtil) {
    inner class MyViewHolder(private val binding: ItemEventBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(event:ScheduleModel) {
            val start_hour = event.start!!.toInt() / 60
            val start_minute = event.start!!.toInt() % 60
            val end_hour = event.end!!.toInt() / 60
            val end_minute = event.end!!.toInt() % 60

            binding.startTime.text = String.format("%d시 %d분", start_hour, start_minute)
            binding.endTime.text = String.format("%d시 %d분", end_hour, end_minute)
            if (event.dest.equals("move")) {
                binding.whatTime.text = "통근 시간"
                binding.detailwhatTime.makeGONE()

            } else {
                binding.whatTime.text = "일/공부하는 시간"
                binding.detailwhatTime.text = event.dest
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = ItemEventBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }




    companion object{
        val diffUtil=object: DiffUtil.ItemCallback<ScheduleModel>(){
            override fun areItemsTheSame(oldItem: ScheduleModel, newItem: ScheduleModel) :Boolean {
                return oldItem==newItem
            }

            override fun areContentsTheSame(oldItem: ScheduleModel, newItem: ScheduleModel) :Boolean {
                return oldItem==newItem
            }
        }
    }
}





