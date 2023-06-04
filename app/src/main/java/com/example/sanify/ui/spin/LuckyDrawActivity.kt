package com.example.sanify.ui.spin

import android.R
import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.bluehomestudio.luckywheel.WheelItem
import com.example.sanify.databinding.ActivityLuckyDrawBinding
import com.example.sanify.ui.auth.bottomsheet.LuckyDrawBottomSheet
import com.example.sanify.utils.NetworkUtils
import com.example.sanify.utils.StorageUtil
import rubikstudio.library.model.LuckyItem
import java.util.*


class LuckyDrawActivity : AppCompatActivity() {

    var localStorage = StorageUtil.getInstance()

    var data: ArrayList<LuckyItem> = ArrayList()
    private var coin = 0

    lateinit var binding: ActivityLuckyDrawBinding
    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLuckyDrawBinding.inflate(layoutInflater)
        setContentView(binding.root)
        localStorage.sharedPref = getSharedPreferences("sharedPref", MODE_PRIVATE)

        val wheelItems: MutableList<WheelItem> = ArrayList()


        // initially hide the animation
        binding.congoAnimation.visibility = View.GONE




        /*binding.spinBtn.setOnClickListener {
            if (NetworkUtils.isOnline(this)){

            }else{
                Toast.makeText(this, "Please connect to the internet", Toast.LENGTH_SHORT).show()
            }
        }*/


        binding.backBtn.setOnClickListener {
            finish()
        }


        val luckyItem1 = LuckyItem()
        luckyItem1.text = "0"
        luckyItem1.color = Color.parseColor("#8574F1")
        data.add(luckyItem1)

        val luckyItem2 = LuckyItem()
        luckyItem2.text = "20"
        luckyItem2.color = Color.parseColor("#8E84FF")
        data.add(luckyItem2)

        val luckyItem3 = LuckyItem()
        luckyItem3.text = "40"
        luckyItem3.color = Color.parseColor("#752BEF")
        data.add(luckyItem3)

        val luckyItem4 = LuckyItem()
        luckyItem4.text = "60"
        luckyItem4.color = ContextCompat.getColor(applicationContext, com.example.sanify.R.color.Spinwell140)
        data.add(luckyItem4)

        val luckyItem5 = LuckyItem()
        luckyItem5.text = "80"
        luckyItem5.color = Color.parseColor("#8574F1")
        data.add(luckyItem5)

        val luckyItem6 = LuckyItem()
        luckyItem6.text = "100"
        luckyItem6.color = Color.parseColor("#8E84FF")
        data.add(luckyItem6)

        val luckyItem7 = LuckyItem()
        luckyItem7.text = "120"
        luckyItem7.color = Color.parseColor("#752BEF")
        data.add(luckyItem7)

        val luckyItem8 = LuckyItem()
        luckyItem8.text = "140"
        luckyItem8.color = ContextCompat.getColor(applicationContext, com.example.sanify.R.color.Spinwell140)
        data.add(luckyItem8)

        binding.luckyWheel.setData(data)
        binding.luckyWheel.setRound(getRandomRound())


        binding.spinBtn.setOnClickListener {
            val index = getRandomIndex()
            binding.luckyWheel.startLuckyWheelWithTargetIndex(index)

            binding.spinBtn.isEnabled = false
            binding.playAnimation.pauseAnimation()
            binding.spinBtn.alpha = .5f

        }


        binding.luckyWheel.setLuckyRoundItemSelectedListener { index ->
            if (index == 1) {
                coin = 0
            }
            if (index == 2) {
            coin = 20
            }
            if (index == 3) {
            coin = 40
            }
            if (index == 4) {
            coin = 60
            }
            if (index == 5) {
            coin = 80
            }
            if (index == 6) {
            coin = 100
            }
            if (index == 7) {
            coin = 120
            }
            if (index == 8) {
            coin = 140
            }

            Toast.makeText(applicationContext, "+ $coin Coins", Toast.LENGTH_SHORT).show()

            val bottomSheetLottery = LuckyDrawBottomSheet(coin.toString())
            bottomSheetLottery.show(supportFragmentManager, "TAG")

            binding.spinBtn.isEnabled = true
            binding.playAnimation.playAnimation()
            binding.spinBtn.alpha = 1f

            binding.congoAnimation.visibility = View.VISIBLE
            binding.congoAnimation.playAnimation()

        }

    }

    private fun getRandomIndex(): Int {
        val ind = intArrayOf(1, 2, 3, 4, 5, 6, 7, 8)
        val rand: Int = Random().nextInt(ind.size)
        return ind[rand]
    }

    private fun getRandomRound(): Int {
        val rand = Random()
        return rand.nextInt(10) + 15
    }

}