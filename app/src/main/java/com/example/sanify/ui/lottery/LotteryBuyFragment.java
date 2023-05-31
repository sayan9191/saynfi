package com.example.sanify.ui.lottery;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sanify.MainActivity;
import com.example.sanify.adapter.lottery.YourTicketsAdapter;
import com.example.sanify.adapter.lottery.ResultFragment;
import com.example.sanify.databinding.ActivityLotteryBuyBinding;
import com.example.sanify.model.lottery.LotteryTicketInformation;
import com.example.sanify.ui.auth.bottomsheet.BottomSheetLottery;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class LotteryBuyFragment extends AppCompatActivity {
    ActivityLotteryBuyBinding binding;
    private CountDownTimer countDownTimer;
    public String lotteryNo;

    private YourTicketsAdapter lotteryAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLotteryBuyBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        List<LotteryTicketInformation> items3 = new ArrayList<LotteryTicketInformation>();

        long currentTimeMillis = System.currentTimeMillis();
        long ninePmMillis;
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 22); // Set the hour to 9 p.m.
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        ninePmMillis = calendar.getTimeInMillis();

        // Calculate the remaining time in milliseconds
        long remainingMillis = ninePmMillis - currentTimeMillis;

        countDownTimer = new CountDownTimer(remainingMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                long seconds = millisUntilFinished / 1000;
                long minutes = seconds / 60;
                long hours = minutes / 60;
                long days = hours / 24;

                String countdownText = "Lottery closes at: " +
                        String.format("%02d", hours % 24) + "h " +
                        String.format("%02d", minutes % 60) + "m " +
                        String.format("%02d", seconds % 60) + "s";

                binding.countdownTextView.setText(countdownText);
            }

            @Override
            public void onFinish() {
                binding.countdownTextView.setText("Congratulations Winners");
            }
        };

        countDownTimer.start();

        binding.buyLotteryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BottomSheetLottery bottomSheetLottery = new BottomSheetLottery();
                bottomSheetLottery.show(getSupportFragmentManager(), "TAG");
                lotteryNo = String.valueOf(ThreadLocalRandom.current().nextInt(100000, 999999));
                items3.add(new LotteryTicketInformation(lotteryNo));
                lotteryAdapter.updateList(items3);
                if (items3.size() > 0) {
//                    binding.ticketNoView.setVisibility(View.VISIBLE);
                }
            }
        });

        //YourTicket
        binding.yourTicketsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                getSupportFragmentManager().beginTransaction()
//                        .setReorderingAllowed(true)
//                        .addToBackStack("Home")
//                        .replace(R.id.fragmentContainerView, new YourTicketsFragment())
//                        .commit();
            }
        });

        //

        binding.resultBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LotteryBuyFragment.this, ResultFragment.class);
                startActivity(intent);
            }
        });

        binding.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LotteryBuyFragment.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
    }
}