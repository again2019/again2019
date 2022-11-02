package com.goingbacking.goingbacking.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.goingbacking.goingbacking.databinding.ItemCheerBinding

class CheerRecyclerViewAdapter() : RecyclerView.Adapter<CheerRecyclerViewAdapter.MyViewHolder>() {
    var events = listOf<String>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = ItemCheerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(viewHolder: MyViewHolder, position: Int) {
        viewHolder.bind(events[position])

    }
    override fun getItemCount(): Int = events.size


    inner class MyViewHolder(private val binding: ItemCheerBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(cheer: String) {
           binding.itemCheer.text = cheer
        }

    }

}