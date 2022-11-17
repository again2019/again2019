package com.goingbacking.goingbacking.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.goingbacking.goingbacking.Model.TmpTimeDTO
import com.goingbacking.goingbacking.databinding.ItemTmpBinding
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

//RecyclerView.Adapter<TmpTimeRecyclerViewAdapter.MyViewHolder>()
class TmpTimeRecyclerViewAdapter(
    val onItemClicked: (String, String, String, String, Double, String, String, String, String) -> Unit
): ListAdapter<TmpTimeDTO, TmpTimeRecyclerViewAdapter.MyViewHolder>(diffUtil) {
    //private var tmpTimeDTOList : ArrayList<TmpTimeDTO> = arrayListOf()
    inner class MyViewHolder(private val binding: ItemTmpBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: TmpTimeDTO) {

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
                onItemClicked.invoke(wakeUpTime1, wakeUpTime2, wakeUpTime3, wakeUpTime4, item.nowSeconds!!.toDouble(), String.format("%d시간 %d분", hour, minute), simpleDate1, simpleDate2, simpleDate3)

            }
        }
    }




//    fun updateList(list: ArrayList<TmpTimeDTO>) {
//        this.tmpTimeDTOList = list
//        notifyDataSetChanged()
//    }

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