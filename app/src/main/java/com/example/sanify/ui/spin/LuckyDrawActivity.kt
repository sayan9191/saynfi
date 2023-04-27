package com.example.sanify.ui.spin

import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bluehomestudio.luckywheel.WheelItem
import com.example.sanify.R
import com.example.sanify.databinding.ActivityLuckyDrawBinding

class LuckyDrawActivity : AppCompatActivity() {

    val textItems = listOf<String>("00", "01", "02", "03", "05", "10", "15", "20")
    val colorItems = listOf<Int>(R.color.wheel_color_1, R.color.wheel_color_2, R.color.wheel_color_1,
            R.color.wheel_color_2, R.color.wheel_color_1, R.color.wheel_color_2, R.color.wheel_color_1, R.color.wheel_color_2, R.color.wheel_color_1, R.color.wheel_color_2)

    var targetValue = 2;
    var total = 0;

    lateinit var binding: ActivityLuckyDrawBinding
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



        binding.lwv.setLuckyWheelReachTheTarget {
            total += textItems[targetValue-1].toInt()
            targetValue = (1..8).random()
            binding.lwv.setTarget(targetValue)
            binding.totalText.text = "Total: ₹ $total"
        }

    }
}