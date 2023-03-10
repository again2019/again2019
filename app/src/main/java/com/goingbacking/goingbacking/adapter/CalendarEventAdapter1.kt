package com.goingbacking.goingbacking.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.goingbacking.goingbacking.model.Event
import com.goingbacking.goingbacking.databinding.ItemEventBinding
import com.goingbacking.goingbacking.util.makeGONE

class CalendarEventAdapter1(val onClick: (String, Int) -> Unit)
    : ListAdapter<Event, CalendarEventAdapter1.MyViewHolder>(diffUtil) {
    inner class MyViewHolder(private val binding: ItemEventBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(event:Event) {
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
                itemView.setOnClickListener {
                    onClick(event.date!!, (event.start)!!.toInt())
                }
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
        val diffUtil=object: DiffUtil.ItemCallback<Event>(){
            override fun areItemsTheSame(oldItem: Event, newItem: Event) :Boolean {
                return oldItem==newItem
            }

            override fun areContentsTheSame(oldItem: Event, newItem: Event) :Boolean {
                return oldItem==newItem
            }
        }
    }
    }

