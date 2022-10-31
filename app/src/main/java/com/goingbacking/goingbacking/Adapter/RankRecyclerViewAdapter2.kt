package com.goingbacking.goingbacking.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.goingbacking.goingbacking.Model.NewSaveTimeMonthDTO
import com.goingbacking.goingbacking.Model.NewSaveTimeYearDTO
import com.goingbacking.goingbacking.bottomsheet.RankBottomSheet
import com.goingbacking.goingbacking.databinding.ItemRankingBinding

class RankRecyclerViewAdapter2 (
    val onItemClicked : (String) -> Unit
        ): RecyclerView.Adapter<RankRecyclerViewAdapter2.MyViewHolder>() {
    private var newSaveTimeYearDTOList : ArrayList<NewSaveTimeYearDTO> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = ItemRankingBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = newSaveTimeYearDTOList[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return this.newSaveTimeYearDTOList.size
    }

    inner class MyViewHolder(val binding: ItemRankingBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: NewSaveTimeYearDTO) {

            binding.rankCount.text = item.count.toString()
            binding.rankNickname.text = item.nickname.toString()
            binding.rankType.text = item.type.toString()
            binding.rankWhattodo.text = item.whattodo.toString()
            binding.rankButton.setOnClickListener {
                onItemClicked.invoke(item.uid.toString())
            }
        }

    }





}