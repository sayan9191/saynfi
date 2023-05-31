package com.example.sanify.ui.auth.bottomsheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.sanify.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class LuckyDrawBottomSheet(val coins: String) : BottomSheetDialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view: View = inflater.inflate(R.layout.spinner_wheel_bottom_sheet, container, false)

        val winningText = view.findViewById<TextView>(R.id.luckyDrawWinningText);

        winningText.setText("You own ${coins.toInt()} coins")

        view.setOnClickListener {
            this.dismiss()
        }
        return view
    }
}