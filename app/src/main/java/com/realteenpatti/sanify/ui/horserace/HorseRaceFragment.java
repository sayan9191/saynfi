package com.realteenpatti.sanify.ui.horserace;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.realteenpatti.sanify.MainActivity;
import com.realteenpatti.sanify.databinding.FragmentHorseRaceBinding;
import com.realteenpatti.sanify.MainActivity;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class HorseRaceFragment extends Fragment {

    FragmentHorseRaceBinding binding;
    HorseRaceViewModel viewModel;
    private CountDownTimer countDownTimer;
    long localRemainingTime = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentHorseRaceBinding.inflate(getLayoutInflater(), container, false);
        viewModel = new ViewModelProvider(this).get(HorseRaceViewModel.class);


        randomProgress();

        binding.increaseOne.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View view) {
                Integer amount1 = Integer.valueOf(binding.bidAmountOne.getText().toString().trim());
                amount1 = amount1 + 10;
                binding.bidAmountOne.setText(amount1.toString());
            }
        });

        binding.decreaseOne.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View view) {
                Integer amount1 = Integer.valueOf(binding.bidAmountOne.getText().toString().trim());
                if (amount1 > 10) {
                    amount1 = amount1 - 10;
                    binding.bidAmountOne.setText(amount1.toString());
                } else {
                    Toast.makeText(requireContext(), "Minimum Bid amount is 10", Toast.LENGTH_SHORT).show();
                }
            }
        });

        binding.bidBtnOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Integer horseOneBidAmount = Integer.valueOf(binding.bidAmountOne.getText().toString().trim());
                Toast.makeText(requireContext(), "You bid " + horseOneBidAmount + " on Horse No.1", Toast.LENGTH_SHORT).show();
                binding.bidAmountOne.setText("10");
                viewModel.horseBid(horseOneBidAmount, 1);
            }
        });

        binding.increaseTwo.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View view) {
                int amount2 = Integer.parseInt(binding.bidAmountTwo.getText().toString().trim());
                amount2 = amount2 + 10;
                binding.bidAmountTwo.setText(Integer.toString(amount2));
            }
        });

        binding.decreaseTwo.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View view) {
                int amount2 = Integer.parseInt(binding.bidAmountTwo.getText().toString().trim());
                if (amount2 > 10) {
                    amount2 = amount2 - 10;
                    binding.bidAmountTwo.setText(Integer.toString(amount2));
                } else {
                    Toast.makeText(requireContext(), "Minimum Bid amount is 10", Toast.LENGTH_SHORT).show();
                }
            }
        });

        binding.bidBtnTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int horseTwoBidAmount = Integer.parseInt(binding.bidAmountTwo.getText().toString().trim());
                Toast.makeText(requireContext(), "You bid " + horseTwoBidAmount + " on Horse No.2", Toast.LENGTH_SHORT).show();
                binding.bidAmountTwo.setText("10");
                viewModel.horseBid(horseTwoBidAmount, 2);
            }
        });

        binding.increaseThree.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View view) {
                int amount3 = Integer.parseInt(binding.bidAmountThree.getText().toString().trim());
                amount3 = amount3 + 10;
                binding.bidAmountThree.setText(Integer.toString(amount3));
            }
        });

        binding.decreaseThree.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View view) {
                int amount3 = Integer.parseInt(binding.bidAmountThree.getText().toString().trim());
                if (amount3 > 10) {
                    amount3 = amount3 - 10;
                    binding.bidAmountThree.setText(Integer.toString(amount3));
                } else {
                    Toast.makeText(requireContext(), "Minimum Bid amount is 10", Toast.LENGTH_SHORT).show();
                }
            }
        });

        binding.bidBtnThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int horseThreeBidAmount = Integer.parseInt(binding.bidAmountThree.getText().toString().trim());
                Toast.makeText(requireContext(), "You bid " + horseThreeBidAmount + " on Horse No.3", Toast.LENGTH_SHORT).show();
                binding.bidAmountThree.setText("10");
                viewModel.horseBid(horseThreeBidAmount, 3);
            }
        });

        binding.increaseFour.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View view) {
                int amount4 = Integer.parseInt(binding.bidAmountFour.getText().toString().trim());
                amount4 = amount4 + 10;
                binding.bidAmountFour.setText(Integer.toString(amount4));
            }
        });

        binding.decreaseFour.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View view) {
                int amount4 = Integer.parseInt(binding.bidAmountFour.getText().toString().trim());
                if (amount4 > 10) {
                    amount4 = amount4 - 10;
                    binding.bidAmountFour.setText(Integer.toString(amount4));
                } else {
                    Toast.makeText(requireContext(), "Minimum Bid amount is 10", Toast.LENGTH_SHORT).show();
                }
            }
        });

        binding.bidBtnFour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int horseFourBidAmount = Integer.parseInt(binding.bidAmountFour.getText().toString().trim());
                Toast.makeText(requireContext(), "You bid " + horseFourBidAmount + " on Horse No.4", Toast.LENGTH_SHORT).show();
                binding.bidAmountFour.setText("10");
                viewModel.horseBid(horseFourBidAmount, 4);
            }
        });

        binding.increaseFive.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View view) {
                int amount5 = Integer.parseInt(binding.bidAmountFive.getText().toString().trim());
                amount5 = amount5 + 10;
                binding.bidAmountFive.setText(Integer.toString(amount5));
            }
        });

        binding.decreaseFive.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View view) {
                int amount5 = Integer.parseInt(binding.bidAmountFive.getText().toString().trim());
                if (amount5 > 10) {
                    amount5 = amount5 - 10;
                    binding.bidAmountFive.setText(Integer.toString(amount5));
                } else {
                    Toast.makeText(requireContext(), "Minimum Bid amount is 10", Toast.LENGTH_SHORT).show();
                }
            }
        });

        binding.bidBtnFive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int horseFiveBidAmount = Integer.parseInt(binding.bidAmountFive.getText().toString().trim());
                Toast.makeText(requireContext(), "You bid " + horseFiveBidAmount + " on Horse No.5", Toast.LENGTH_SHORT).show();
                binding.bidAmountFive.setText("10");
                viewModel.horseBid(horseFiveBidAmount, 5);
            }
        });

        binding.increaseSix.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View view) {
                int amount6 = Integer.parseInt(binding.bidAmountSix.getText().toString().trim());
                amount6 = amount6 + 10;
                binding.bidAmountSix.setText(Integer.toString(amount6));
            }
        });

        binding.decreaseSix.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View view) {
                int amount6 = Integer.parseInt(binding.bidAmountSix.getText().toString().trim());
                if (amount6 > 10) {
                    amount6 = amount6 - 10;
                    binding.bidAmountSix.setText(Integer.toString(amount6));
                } else {
                    Toast.makeText(requireContext(), "Minimum Bid amount is 10", Toast.LENGTH_SHORT).show();
                }
            }
        });
        binding.bidBtnSix.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int horseSixBidAmount = Integer.parseInt(binding.bidAmountSix.getText().toString().trim());
                Toast.makeText(requireContext(), "You bid " + horseSixBidAmount + " on Horse No.6", Toast.LENGTH_SHORT).show();
                binding.bidAmountSix.setText("10");
                viewModel.horseBid(horseSixBidAmount, 6);
            }
        });

        //back Button
        binding.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(requireContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        return binding.getRoot();
    }


    void randomProgress(){
        if (countDownTimer != null){
            countDownTimer.cancel();
        }

        countDownTimer = new CountDownTimer(90000, 50) {
            @Override
            public void onTick(long millisUntilFinished) {

                localRemainingTime = millisUntilFinished;

                long seconds = millisUntilFinished / 1000;
                long minutes = seconds / 60;
                long hours = minutes / 60;

                String countdownText = "Race closes in: " + String.format("%02d", hours % 24) + "h " + String.format("%02d", minutes % 60) + "m " + String.format("%02d", seconds % 60) + "s";

                binding.countdownTextView.setText(countdownText);

                changeProgress(binding.horseOne, 1, -1, 80, 0);
                changeProgress(binding.horseTwo, 1, -1, 80, 0);
                changeProgress(binding.horseThree, 1, -1, 80, 0);
                changeProgress(binding.horseFour, 1, -1, 80, 0);
                changeProgress(binding.horseFive, 1, -1, 80, 0);
                changeProgress(binding.horseSix, 1, -1, 80, 0);

                if (localRemainingTime < 20000){
                    int winNum = ThreadLocalRandom.current().nextInt(1, 6);
                    changeWinningProgress(winNum);
                }
            }

            @Override
            public void onFinish() {
                binding.countdownTextView.setText("Congratulations Winners");
            }
        };

        countDownTimer.start();
    }


    void changeWinningProgress(int winningHorseNum){

        Log.d("Winner", "Horse: " + winningHorseNum);

        if (countDownTimer != null){
            countDownTimer.cancel();
        }

        countDownTimer = new CountDownTimer(localRemainingTime, 100) {
            @Override
            public void onTick(long millisUntilFinished) {

                long seconds = millisUntilFinished / 1000;
                long minutes = seconds / 60;
                long hours = minutes / 60;

                String countdownText = "Race closes in: " + String.format("%02d", hours % 24) + "h " + String.format("%02d", minutes % 60) + "m " + String.format("%02d", seconds % 60) + "s";

                binding.countdownTextView.setText(countdownText);

                if (winningHorseNum == 1){
                    changeProgress(binding.horseOne, ThreadLocalRandom.current().nextInt(2, 4), -1, 97, 0);
                }else{
                    changeProgress(binding.horseOne, 2, -1, ThreadLocalRandom.current().nextInt(85, 96), 0);
                }

                if (winningHorseNum == 2){
                    changeProgress(binding.horseTwo, ThreadLocalRandom.current().nextInt(2, 4), -1, 98, 0);
                }else{
                    changeProgress(binding.horseTwo, 2, -1, ThreadLocalRandom.current().nextInt(85, 96), 0);
                }

                if (winningHorseNum == 3){
                    changeProgress(binding.horseThree, ThreadLocalRandom.current().nextInt(2, 4), -1, 97, 0);
                }else{
                    changeProgress(binding.horseThree, 2, -1, ThreadLocalRandom.current().nextInt(80, 96), 0);
                }

                if (winningHorseNum == 4){
                    changeProgress(binding.horseFour, ThreadLocalRandom.current().nextInt(2, 4), -1, 98, 0);
                }else{
                    changeProgress(binding.horseFour, 2, -1, ThreadLocalRandom.current().nextInt(85, 96), 0);
                }

                if (winningHorseNum == 5){
                    changeProgress(binding.horseFive, ThreadLocalRandom.current().nextInt(2, 4), -1, 97, 0);
                }else{
                    changeProgress(binding.horseFive, 2, -1, ThreadLocalRandom.current().nextInt(80, 96), 0);
                }

                if (winningHorseNum == 6){
                    changeProgress(binding.horseSix, ThreadLocalRandom.current().nextInt(2, 4), -1, 98, 0);
                }else{
                    changeProgress(binding.horseSix, 2, -1, ThreadLocalRandom.current().nextInt(85, 96), 0);
                }
            }

            @Override
            public void onFinish() {
                binding.countdownTextView.setText("Congratulations Winners");

                switch (winningHorseNum){
                    case 1:
                        binding.horseOne.setProgress(100, true);
                        break;
                    case 2:
                        binding.horseTwo.setProgress(100, true);
                        break;
                    case 3:
                        binding.horseThree.setProgress(100, true);
                        break;
                    case 4:
                        binding.horseFour.setProgress(100, true);
                        break;
                    case 5:
                        binding.horseFive.setProgress(100, true);
                        break;
                    case 6:
                        binding.horseSix.setProgress(100, true);
                        break;
                }
            }
        };

        countDownTimer.start();
    }

    private void changeProgress(ProgressBar view, int max, int min, int upperLimit, int initialLowerLimit){

        int lowerLimit = initialLowerLimit;

        int currentProgress = view.getProgress();

        int randomNum = ThreadLocalRandom.current().nextInt(min, max + 1);

        if (currentProgress > 40){
            lowerLimit = 40;
        }else
        if (currentProgress > 30){
            randomNum += ThreadLocalRandom.current().nextInt(-1, 2);
            lowerLimit = 30;
        }else
        if (currentProgress > 20){
            lowerLimit = 20;
            randomNum += ThreadLocalRandom.current().nextInt(0, 2);
        }else
        if (currentProgress > 10){
            lowerLimit = 10;
            randomNum += ThreadLocalRandom.current().nextInt(1, 2);
        }else if (currentProgress < 10){
            randomNum += ThreadLocalRandom.current().nextInt(1, 3);
        }


        if (currentProgress + randomNum < lowerLimit){
            view.setProgress(lowerLimit, true);
        }
        else if(currentProgress + randomNum > upperLimit){
            view.setProgress(upperLimit, true);
        } else {
            view.setProgress(currentProgress + randomNum, true);
        }
    }

}