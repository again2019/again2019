package com.goingbacking.goingbacking.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.goingbacking.goingbacking.UI.Main.Forth.ForthViewModel
import com.goingbacking.goingbacking.databinding.ItemCheerBinding
import com.goingbacking.goingbacking.util.PrefUtil
import com.goingbacking.goingbacking.util.makeInVisible
import com.goingbacking.goingbacking.util.makeVisible

class CheerRecyclerViewAdapter(
    val onDeleteClick: (String, String) -> Unit) : ListAdapter<String, CheerRecyclerViewAdapter.MyViewHolder>(diffUtil) {
        inner class MyViewHolder(private val binding: ItemCheerBinding) : RecyclerView.ViewHolder(binding.root) {
            fun bind(myUid :String, nickname :String, cheertext: String, original : String) = with(binding) {
                itemCheerNickname.text = nickname
                itemCheer.text = cheertext

                xBtn.setOnClickListener {
                    onDeleteClick(PrefUtil.firebaseUid(), original)
                    val newList = currentList.toMutableList()
                    newList.removeAt(bindingAdapterPosition)
                    submitList(newList)
//                    onDeleteClick(PrefUtil.firebaseUid(), original)
//                    val newList = currentList.toMutableList()
//                    newList.removeAt(bindingAdapterPosition)
//                    submitList(newList)
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


//    companion object{
//        val diffUtil=object: DiffUtil.ItemCallback<String>(){
//            override fun areItemsTheSame(oldItem: String, newItem: String) =
//                oldItem==newItem
//
//
//            override fun areContentsTheSame(oldItem: String, newItem: String) = oldItem==newItem
//
//
//
//        }
//    }
}


//
//
//class CheerRecyclerViewAdapter(
//    val onDeleteClick: (String, String) -> Unit) :
//    RecyclerView.Adapter<CheerRecyclerViewAdapter.MyViewHolder>() {
//    var events = listOf<String>()
//    var hostUid = ""
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
//        val itemView = ItemCheerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
//        return MyViewHolder(itemView)
//    }
//
//    override fun onBindViewHolder(viewHolder: MyViewHolder, position: Int) {
//        val cheer = events[position].split(':')
//
//
//        viewHolder.bind(cheer.get(0), cheer.get(1), cheer.get(2), events[position])
//
//    }
//    override fun getItemCount(): Int = events.size
//
//
//    inner class MyViewHolder(private val binding: ItemCheerBinding) :
//        RecyclerView.ViewHolder(binding.root) {
//
//        fun bind(myUid :String, nickname :String, cheertext: String, original : String) = with(binding) {
//            itemCheerNickname.text = nickname
//            itemCheer.text = cheertext
//            if (myUid.equals(PrefUtil.firebaseUid())) {
//                itemCheerDelete.makeVisible()
//                itemCheerDelete.setOnClickListener {
//                    onDeleteClick(hostUid, original)
//                }
//            } else {
//                itemCheerDelete.makeInVisible()
//            }
//
//
//        }
//
//    }
//
//}