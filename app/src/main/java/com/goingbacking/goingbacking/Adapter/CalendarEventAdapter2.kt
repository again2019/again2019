package com.goingbacking.goingbacking.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.goingbacking.goingbacking.Model.Event
import com.goingbacking.goingbacking.databinding.ItemEventBinding

class CalendarEventAdapter2() : RecyclerView.Adapter<CalendarEventAdapter2.MyViewHolder>() {

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

        fun bind(event: Event) {
            binding.itemEventText.text = event.dest
        }

    }





}