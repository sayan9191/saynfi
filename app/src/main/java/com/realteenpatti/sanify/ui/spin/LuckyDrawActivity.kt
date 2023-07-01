package com.realteenpatti.sanify.ui.spin

import android.graphics.Color
import android.media.MediaPlayer
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.realteenpatti.sanify.R
import com.realteenpatti.sanify.databinding.ActivityLuckyDrawBinding
import com.realteenpatti.sanify.ui.bottomsheet.LuckyDrawBottomSheet
import com.realteenpatti.sanify.ui.dialogbox.LoadingScreen
import com.realteenpatti.sanify.utils.NetworkUtils
import com.realteenpatti.sanify.utils.StorageUtil
import rubikstudio.library.model.LuckyItem
import java.util.*


class LuckyDrawActivity : AppCompatActivity() {

    var localStorage = StorageUtil.getInstance()

    var data: ArrayList<LuckyItem> = ArrayList()
    private var coin = 0

    lateinit var binding: ActivityLuckyDrawBinding
    lateinit var viewModel : LuckyDrawActivityViewModel

    val spinnerValues = ArrayList<Int>()
    var mediaPlayer: MediaPlayer? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLuckyDrawBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this)[LuckyDrawActivityViewModel::class.java]
        setContentView(binding.root)
        localStorage.sharedPref = getSharedPreferences("sharedPref", MODE_PRIVATE)

//        val wheelItems: MutableList<WheelItem> = ArrayList()


        // Play Music



        try {
            if (mediaPlayer == null || !mediaPlayer!!.isPlaying())
            mediaPlayer = MediaPlayer.create(this, R.raw.sound_lottery)

            mediaPlayer?.let {
                it.isLooping = true
                it.start();
            }
        } catch (e : Exception){
            e.stackTrace
        }


        // initially hide the animation
        binding.congoAnimation.visibility = View.GONE


        // Initial balance check
        viewModel.getCoinBalance()
        viewModel.getLuckyDrawCoinValues()

        viewModel.spinnerCoins.observe(this) {
            if (it != null){
                spinnerValues.clear()
                it.forEach { coinData ->
                    spinnerValues.add(coinData.coin_value)
                }
                initSpinner()
            }
        }


        viewModel.coinBalance.observe(this) {
            binding.coinAmount.text = it.toString()
        }

        viewModel.errorMessage.observe(this) { s ->
            if (s != "") {
                Toast.makeText(this, s, Toast.LENGTH_SHORT).show()
            }
        }

        viewModel.isLoading.observe(this) { isLoading ->
            if (isLoading) {
                LoadingScreen.showLoadingDialog(this)
            } else {
                try {
                    LoadingScreen.hideLoadingDialog()
                }catch (e : Exception){
                    e.stackTrace
                }
            }
        }

        binding.backBtn.setOnClickListener {
            finish()
        }


        viewModel.isCoinDeducted.observe(this) {
            if (it){
                val index = getRandomIndex()
                binding.luckyWheel.startLuckyWheelWithTargetIndex(index)

                binding.spinBtn.isEnabled = false
                binding.playAnimation.pauseAnimation()
                binding.spinBtn.alpha = .5f
            }
        }

        viewModel.isCoinAdded.observe(this) {
            if (it) {
                val bottomSheetLottery = LuckyDrawBottomSheet(coin)
                bottomSheetLottery.show(supportFragmentManager, "TAG")

                binding.spinBtn.isEnabled = true
                binding.playAnimation.playAnimation()
                binding.spinBtn.alpha = 1f

                binding.congoAnimation.visibility = View.VISIBLE
                binding.congoAnimation.playAnimation()
            }
        }


        binding.luckyWheel.setLuckyRoundItemSelectedListener { index ->
            if (index == 1) {
                coin = spinnerValues[0]
            }
            if (index == 2) {
            coin = spinnerValues[1]
            }
            if (index == 3) {
            coin = spinnerValues[2]
            }
            if (index == 4) {
            coin = spinnerValues[3]
            }
            if (index == 5) {
            coin = spinnerValues[4]
            }
            if (index == 6) {
            coin = spinnerValues[5]
            }
            if (index == 7) {
            coin = spinnerValues[6]
            }
            if (index == 8) {
            coin = spinnerValues[7]
            }


            if (coin == 0){
                val bottomSheetLottery = LuckyDrawBottomSheet(coin)
                bottomSheetLottery.show(supportFragmentManager, "TAG")

                binding.spinBtn.isEnabled = true
                binding.playAnimation.playAnimation()
                binding.spinBtn.alpha = 1f
            }else{
                viewModel.addCoin(coin)
            }
        }

    }

    private fun initSpinner(){
        data.clear()

        val luckyItem1 = LuckyItem()
        luckyItem1.text = spinnerValues[0].toString()
        luckyItem1.color = Color.parseColor("#8574F1")
        data.add(luckyItem1)

        val luckyItem2 = LuckyItem()
        luckyItem2.text = spinnerValues[1].toString()
        luckyItem2.color = Color.parseColor("#8E84FF")
        data.add(luckyItem2)

        val luckyItem3 = LuckyItem()
        luckyItem3.text = spinnerValues[2].toString()
        luckyItem3.color = Color.parseColor("#752BEF")
        data.add(luckyItem3)

        val luckyItem4 = LuckyItem()
        luckyItem4.text = spinnerValues[3].toString()
        luckyItem4.color = ContextCompat.getColor(applicationContext, R.color.Spinwell140)
        data.add(luckyItem4)

        val luckyItem5 = LuckyItem()
        luckyItem5.text = spinnerValues[4].toString()
        luckyItem5.color = Color.parseColor("#8574F1")
        data.add(luckyItem5)

        val luckyItem6 = LuckyItem()
        luckyItem6.text = spinnerValues[5].toString()
        luckyItem6.color = Color.parseColor("#8E84FF")
        data.add(luckyItem6)

        val luckyItem7 = LuckyItem()
        luckyItem7.text = spinnerValues[6].toString()
        luckyItem7.color = Color.parseColor("#752BEF")
        data.add(luckyItem7)

        val luckyItem8 = LuckyItem()
        luckyItem8.text = spinnerValues[7].toString()
        luckyItem8.color = ContextCompat.getColor(applicationContext, R.color.Spinwell140)
        data.add(luckyItem8)

        binding.luckyWheel.setData(data)
        binding.luckyWheel.setRound(getRandomRound())


        binding.spinBtn.setOnClickListener {
            if (NetworkUtils.isOnline(this)){


                viewModel.deductCoin(10);
            }else {
                Toast.makeText(this, "Please Connect to the internet first", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private fun getRandomIndex(): Int {
        val ind = intArrayOf(1, 2, 3, 4, 5, 6, 7, 8)
        var rand: Int = Random().nextInt(ind.size)

        if (ind[rand] >6){
            rand = Random().nextInt(ind.size);
        }

        if (ind[rand] >6){
            rand = Random().nextInt(ind.size);
        }

        if (ind[rand] >6){
            rand = Random().nextInt(ind.size);
        }

        if (ind[rand] >7){
            rand = Random().nextInt(ind.size);
        }


        return ind[rand]
    }

    private fun getRandomRound(): Int {
        val rand = Random()
        return rand.nextInt(10) + 15
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer?.stop()
    }

}