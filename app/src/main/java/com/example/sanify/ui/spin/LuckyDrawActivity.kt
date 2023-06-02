package com.example.sanify.ui.spin

import android.annotation.SuppressLint
import android.graphics.BitmapFactory
import android.opengl.Visibility
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bluehomestudio.luckywheel.WheelItem
import com.example.sanify.R
import com.example.sanify.databinding.ActivityLuckyDrawBinding
import com.example.sanify.ui.auth.bottomsheet.LuckyDrawBottomSheet
import com.example.sanify.utils.NetworkUtils
import com.example.sanify.utils.StorageUtil

class LuckyDrawActivity : AppCompatActivity() {

    val textItems = listOf<String>("Better luck next time", "01", "02", "03", "05", "10")
    val colorItems = listOf<Int>(R.color.wheel_color_1, R.color.teal_500, R.color.wheel_color_1,
            R.color.teal_500, R.color.wheel_color_1, R.color.teal_500, R.color.wheel_color_1, R.color.wheel_color_2, R.color.wheel_color_1, R.color.wheel_color_2)

    var targetValue = (1..6).random()
    var total = 0;
    var localStorage = StorageUtil.getInstance()

    lateinit var binding: ActivityLuckyDrawBinding
    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLuckyDrawBinding.inflate(layoutInflater)
        setContentView(binding.root)
        localStorage.sharedPref = getSharedPreferences("sharedPref", MODE_PRIVATE)

        val wheelItems: MutableList<WheelItem> = ArrayList()

        var i = 0
        textItems.forEach{
            wheelItems.add(WheelItem(getColor(colorItems[i]), BitmapFactory.decodeResource(resources, R.drawable.transparent), it))
            i++
        }


        // initially hide the animation
        binding.congoAnimation.visibility = View.GONE

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
            if (NetworkUtils.isOnline(this)){
//                if (localStorage.coins!! < 10){
//                    Toast.makeText(this, "Insufficient balance", Toast.LENGTH_SHORT).show()
//                }else{
//                    localStorage.deductCoin(10)
//                    binding.spinBtn.isClickable = false
//                    binding.lwv.rotateWheelTo(targetValue)
////            binding.playAnimation.isClickable = false
//                    binding.playAnimation.pauseAnimation()
//                    binding.coinAmount.text = localStorage.coins.toString()
//                }
            }else{
                Toast.makeText(this, "Please connect to the internet", Toast.LENGTH_SHORT).show()
            }
        }


        binding.lwv.setLuckyWheelReachTheTarget {
            binding.congoAnimation.visibility = View.VISIBLE

            if (textItems[targetValue-1] != "Better luck next time"){
                total += textItems[targetValue-1].toInt()
//                localStorage.addCoins(textItems[targetValue-1].toInt())
                val bottomSheetLottery = LuckyDrawBottomSheet(textItems[targetValue-1])
                bottomSheetLottery.show(supportFragmentManager, "TAG")
            }

            targetValue = (1..6).random()
            binding.lwv.setTarget(targetValue)
            binding.congoAnimation.playAnimation()
//            binding.playAnimation.isClickable = true
            binding.playAnimation.playAnimation()
            binding.spinBtn.isClickable = true
//            binding.coinAmount.text = localStorage.coins.toString()
        }

//        binding.coinAmount.text = localStorage.coins.toString()


        binding.backBtn.setOnClickListener {
            finish()
        }
    }
}