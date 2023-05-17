package com.example.sanify.ui.lottery;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.sanify.adapter.lottery.LotteryNameAdapter;
import com.example.sanify.adapter.lottery.LotteryTicketAdapter;
import com.example.sanify.databinding.ActivityLotteryBuyBinding;
import com.example.sanify.model.lottery.LotteryInformation;
import com.example.sanify.model.lottery.LotteryTicketInformation;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class LotteryBuyFragment extends AppCompatActivity {
    ActivityLotteryBuyBinding binding;
    private CountDownTimer countDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLotteryBuyBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


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

                String countdownText = "CountDown:" +
                        String.format("%02d", hours % 24) + "h " +
                        String.format("%02d", minutes % 60) + "m " +
                        String.format("%02d", seconds % 60) + "s";

                binding.countdownTextView.setText(countdownText);
            }

            @Override
            public void onFinish() {
                binding.countdownTextView.setText("Time Over");
//                binding.buyLotteryBtn.setVisibility(View.GONE);
            }
        };

        countDownTimer.start();


        List<LotteryInformation> items1 = new ArrayList<LotteryInformation>();
        items1.add(new

                LotteryInformation("SAYAN MONDAL", "20154144"));
        items1.add(new

                LotteryInformation("SANTUNU MUKHERJEE", "785555"));
        items1.add(new

                LotteryInformation("Srijan Mukherjee", "98452221"));
        items1.add(new

                LotteryInformation("Bikash Ghosh", "478221235"));
        items1.add(new

                LotteryInformation("Bidhut Sen", "10258686"));
        items1.add(new

                LotteryInformation("Ankan Ghosh", "86574212"));

        List<LotteryInformation> items2 = new ArrayList<LotteryInformation>();
        items2.add(new

                LotteryInformation("Rank 1", "৳1,00,000"));
        items2.add(new

                LotteryInformation("Rank 2", "৳50,000"));
        items2.add(new

                LotteryInformation("Rank 3", "৳10,000"));
        items2.add(new

                LotteryInformation("Rank 4-5", "৳5,000"));
        items2.add(new

                LotteryInformation("Rank 6-15", "৳2,000"));
        items2.add(new

                LotteryInformation("Rank 16-40", "৳1,000"));
        binding.recyclerviewLottery.setLayoutManager(new

                LinearLayoutManager(getApplicationContext()));
        binding.recyclerviewLottery.setAdapter(new

                LotteryNameAdapter(getApplicationContext(), items2));
        binding.WinnerName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.recyclerviewLottery.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                binding.recyclerviewLottery.setAdapter(new LotteryNameAdapter(getApplicationContext(), items1));
            }
        });
        binding.prizePoolBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.recyclerviewLottery.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                binding.recyclerviewLottery.setAdapter(new LotteryNameAdapter(getApplicationContext(), items2));
            }
        });

        List<LotteryTicketInformation> items3 = new ArrayList<LotteryTicketInformation>();
        items3.add(new LotteryTicketInformation("100000"));
        items3.add(new LotteryTicketInformation("100000"));
        items3.add(new LotteryTicketInformation("100000"));
        items3.add(new LotteryTicketInformation("100000"));
        items3.add(new LotteryTicketInformation("100000"));
        items3.add(new LotteryTicketInformation("100000"));
        binding.ticketRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(4, StaggeredGridLayoutManager.VERTICAL));
        binding.ticketRecyclerView.setAdapter(new LotteryTicketAdapter(getApplicationContext(), items3));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
    }
}