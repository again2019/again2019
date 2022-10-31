package com.goingbacking.goingbacking.UI.Main.Forth

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.goingbacking.goingbacking.Model.NewSaveTimeMonthDTO
import com.goingbacking.goingbacking.UI.Base.BaseFragment
import com.goingbacking.goingbacking.ViewModel.ForthViewModel
import com.goingbacking.goingbacking.databinding.FragmentForthMain1Binding
import com.goingbacking.goingbacking.util.FBConstants
import com.goingbacking.goingbacking.util.UiState
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class ForthMainFragment1 : BaseFragment<FragmentForthMain1Binding>() {
    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentForthMain1Binding {
        return FragmentForthMain1Binding.inflate(inflater, container, false)
    }


    val viewModel : ForthViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val current = LocalDateTime.now()
        val simpleDate1 = DateTimeFormatter.ofPattern("yyyy-MM")
        val curYearMonth = current.format(simpleDate1)

        val arrayList = ArrayList<NewSaveTimeMonthDTO>()

        FirebaseFirestore.getInstance().collection("RankMonthInfo").document(curYearMonth)
            .collection(curYearMonth).orderBy("count", Query.Direction.DESCENDING).get().addOnSuccessListener { documents ->
                for (document in documents) {
                    arrayList.add(document.toObject(NewSaveTimeMonthDTO::class.java))
                }

                Log.d("experiment", arrayList.toString())
            }

    }
}