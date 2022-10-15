package com.goingbacking.goingbacking.Adapter

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.goingbacking.goingbacking.Model.TmpTimeDTO
import com.goingbacking.goingbacking.WhatToDoSaveActivity
import com.goingbacking.goingbacking.databinding.ItemTmpBinding
import com.google.firebase.firestore.FieldValue
import java.text.SimpleDateFormat

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

            binding.nowSeconds.text = item.nowSeconds.toString()
            binding.startTime.text = item.startTime.toString()
            binding.wakeUpTime.text = item.wakeUpTime.toString()

            val simpleDate1 = SimpleDateFormat("yyyy-MM")
            val simpleDate2 = SimpleDateFormat("dd")
            val simpleDate3 = SimpleDateFormat("yyyy")
            val simpleDate4 = SimpleDateFormat("MM")


            val wakeUpTime1 = simpleDate1.format(item.wakeUpTime!!).toString()
            val wakeUpTime2 = simpleDate2.format(item.wakeUpTime!!).toString()
            val wakeUpTime3 = simpleDate3.format(item.wakeUpTime!!).toString()
            val wakeUpTime4 = simpleDate4.format(item.wakeUpTime!!).toString()

            val count = FieldValue.increment(item.nowSeconds!!.toDouble())

            Log.d("experiment", "wakeUptime: " + wakeUpTime1 + " " + wakeUpTime2)

            binding.saveButton.setOnClickListener {
                onItemClicked.invoke(wakeUpTime1, wakeUpTime2, wakeUpTime3, wakeUpTime4, count, item.nowSeconds!!.toDouble())


            }

        }
    }




}