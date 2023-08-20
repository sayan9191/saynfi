package com.realteenpatti.sanify.ui.bottomsheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Switch
import android.widget.TextView
import com.airbnb.lottie.LottieAnimationView

import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.realteenpatti.sanify.R

class LuckyDrawBottomSheet(val coins: Int, private val winning_horse_id : Int = -1, private val gameTag : String = "horse" ) : BottomSheetDialogFragment() {


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
        val winningHorseText = view.findViewById<TextView>(R.id.winningHorseIdText);

        if (winning_horse_id > 0){
            winningHorseText.visibility = View.VISIBLE
            when(gameTag){
                "horse" ->
                    winningHorseText.text = "Horse number $winning_horse_id has won the race"
                "jm" -> winningHorseText.text = "Card number $winning_horse_id has won"
            }

        }else{
            winningHorseText.visibility = View.GONE
        }

        if (coins > 0){
            title.text = "Congratulations"
            winningText.text = "You own $coins coins"
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