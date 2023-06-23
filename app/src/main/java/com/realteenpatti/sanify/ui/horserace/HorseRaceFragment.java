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

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.realteenpatti.sanify.MainActivity;
import com.realteenpatti.sanify.databinding.FragmentHorseRaceBinding;
import com.realteenpatti.sanify.MainActivity;
import com.realteenpatti.sanify.retrofit.models.horse.GetSlotDetailsResponseModel;
import com.realteenpatti.sanify.retrofit.models.horse.HorseWinnerResponseModel;
import com.realteenpatti.sanify.ui.bottomsheet.LuckyDrawBottomSheet;
import com.realteenpatti.sanify.ui.dialogbox.LoadingScreen;

import java.util.Objects;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class HorseRaceFragment extends Fragment {

    FragmentHorseRaceBinding binding;
    HorseRaceViewModel viewModel;
    private CountDownTimer countDownTimer;
    long localRemainingTime = 0;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentHorseRaceBinding.inflate(getLayoutInflater(), container, false);
        viewModel = new ViewModelProvider(this).get(HorseRaceViewModel.class);

        viewModel.getSlotDetailsInfo();

        viewModel.getSlotDetails().observe(getViewLifecycleOwner(), new Observer<GetSlotDetailsResponseModel>() {
            @Override
            public void onChanged(GetSlotDetailsResponseModel timeSlot) {
                if (timeSlot.is_horse_bidding_slot_open()){
                    startRaceProgress(timeSlot.getRemaining_time_in_millis());
                }else{
                    nextSlotCountDown(timeSlot.getRemaining_time_in_millis());
                }
            }
        });

        viewModel.getWinnerDetail().observe(getViewLifecycleOwner(), new Observer<HorseWinnerResponseModel>() {
            @Override
            public void onChanged(HorseWinnerResponseModel winnerData) {
                changeWinningProgress(winnerData.getWinnig_horse_id(), winnerData.getWin_money());
            }
        });

        viewModel.getCoinBalance();

        viewModel.getCurrentCoinBalance().observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer balance) {
                binding.coinAmount.setText(String.valueOf(balance));
            }
        });

        viewModel.isBidSuccess().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isSuccess) {
                if (isSuccess){
                    viewModel.getCoinBalance();
                }
            }
        });

        viewModel.getErrorMessage().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                if (!Objects.equals(s, "")) {
                    Toast.makeText(requireActivity(), s, Toast.LENGTH_SHORT).show();
                }
            }
        });

        viewModel.isLoading().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isLoading) {
                if (isLoading) {
                    LoadingScreen.Companion.showLoadingDialog(requireContext());
                } else {
                    try {
                        LoadingScreen.Companion.hideLoadingDialog();
                    } catch (Exception e) {
                        e.getStackTrace();
                    }
                }
            }
        });

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
                int horseOneBidAmount = Integer.parseInt(binding.bidAmountOne.getText().toString().trim());
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



    void nextSlotCountDown(long countDownMilliSeconds){
        if (countDownTimer != null){
            countDownTimer.cancel();
        }

        countDownTimer = new CountDownTimer(countDownMilliSeconds, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {

                localRemainingTime = millisUntilFinished;

                long seconds = millisUntilFinished / 1000;
                long minutes = seconds / 60;
                long hours = minutes / 60;

                String countdownText = "Race closes in: " + String.format("%02d", hours % 24) + "h " + String.format("%02d", minutes % 60) + "m " + String.format("%02d", seconds % 60) + "s";

                binding.countdownTextView.setText(countdownText);
            }

            @Override
            public void onFinish() {
                viewModel.getSlotDetailsInfo();
                binding.countdownTextView.setText("Race is starting");
            }
        };

        countDownTimer.start();
    }

    void startRaceProgress(long remainingMilliSeconds){

        if (remainingMilliSeconds < 30000){
            setInitialProgress(50);
        }else if(remainingMilliSeconds < 40000){
            setInitialProgress(45);
        }else if (remainingMilliSeconds < 50000){
            setInitialProgress(40);
        }else if (remainingMilliSeconds < 60000){
            setInitialProgress(35);
        }else if (remainingMilliSeconds < 70000){
            setInitialProgress(30);
        }else if (remainingMilliSeconds < 80000){
            setInitialProgress(25);
        }

        if (countDownTimer != null){
            countDownTimer.cancel();
        }


        final long[] timeDelay = {0};

        countDownTimer = new CountDownTimer(remainingMilliSeconds, 50) {
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


                if (millisUntilFinished < 25000){
                    if (timeDelay[0] == 0){
                        viewModel.getWinnerDetails();
                    }
                    else{
                        timeDelay[0] += 50;
                    }

                    if (timeDelay[0] > 1000){
                        timeDelay[0] = 0;
                    }
                }
            }

            @Override
            public void onFinish() {
                binding.countdownTextView.setText("Something went wrong");
            }
        };

        countDownTimer.start();
    }


    void changeWinningProgress(int winningHorseNum, int winMoney){

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

                LuckyDrawBottomSheet bottomSheetLottery = new LuckyDrawBottomSheet(winMoney + "");
                bottomSheetLottery.show(getParentFragmentManager(), "TAG");

                viewModel.getCoinBalance();
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


    private void setInitialProgress(int percent){
        changeProgress(binding.horseOne, percent, percent - 15, 80, 0);
        changeProgress(binding.horseTwo, percent, percent - 15, 80, 0);
        changeProgress(binding.horseThree, percent, percent - 15, 80, 0);
        changeProgress(binding.horseFour, percent, percent - 15, 80, 0);
        changeProgress(binding.horseFive, percent, percent - 15, 80, 0);
        changeProgress(binding.horseSix, percent, percent - 15, 80, 0);
    }

}