package com.example.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.presentation.PrefUtil
import com.example.presentation.databinding.ItemCheerBinding
import com.example.domain.util.makeInVisible

class CheerRecyclerViewAdapter(
    val onDeleteClick: (String) -> Unit) : ListAdapter<String, CheerRecyclerViewAdapter.MyViewHolder>(
    diffUtil
) {
        inner class MyViewHolder(private val binding: ItemCheerBinding) : RecyclerView.ViewHolder(binding.root) {
            fun bind(myUid :String, nickname :String, cheertext: String, original : String) = with(binding) {
                itemCheerNickname.text = nickname
                itemCheer.text = cheertext

                if(!myUid.equals(PrefUtil.getCurrentUid(binding.root.context))) {
                    xBtn.makeInVisible()
                } else {
                xBtn.setOnClickListener {
                    AlertDialog.Builder(binding.root.context)
                        .setMessage("해당 댓글을 삭제하시겠습니까?")
                        .setPositiveButton("삭제하기") { _, _ ->
                            onDeleteClick(original)
                            val newList = currentList.toMutableList()
                            newList.removeAt(bindingAdapterPosition)
                            submitList(newList)
                        }
                        .setNegativeButton("나가기", null)
                        .show()
                    }
                }
//                if (myUid.equals(PrefUtil.firebaseUid())) {
//                    itemCheerDelete.makeVisible()
//                    itemCheerDelete.setOnClickListener {
//                        val newList = currentList.toMutableList()
//                        newList.removeAt(bindingAdapterPosition)
//                        submitList(newList)
//
//                        onDeleteClick(PrefUtil.firebaseUid(), original)
//                    }
//                } else {
//                    itemCheerDelete.makeInVisible()
//                }

            }
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = ItemCheerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(itemView)


    }
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val cheer = currentList[position].split(':')


        holder.bind(cheer.get(0), cheer.get(1), cheer.get(2), currentList[position])
    }

    companion object{
        val diffUtil=object: DiffUtil.ItemCallback<String>(){
            override fun areItemsTheSame(oldItem: String, newItem: String) :Boolean {
                return oldItem==newItem
            }

            override fun areContentsTheSame(oldItem: String, newItem: String) :Boolean {
                return oldItem==newItem
            }
        }
    }
}
