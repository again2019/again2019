package com.goingbacking.goingbacking.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.goingbacking.goingbacking.Model.TmpTimeDTO
import com.goingbacking.goingbacking.databinding.ItemTmpBinding

class TmpTimeRecyclerViewAdapter(): RecyclerView.Adapter<TmpTimeRecyclerViewAdapter.MyViewHolder>() {
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
            binding.saveButton.setOnClickListener {


                 }

        }
    }


}