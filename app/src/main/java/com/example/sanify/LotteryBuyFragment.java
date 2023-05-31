package com.example.sanify;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.sanify.adapter.lottery.ResultFragment;
import com.example.sanify.databinding.FragmentLotteryBuyBinding;
import com.example.sanify.model.lottery.LotteryTicketInformation;
import com.example.sanify.ui.auth.bottomsheet.BottomSheetLottery;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class LotteryBuyFragment extends Fragment {

    FragmentLotteryBuyBinding binding;
    private CountDownTimer countDownTimer;
    public String lotteryNo;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentLotteryBuyBinding.inflate(inflater, container, false);
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
                Intent intent = new Intent(requireContext(), YourTicketsFragment.class);
                startActivity(intent);
            }
        });

        //

        binding.resultBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(requireContext(), ResultFragment.class);
                startActivity(intent);
            }
        });

        binding.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(requireContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        return binding.getRoot();

    }
}