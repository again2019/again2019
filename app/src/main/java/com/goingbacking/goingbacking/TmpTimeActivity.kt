package com.goingbacking.goingbacking

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.goingbacking.goingbacking.Model.TmpTimeDTO
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_tmp_time.*
import kotlinx.android.synthetic.main.item_tmp.view.*
import java.text.SimpleDateFormat

class TmpTimeActivity : AppCompatActivity() {
    var auth : FirebaseAuth? = null
    var firebaseFirestore : FirebaseFirestore? = null
    var userId : String? = null
    var tmpTimeDTO : TmpTimeDTO? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tmp_time)

        init()

        TmprecyclerView.adapter = TmpTimeRecyclerViewAdapter()
        TmprecyclerView.layoutManager = LinearLayoutManager(this)
    }

    inner class TmpTimeRecyclerViewAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
        var tmpTimeDTOList : ArrayList<TmpTimeDTO> = arrayListOf()
        init {

            FirebaseFirestore.getInstance().collection("TmpTimeInfo").document(userId!!).collection(userId!!).addSnapshotListener { querySnapshot, firebaseFirestoreException ->
                tmpTimeDTOList.clear()
                if(querySnapshot == null) return@addSnapshotListener

                for(snapshot in querySnapshot.documents){
                    tmpTimeDTOList.add(snapshot.toObject(TmpTimeDTO::class.java)!!)
                }
                notifyDataSetChanged()
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            var view = LayoutInflater.from(parent.context).inflate(R.layout.item_tmp,parent,false)
            return CustomViewHolder(view)
        }
        inner class CustomViewHolder(view : View) : RecyclerView.ViewHolder(view)

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            var view = (holder as CustomViewHolder).itemView

            val simpleDate = SimpleDateFormat("dd/M/yyyy hh:mm:ss")

            view.nowSeconds.text = simpleDate.format(tmpTimeDTOList[position].nowSeconds).toString()
            view.startTime.text = tmpTimeDTOList[position].startTime.toString()
            view.wakeUpTime.text = simpleDate.format(tmpTimeDTOList[position].wakeUpTime).toString()
            view.saveButton.setOnClickListener {

            }


        }

        override fun getItemCount(): Int {
            return tmpTimeDTOList.size        }

    }



        fun init() {
        auth = FirebaseAuth.getInstance()
        firebaseFirestore = FirebaseFirestore.getInstance()
        userId = auth?.currentUser?.uid
        tmpTimeDTO = TmpTimeDTO()
    }
}