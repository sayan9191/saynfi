package com.example.sanify.ui.lottery;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.sanify.R;
import com.example.sanify.adapter.lottery.LotteryNameAdapter;
import com.example.sanify.adapter.lottery.LotteryTicketAdapter;
import com.example.sanify.databinding.ActivityLotteryBuyBinding;
import com.example.sanify.model.lottery.LotteryInformation;
import com.example.sanify.model.lottery.LotteryTicketInformation;
import com.example.sanify.ui.auth.bottomsheet.BottomSheetLottery;
import com.example.sanify.ui.auth.bottomsheet.BottomSheetPP;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class LotteryBuyFragment extends AppCompatActivity {
    ActivityLotteryBuyBinding binding;
    private CountDownTimer countDownTimer;
    public String lotteryNo;

    private LotteryTicketAdapter lotteryAdapter;

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

                String countdownText = "CountDown:" +
                        String.format("%02d", hours % 24) + "h " +
                        String.format("%02d", minutes % 60) + "m " +
                        String.format("%02d", seconds % 60) + "s";

                binding.countdownTextView.setText(countdownText);
            }

            @Override
            public void onFinish() {
                binding.countdownTextView.setText("Time Over");
            }
        };

        countDownTimer.start();


        List<LotteryInformation> items1 = new ArrayList<LotteryInformation>();
        items1.add(new

                LotteryInformation("Rank 1", "20154144"));
        items1.add(new

                LotteryInformation("Rank 2", "785555"));
        items1.add(new

                LotteryInformation("Rank 3", "984522"));
        items1.add(new

                LotteryInformation("Rank 4", "478221"));
        items1.add(new

                LotteryInformation("Rank 5", "10258686"));
        items1.add(new LotteryInformation("Rank 7", "804212"));
        items1.add(new LotteryInformation("Rank 8", "985412"));
        items1.add(new LotteryInformation("Rank 9", "001057"));
        items1.add(new LotteryInformation("Rank 10", "122144"));
        items1.add(new LotteryInformation("Rank 11", "5426550"));
        items1.add(new LotteryInformation("Rank 12", "4877449"));
        items1.add(new LotteryInformation("Rank 13", "9876541"));
        items1.add(new LotteryInformation("Rank 14", "3201458"));
        items1.add(new LotteryInformation("Rank 15", "98745655"));
        items1.add(new LotteryInformation("Rank 16", "21025448"));
        items1.add(new LotteryInformation("Rank 17", "89785212"));
        items1.add(new LotteryInformation("Rank 18", "478210205"));
        items1.add(new LotteryInformation("Rank 19", "545545444"));
        items1.add(new LotteryInformation("Rank 20", "822555554"));
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

        // Set adapter
        binding.ticketRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(4, StaggeredGridLayoutManager.VERTICAL));
        lotteryAdapter = new LotteryTicketAdapter(getApplicationContext());
        binding.ticketRecyclerView.setAdapter(lotteryAdapter);

        binding.WinnerName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.prizePoolBtn.setBackgroundTintList(getResources().getColorStateList(R.color.unselect_color));
                binding.WinnerName.setBackgroundTintList(getResources().getColorStateList(R.color.select_color));
                binding.recyclerviewLottery.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                binding.recyclerviewLottery.setAdapter(new LotteryNameAdapter(getApplicationContext(), items1));
                binding.tickets.setVisibility(View.GONE);
                binding.ticketRecyclerView.setVisibility(View.GONE);
            }
        });
        binding.prizePoolBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.WinnerName.setBackgroundTintList(getResources().getColorStateList(R.color.unselect_color));
                binding.prizePoolBtn.setBackgroundTintList(getResources().getColorStateList(R.color.select_color));
                binding.recyclerviewLottery.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                binding.recyclerviewLottery.setAdapter(new LotteryNameAdapter(getApplicationContext(), items2));
                binding.tickets.setVisibility(View.VISIBLE);
                binding.ticketRecyclerView.setVisibility(View.VISIBLE);
            }
        });

        binding.buyLotteryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BottomSheetLottery bottomSheetLottery = new BottomSheetLottery();
                bottomSheetLottery.show(getSupportFragmentManager(), "TAG");
                lotteryNo = String.valueOf(ThreadLocalRandom.current().nextInt(100000, 999999));
                items3.add(new LotteryTicketInformation(lotteryNo));
                lotteryAdapter.updateList(items3);
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