package com.goingbacking.goingbacking.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.goingbacking.goingbacking.Model.Event
import com.goingbacking.goingbacking.R
import kotlinx.android.synthetic.main.example_3_event_item_view.view.*

class CalendarEventAdapter(val onClick: (Event) -> Unit) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

        var events = mutableListOf<Event>()

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            var view = LayoutInflater.from(parent.context)
                .inflate(R.layout.example_3_event_item_view, parent, false)

            return CustomViewHolder(view)
        }

        inner class CustomViewHolder(view: View) : RecyclerView.ViewHolder(view)

        override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
            var view = (viewHolder as CustomViewHolder).itemView

            view.itemEventText.text = events[position].dest
            view.setOnClickListener { onClick(events[position]) }

        }
        override fun getItemCount(): Int = events.size
    }

