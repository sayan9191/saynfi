package com.realteenpatti.sanify.ui.bottomsheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.airbnb.lottie.LottieAnimationView

import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.realteenpatti.sanify.R

class LuckyDrawBottomSheet(val coins: String) : BottomSheetDialogFragment() {


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view: View = inflater.inflate(R.layout.spinner_wheel_bottom_sheet, container, false)

        val winningText = view.findViewById<TextView>(R.id.luckyDrawWinningText);
        val title = view.findViewById<TextView>(R.id.congoTitle);
        val winningAnimation = view.findViewById<LottieAnimationView>(R.id.winningAnimation);
        val loosingAnimation = view.findViewById<LottieAnimationView>(R.id.loosingAnimation);

        if (coins.toInt() > 0){
            title.text = "Congratulations"
            winningText.text = "You own ${coins.toInt()} coins"
            winningAnimation.visibility = View.VISIBLE
            loosingAnimation.visibility = View.GONE
        } else{
            winningText.text = "Better luck next time"
            title.text = "Oops !"
            winningAnimation.visibility = View.GONE
            loosingAnimation.visibility = View.VISIBLE
        }

        view.setOnClickListener {
            this.dismiss()
        }
        return view
    }
}