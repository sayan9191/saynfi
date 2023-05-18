package com.example.sanify.ui.spin

import android.annotation.SuppressLint
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Bundle
import android.view.MotionEvent
import androidx.appcompat.app.AppCompatActivity
import com.bluehomestudio.luckywheel.WheelItem
import com.example.sanify.R
import com.example.sanify.databinding.ActivityLuckyDrawBinding

class LuckyDrawActivity : AppCompatActivity() {

    val textItems = listOf<String>("Better luck next time", "01", "02", "03", "05", "10")
    val colorItems = listOf<Int>(R.color.wheel_color_1, R.color.teal_500, R.color.wheel_color_1,
            R.color.teal_500, R.color.wheel_color_1, R.color.teal_500, R.color.wheel_color_1, R.color.wheel_color_2, R.color.wheel_color_1, R.color.wheel_color_2)

    var targetValue = 2;
    var total = 0;

    lateinit var binding: ActivityLuckyDrawBinding
    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLuckyDrawBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val wheelItems: MutableList<WheelItem> = ArrayList()

        var i = 0
        textItems.forEach{
            wheelItems.add(WheelItem(getColor(colorItems[i]), BitmapFactory.decodeResource(resources, R.drawable.transparent), it))
            i++
        }


        // initial vale
        binding.lwv.addWheelItems(wheelItems)
        binding.lwv.setTarget(targetValue)

        binding.lwv.setOnTouchListener { view, motionEvent ->
            if ( motionEvent.action == MotionEvent.ACTION_DOWN ) {
                //   remove gestureDetector
            } else {
                view.onTouchEvent(motionEvent);
            }
            return@setOnTouchListener true;
        }



        binding.spinBtn.setOnClickListener {
            binding.spinBtn.isClickable = false
            binding.lwv.rotateWheelTo(targetValue)
//            binding.playAnimation.isClickable = false
            binding.playAnimation.pauseAnimation()
        }


        binding.lwv.setLuckyWheelReachTheTarget {
            total += textItems[targetValue-1].toInt()
            targetValue = (1..8).random()
            binding.lwv.setTarget(targetValue)
            binding.congoAnimation.playAnimation()
//            binding.playAnimation.isClickable = true
            binding.playAnimation.playAnimation()
            binding.spinBtn.isClickable = true
        }

    }
}