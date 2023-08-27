package com.realteenpatti.sanify.ui.jhandimunda;

import android.annotation.SuppressLint;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.realteenpatti.sanify.R;
import com.realteenpatti.sanify.databinding.FragmentJhandiMundaBinding;
import com.realteenpatti.sanify.retrofit.models.jhandimunda.GetJMSlotDetailsResponseModel;
import com.realteenpatti.sanify.retrofit.models.jhandimunda.JMMyBidResponseModel;
import com.realteenpatti.sanify.retrofit.models.jhandimunda.JMMyBidResponseModelItem;
import com.realteenpatti.sanify.retrofit.models.jhandimunda.JMWinnerResponseModel;
import com.realteenpatti.sanify.ui.bottomsheet.LuckyDrawBottomSheet;
import com.realteenpatti.sanify.ui.dialogbox.LoadingScreen;

import java.util.ArrayList;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Consumer;


public class JhandiMundaFragment extends Fragment {
    FragmentJhandiMundaBinding binding;
    JhandiMundaViewModel viewModel;
    private CountDownTimer countDownTimer;
    long localRemainingTime = 0;
    MediaPlayer mPlayer;

    boolean isWinnerFetched = false;
    long fetchedTime = 30000;


    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentJhandiMundaBinding.inflate(getLayoutInflater(), container, false);
        viewModel = new ViewModelProvider(this).get(JhandiMundaViewModel.class);

        if (mPlayer == null || !mPlayer.isPlaying()) {
            mPlayer = MediaPlayer.create(requireContext(), R.raw.sound_horse_race);
            mPlayer.setLooping(true);
            mPlayer.start();
        }

        //back Button
        binding.backBtn.setOnClickListener(view -> getParentFragmentManager().popBackStackImmediate());


        viewModel.getSlotDetailsInfo();

        viewModel.getSlotDetails().observe(getViewLifecycleOwner(), new Observer<GetJMSlotDetailsResponseModel>() {
            @Override
            public void onChanged(GetJMSlotDetailsResponseModel slotDetails) {
                if (slotDetails.is_jhandi_munda_slot_open()){
                    openSlot(slotDetails.getRemaining_time_in_millis());
                }else{
                    nextSlotCountDown(slotDetails.getRemaining_time_in_millis());
                }
            }
        });


        viewModel.getMyBidDetails();
        viewModel.getMyJmBids();
        viewModel.getCoinBalance();

        viewModel.getMyBidDetails().observe(getViewLifecycleOwner(), new Observer<JMMyBidResponseModel>() {
            @Override
            public void onChanged(JMMyBidResponseModel biddingDetails) {
                biddingDetails.forEach(new Consumer<JMMyBidResponseModelItem>() {
                    @Override
                    public void accept(JMMyBidResponseModelItem bidDetail) {
                        switch (bidDetail.getCard_id()) {
                            case 1:
                                binding.bidingAmountOne.setText(String.valueOf(bidDetail.getBid_amount()));
                                break;
                            case 2:
                                binding.bidingAmountTwo.setText(String.valueOf(bidDetail.getBid_amount()));
                                break;
                            case 3:
                                binding.bidingAmountThree.setText(String.valueOf(bidDetail.getBid_amount()));
                                break;
                            case 4:
                                binding.bidingAmountFour.setText(String.valueOf(bidDetail.getBid_amount()));
                                break;
                            case 5:
                                binding.bidingAmountFive.setText(String.valueOf(bidDetail.getBid_amount()));
                                break;
                            case 6:
                                binding.bidingAmountSix.setText(String.valueOf(bidDetail.getBid_amount()));
                                break;
                        }
                    }
                });
            }
        });

        viewModel.getCurrentCoinBalance().observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer balance) {
                binding.coinAmount.setText(String.valueOf(balance));
            }
        });

        viewModel.isBidSuccess().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isSuccess) {
                if (isSuccess) {
                    viewModel.getCoinBalance();
                    viewModel.getMyJmBids();
                }
            }
        });

        viewModel.getErrorMessage().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                if (!Objects.equals(s, "")) {
                    Toast.makeText(requireActivity(), s, Toast.LENGTH_SHORT).show();
                    if (Objects.equals(s, "You haven't participated in this slot")) {
                        isWinnerFetched = true;
                    }
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

        binding.bidOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int diceOneBidAmount = Integer.parseInt(binding.bidAmountOne.getText().toString().trim());
                binding.bidAmountOne.setText("10");
                viewModel.jmBid(diceOneBidAmount, 1);
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

        binding.bidTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int diceTwoBidAmount = Integer.parseInt(binding.bidAmountTwo.getText().toString().trim());
                binding.bidAmountTwo.setText("10");
                viewModel.jmBid(diceTwoBidAmount, 2);
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

        binding.bidThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int diceThreeBidAmount = Integer.parseInt(binding.bidAmountThree.getText().toString().trim());
                binding.bidAmountThree.setText("10");
                viewModel.jmBid(diceThreeBidAmount, 3);
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

        binding.bidFour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int diceFourBidAmount = Integer.parseInt(binding.bidAmountFour.getText().toString().trim());
                binding.bidAmountFour.setText("10");
                viewModel.jmBid(diceFourBidAmount, 4);
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

        binding.bidFive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int diceFiveBidAmount = Integer.parseInt(binding.bidAmountFive.getText().toString().trim());
                binding.bidAmountFive.setText("10");
                viewModel.jmBid(diceFiveBidAmount, 5);
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
        binding.bidSix.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int diceSixBidAmount = Integer.parseInt(binding.bidAmountSix.getText().toString().trim());
                binding.bidAmountSix.setText("10");
                viewModel.jmBid(diceSixBidAmount, 6);
            }
        });


        viewModel.getWinnerDetail().observe(getViewLifecycleOwner(), new Observer<JMWinnerResponseModel>() {
            @Override
            public void onChanged(JMWinnerResponseModel winnerDetails) {

                if (winnerDetails != null){
                    showWinnerAnimation(winnerDetails);
                }
            }
        });


        return binding.getRoot();

    }


    private void openSlot(int remainingMilliSeconds) {

        enableBidButtons();

        if (countDownTimer != null){
            countDownTimer.cancel();
        }

        countDownTimer = new CountDownTimer(remainingMilliSeconds, 50) {
            @Override
            public void onTick(long millisUntilFinished) {

                localRemainingTime = millisUntilFinished;

                long seconds = millisUntilFinished / 1000;
                long minutes = seconds / 60;
                long hours = minutes / 60;

                String countdownText = "Slot closes in: " + String.format("%02d", hours % 24) + "h " + String.format("%02d", minutes % 60) + "m " + String.format("%02d", seconds % 60) + "s";

                binding.countdownTextView.setText(countdownText);


            }

            @Override
            public void onFinish() {
                binding.countdownTextView.setText("Game finished");
                viewModel.getSlotDetailsInfo();
                viewModel.getWinnerDetails();
            }
        };

        countDownTimer.start();
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

                String countdownText = "Game starts in: " + String.format("%02d", hours % 24) + "h " + String.format("%02d", minutes % 60) + "m " + String.format("%02d", seconds % 60) + "s";

                binding.countdownTextView.setText(countdownText);


                if (millisUntilFinished < 10001){
                    disableBidButton();
                }

                if (!isWinnerFetched && fetchedTime - millisUntilFinished > 2000){
                    viewModel.getWinnerDetails();
                    fetchedTime = millisUntilFinished;
                }

            }

            @Override
            public void onFinish() {
                viewModel.getSlotDetailsInfo();
                binding.countdownTextView.setText("Game is starting");
                resetBidAmounts();
                isWinnerFetched = false;
            }
        };

        countDownTimer.start();
    }


    CountDownTimer timer;
    private void showWinnerAnimation(JMWinnerResponseModel winnerDetails){
        isWinnerFetched = true;

        int[] arr = {0,0,0,0,0,0};

//        binding.animation.setVisibility(View.VISIBLE);
//        binding.dice1Anim.playAnimation();
//        binding.dice2Anim.playAnimation();
//        binding.dice3Anim.playAnimation();
//        binding.dice4Anim.playAnimation();
//        binding.dice5Anim.playAnimation();
//        binding.dice6Anim.playAnimation();


        if (winnerDetails.getWinnig_card_id() == -1){
            for (int index = 0; index < 6; index ++) {
                int randomNum = ThreadLocalRandom.current().nextInt(1, 6 + 1);
                while (isArrContains(arr, randomNum)){
                    randomNum = ThreadLocalRandom.current().nextInt(1, 6 + 1);
                }
                arr[index] = randomNum;
            }
        } else {
            /*
             * If any card wins
             */
            int numOfWinningCards = ThreadLocalRandom.current().nextInt(2, 4 + 1);

            /// Assign winning card positions
            while(numOfWinningCards > 0){
                int randomIndexNum = ThreadLocalRandom.current().nextInt(0, 5 + 1);
                if (arr[randomIndexNum] != 0){
                    randomIndexNum = ThreadLocalRandom.current().nextInt(0, 5 + 1);
                }
                arr[randomIndexNum] = winnerDetails.getWinnig_card_id();
                numOfWinningCards--;
            }

            /// Fill up the rest of the positions
            int randomNum = ThreadLocalRandom.current().nextInt(1, 6 + 1);

            for (int index = 0; index < 6; index ++) {
                while (isArrContains(arr, randomNum)){
                    randomNum = ThreadLocalRandom.current().nextInt(1, 6 + 1);
                }
                if(arr[index] == 0){
                    arr[index] = randomNum;
                }
            }
        }

        if (timer != null){
            timer.cancel();
        }

        // Roll dice
        timer = new CountDownTimer(3000, 200) {
            @Override
            public void onTick(long millisUntilFinished) {
                rollDice();
            }

            @Override
            public void onFinish() {

                // Stop dice roll
                placeAllDice(arr);

                // Show pop up
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        showPopUp(winnerDetails);
                    }
                }, 2000);
            }
        };

        timer.start();

//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
////                binding.dice1Anim.pauseAnimation();
////                binding.dice2Anim.pauseAnimation();
////                binding.dice3Anim.pauseAnimation();
////                binding.dice4Anim.pauseAnimation();
////                binding.dice5Anim.pauseAnimation();
////                binding.dice6Anim.pauseAnimation();
////                binding.animation.setVisibility(View.GONE);
//
//                new Handler().postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        showPopUp(winnerDetails);
//                    }
//                }, 2000);
//            }
//        }, 3000);

    }


    private void rollDice(){
        int[] arr = {0,0,0,0,0,0};

        for (int i = 0; i < arr.length; i++){
            arr[i] = ThreadLocalRandom.current().nextInt(1, 6 + 1);
        }

        placeAllDice(arr);
    }

    void placeAllDice(int[] arr){

        for (int index = 0; index < arr.length; index ++){
            switch (arr[index]) {
                case 1:
                    setDice(index + 1, R.drawable.jhandi_one);
                    break;
                case 2:
                    setDice(index + 1, R.drawable.jhandi_two);
                    break;
                case 3:
                    setDice(index + 1, R.drawable.jhandi_three);
                    break;
                case 4:
                    setDice(index + 1, R.drawable.jhansi_four);
                    break;
                case 5:
                    setDice(index + 1, R.drawable.jhandi_five);
                    break;
                case 6:
                    setDice(index + 1, R.drawable.jhandi_six);
                    break;
            }
        }
    }

    void setDice(int position, int resource){
        switch (position) {
            case 1:
                binding.dice1.setImageResource(resource);
                break;
            case 2:
                binding.dice2.setImageResource(resource);
                break;
            case 3:
                binding.dice3.setImageResource(resource);
                break;
            case 4:
                binding.dice4.setImageResource(resource);
                break;
            case 5:
                binding.dice5.setImageResource(resource);
                break;
            case 6:
                binding.dice6.setImageResource(resource);
                break;
        }
    }

    private Boolean isArrContains(int[] arr, int num){
        for(int element : arr){
            if (element == num){
                return true;
            }
        }
        return false;
    }


    private void showPopUp(JMWinnerResponseModel winnerDetails){
        if (winnerDetails.getWinnig_card_id() == -1 || !winnerDetails.is_user_winner()) {
            LuckyDrawBottomSheet bottomSheetLottery = new LuckyDrawBottomSheet(0, winnerDetails.getWinnig_card_id(), "jm");
            Log.d("_________", String.valueOf(winnerDetails.getWin_money()));
            bottomSheetLottery.show(getParentFragmentManager(), "TAG");
        }else if (winnerDetails.getTotal_bid_money() > 0){
            LuckyDrawBottomSheet bottomSheetLottery = new LuckyDrawBottomSheet(winnerDetails.getWin_money(), winnerDetails.getWinnig_card_id(), "jm");
            Log.d("_________", String.valueOf(winnerDetails.getWin_money()));
            bottomSheetLottery.show(getParentFragmentManager(), "TAG");
        }
    }



    public void disableBidButton() {
        binding.bidOne.setEnabled(false);
        binding.bidOne.setAlpha(0.5f);

        binding.bidTwo.setEnabled(false);
        binding.bidTwo.setAlpha(0.5f);

        binding.bidThree.setEnabled(false);
        binding.bidThree.setAlpha(0.5f);

        binding.bidFour.setEnabled(false);
        binding.bidFour.setAlpha(0.5f);

        binding.bidFive.setEnabled(false);
        binding.bidFive.setAlpha(0.5f);

        binding.bidSix.setEnabled(false);
        binding.bidSix.setAlpha(0.5f);
//        binding.myBidingLinear1.setVisibility(View.GONE);
//        binding.myBidingLinear2.setVisibility(View.GONE);
    }

    //bid start function--SAYAN
    @SuppressLint("SetTextI18n")
    public void enableBidButtons() {
        binding.bidOne.setEnabled(true);
        binding.bidOne.setAlpha(1f);
        binding.bidTwo.setEnabled(true);
        binding.bidTwo.setAlpha(1f);
        binding.bidThree.setEnabled(true);
        binding.bidThree.setAlpha(1f);
        binding.bidFour.setEnabled(true);
        binding.bidFour.setAlpha(1f);
        binding.bidFive.setEnabled(true);
        binding.bidFive.setAlpha(1f);
        binding.bidSix.setEnabled(true);
        binding.bidSix.setAlpha(1f);
    }

    private void resetBidAmounts(){
        binding.bidingAmountOne.setText("Current bid: 0");
        binding.bidingAmountTwo.setText("Current bid: 0");
        binding.bidingAmountThree.setText("Current bid: 0");
        binding.bidingAmountFour.setText("Current bid: 0");
        binding.bidingAmountFive.setText("Current bid: 0");
        binding.bidingAmountSix.setText("Current bid: 0");

        int[] arr = {1,2,3,4,5,6};
        placeAllDice(arr);
        isWinnerFetched = false;
    }


    @Override
    public void onPause() {
        super.onPause();
        mPlayer.stop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPlayer.stop();
    }
}