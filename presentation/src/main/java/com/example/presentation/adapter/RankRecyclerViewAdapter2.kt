package com.example.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.model.SavedTimeAboutRankModel
import com.example.presentation.databinding.ItemRankingBinding

class RankRecyclerViewAdapter2 (
    val onItemClicked : (String) -> Unit
        ): ListAdapter<SavedTimeAboutRankModel, RankRecyclerViewAdapter2.MyViewHolder>(diffUtil) {

    inner class MyViewHolder(val binding: ItemRankingBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: SavedTimeAboutRankModel, position: Int) = with(binding) {
            rankNum.text = (position+1).toString()

            val hour = item.count!!.toInt() / 60
            val minute = item.count.toInt() % 60

            rankCount.text = String.format("%d시간 %d분", hour, minute)

            rankNickname.text = item.nickname.toString()
            itemView.setOnClickListener {
                onItemClicked.invoke(item.uid.toString())
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = ItemRankingBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = getItem(position)
        if (position == 0)  {
            holder.bind(item, position)
        } else {
            var beforeNum = position-1
            var first = true

            while(true) {
                if (getItem(position).count!!.equals(getItem(beforeNum).count!!)) {
                    beforeNum = beforeNum - 1
                    first = false
                    if (beforeNum == -1) {
                        beforeNum = -1
                        break
                    }
                } else {

                    break
                }
            }

            if (first) {
                holder.bind(item, position)
            } else {
                holder.bind(item, beforeNum+1)
            }

        }
    }

    companion object{
        val diffUtil=object: DiffUtil.ItemCallback<SavedTimeAboutRankModel>(){
            override fun areItemsTheSame(oldItem: SavedTimeAboutRankModel, newItem: SavedTimeAboutRankModel) :Boolean {
                return oldItem==newItem
            }

            override fun areContentsTheSame(oldItem: SavedTimeAboutRankModel, newItem: SavedTimeAboutRankModel) :Boolean {
                return oldItem==newItem
            }
        }
    }





}