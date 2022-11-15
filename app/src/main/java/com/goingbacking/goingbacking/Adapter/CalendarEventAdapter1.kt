package com.goingbacking.goingbacking.Adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.goingbacking.goingbacking.Model.Event
import com.goingbacking.goingbacking.databinding.ItemEventBinding
import com.goingbacking.goingbacking.util.makeGONE

class CalendarEventAdapter1(val onClick: (String, Int) -> Unit)
    : RecyclerView.Adapter<CalendarEventAdapter1.MyViewHolder>() {

        var events = mutableListOf<Event>()

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
            val itemView = ItemEventBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return MyViewHolder(itemView)
        }

        override fun onBindViewHolder(viewHolder: MyViewHolder, position: Int) {
           viewHolder.bind(events[position])

        }
        override fun getItemCount(): Int = events.size


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
    }

