package com.goingbacking.goingbacking.Adapter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.findFragment
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.goingbacking.goingbacking.Model.TmpTimeDTO
import com.goingbacking.goingbacking.R
import com.goingbacking.goingbacking.UI.Main.First.FirstViewModel
import com.goingbacking.goingbacking.bottomsheet.WhatToDoSaveBottomSheet
import com.goingbacking.goingbacking.databinding.ItemTmpBinding
import com.goingbacking.goingbacking.util.UiState
import com.goingbacking.goingbacking.util.toast
import kotlinx.coroutines.NonDisposableHandle.parent
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class TmpTimeRecyclerViewAdapter: ListAdapter<TmpTimeDTO, TmpTimeRecyclerViewAdapter.MyViewHolder>(diffUtil) {

    inner class MyViewHolder(private val binding: ItemTmpBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: TmpTimeDTO) {

            val fm : FragmentManager = (binding.root.context as AppCompatActivity).supportFragmentManager

            val hour = item.nowSeconds!!.toInt() / 60
            val minute = item.nowSeconds!!.toInt() % 60

            binding.nowSeconds.text = String.format("%d시간 %d분", hour, minute)
            val simpleDate1 = SimpleDateFormat("yyyy년 MM월 dd일", Locale.getDefault()).format(item.wakeUpTime)
            val simpleDate2 = SimpleDateFormat("a HH시 mm분", Locale.getDefault()).format(item.startTime)
            val simpleDate3 = SimpleDateFormat("a HH시 mm분", Locale.getDefault()).format(item.wakeUpTime)

            binding.wakeUpDate.text = simpleDate1
            binding.startTime.text = simpleDate2
            binding.wakeUpTime.text = simpleDate3

            val wakeUpTime1 = SimpleDateFormat("yyyy-MM", Locale.getDefault()).format(item.wakeUpTime!!)
            val wakeUpTime2 = SimpleDateFormat("dd", Locale.getDefault()).format(item.wakeUpTime!!)
            val wakeUpTime3 = SimpleDateFormat("yyyy", Locale.getDefault()).format(item.wakeUpTime!!)
            val wakeUpTime4 = SimpleDateFormat("MM", Locale.getDefault()).format(item.wakeUpTime!!)



            binding.saveButton.setOnClickListener {
                val bottom = WhatToDoSaveBottomSheet()
                val bundle = Bundle()
                if (item.nowSeconds!!.toDouble().equals(0.0)) {
                    toast(binding.root.context, binding.root.context.getString(R.string.no_time_input))
                } else {
                    bundle.putDouble("count_double", item.nowSeconds!!.toDouble())
                    bundle.putString("simpleFormat1", String.format("%d시간 %d분", hour, minute))
                    bundle.putString("simpleFormat2", simpleDate1)
                    bundle.putString("simpleFormat3", simpleDate2)
                    bundle.putString("simpleFormat4", simpleDate3)
                    bundle.putString("startTime", item.startTime.toString())

                    bundle.putString("wakeUpTime1", wakeUpTime1)
                    bundle.putString("wakeUpTime2", wakeUpTime2)
                    bundle.putString("wakeUpTime3", wakeUpTime3)
                    bundle.putString("wakeUpTime4", wakeUpTime4)

                    bottom.arguments = bundle
                    bottom.show(fm, bottom.tag)


                    fm.executePendingTransactions()

                    bottom.dialog!!.setOnCancelListener {

                        val newList = currentList.toMutableList()
                        newList.removeAt(bindingAdapterPosition)
                        submitList(newList)
                        it.dismiss()
                    }
                }





            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = ItemTmpBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(itemView)

    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }


    companion object{
        val diffUtil=object: DiffUtil.ItemCallback<TmpTimeDTO>(){
            override fun areItemsTheSame(oldItem: TmpTimeDTO, newItem: TmpTimeDTO) :Boolean {
                return oldItem==newItem
            }

            override fun areContentsTheSame(oldItem: TmpTimeDTO, newItem: TmpTimeDTO) :Boolean {
                return oldItem==newItem
            }
        }
    }
}