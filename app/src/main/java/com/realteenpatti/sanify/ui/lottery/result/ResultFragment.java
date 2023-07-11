package com.realteenpatti.sanify.ui.lottery.result;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.realteenpatti.sanify.R;
import com.realteenpatti.sanify.databinding.FragmentResultBinding;
import com.realteenpatti.sanify.adapter.lottery.ResultAdapter;
import com.realteenpatti.sanify.retrofit.models.lottery.AllWinnerResponseModel;
import com.realteenpatti.sanify.retrofit.models.lottery.AllWinnerResponseModelItem;
import com.realteenpatti.sanify.retrofit.models.lottery.CountDownTimeResponseModel;
import com.realteenpatti.sanify.retrofit.models.lottery.PrizePoolResponseModel;
import com.realteenpatti.sanify.retrofit.models.lottery.PrizePoolResponseModelItem;
import com.realteenpatti.sanify.ui.dialogbox.LoadingScreen;
import com.realteenpatti.sanify.utils.NetworkUtils;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import jp.wasabeef.recyclerview.animators.SlideInRightAnimator;
import rubikstudio.library.LuckyWheelView;
import rubikstudio.library.model.LuckyItem;

public class ResultFragment extends Fragment {
    FragmentResultBinding binding;
    ResultViewModel viewModel;
    ResultAdapter adapter;

    HashMap<Integer, Integer> prizeMoneyMap = new HashMap<>();

    List<AllWinnerResponseModelItem> winnerList = new ArrayList<>();
    long timeTillResults = -1;
    int currentIndex = 0;
    ArrayList<LuckyItem> data = new ArrayList<LuckyItem>();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentResultBinding.inflate(getLayoutInflater(), container, false);
        viewModel = new ViewModelProvider(this).get(ResultViewModel.class);
        adapter = new ResultAdapter();

        //set Adapter
        LinearLayoutManager layoutManager = new LinearLayoutManager(requireContext());
        layoutManager.setReverseLayout(true);
        binding.recyclerviewResult.setLayoutManager(layoutManager);
        binding.recyclerviewResult.setItemAnimator(new SlideInRightAnimator());
        binding.recyclerviewResult.setAdapter(adapter);


        viewModel.getLotterySlotDetails();

        viewModel.getSlotDetails().observe(getViewLifecycleOwner(), new Observer<CountDownTimeResponseModel>() {
            @Override
            public void onChanged(CountDownTimeResponseModel lotterySlotData) {
                long timeLeft = Long.parseLong(lotterySlotData.getTime_left_in_millis()) + 300000;

//                if (timeLeft < 0){
                // get how much time spent
                if (timeLeft < 0){
                    timeTillResults = -timeLeft;
                    currentIndex = (int) timeTillResults / 8000;
                }else{
                    timeTillResults = 10000000;
                    currentIndex = 101;
                }
//                    timeTillResults = 16000;


//                    currentIndex = 0;

                    // Get prizePool details
                    viewModel.getAllPrizePoolDetails();

//                }
            }
        });


        viewModel.getAllPrizePool().observe(getViewLifecycleOwner(), new Observer<PrizePoolResponseModel>() {
            @Override
            public void onChanged(PrizePoolResponseModel prizePoolResponseModelItems) {
                if (prizePoolResponseModelItems != null){
                    for(PrizePoolResponseModelItem item : prizePoolResponseModelItems){
                        prizeMoneyMap.put(item.getRank_no(), item.getPrize_money());
                    }

                    // Get all winnerList
                    viewModel.getAllWinnerDetails();
                }


            }
        });


        viewModel.getAllWinnerList().observe(getViewLifecycleOwner(), new Observer<AllWinnerResponseModel>() {
            @Override
            public void onChanged(AllWinnerResponseModel allWinnerResponseModelItems) {
                //                adapter.updateAllList(allWinnerResponseModelItems);
                winnerList.clear();
                winnerList.addAll(allWinnerResponseModelItems);
                if (winnerList.size() > 0){
                    binding.topLottieSparkle.setVisibility(View.VISIBLE);
                    binding.winnerSpinner.setVisibility(View.VISIBLE);
                    initAnimation();
//                    Log.d("_________________", winnerList.get(0).getUser().toString());
                }else{
                    binding.topLottieSparkle.setVisibility(View.GONE);
                    binding.winnerSpinner.setVisibility(View.GONE);
                }
            }
        });

        viewModel.getErrorMessage().observe(requireActivity(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                if (!Objects.equals(s, "")) {
                    Toast.makeText(requireContext(), s, Toast.LENGTH_SHORT).show();
                }
            }
        });
        viewModel.isLoading().observe(requireActivity(), new Observer<Boolean>() {
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

        //back btn
        binding.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getParentFragmentManager().popBackStackImmediate();
            }
        });

        binding.luckyWheel.setLuckyRoundItemSelectedListener(new LuckyWheelView.LuckyRoundItemSelectedListener() {
            @Override
            public void LuckyRoundItemSelected(int index) {

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        showWinnerPopup(winnerList.get(currentIndex).getLottery_token_no());
                    }
                }, 500);

            }
        });


        return binding.getRoot();
    }


    private void initSpinner(List<Integer> spinnerValues, int winningIndex){
        data.clear();

        LuckyItem luckyItem1 = new LuckyItem();
        luckyItem1.text = spinnerValues.get(0).toString();
        luckyItem1.color = Color.parseColor("#03e7fc");
        data.add(luckyItem1);

        LuckyItem luckyItem2 = new LuckyItem();
        luckyItem2.text = spinnerValues.get(1).toString();
        luckyItem2.color = Color.parseColor("#fc03f4");
        data.add(luckyItem2);

        LuckyItem luckyItem3 = new LuckyItem();
        luckyItem3.text = spinnerValues.get(2).toString();
        luckyItem3.color = Color.parseColor("#03a1fc");
        data.add(luckyItem3);

        LuckyItem luckyItem4 = new LuckyItem();
        luckyItem4.text = spinnerValues.get(3).toString();
//        luckyItem4.color = ContextCompat.getColor(requireContext(), R.color.Spinwell140);
        luckyItem4.color = Color.parseColor("#036bfc");
        data.add(luckyItem4);

        LuckyItem luckyItem5 = new LuckyItem();
        luckyItem5.text = spinnerValues.get(4).toString();
        luckyItem5.color = Color.parseColor("#03fc5e");
        data.add(luckyItem5);

        LuckyItem luckyItem6 = new LuckyItem();
        luckyItem6.text = spinnerValues.get(5).toString();
        luckyItem6.color = Color.parseColor("#d7fc03");
        data.add(luckyItem6);

        LuckyItem luckyItem7 = new LuckyItem();
        luckyItem7.text = spinnerValues.get(6).toString();
        luckyItem7.color = Color.parseColor("#fca503");
        data.add(luckyItem7);

        LuckyItem luckyItem8 = new LuckyItem();
        luckyItem8.text = spinnerValues.get(7).toString();
//        luckyItem8.color = ContextCompat.getColor(requireContext(), R.color.Spinwell140);
        luckyItem8.color = Color.parseColor("#fc3503");
        data.add(luckyItem8);

        binding.luckyWheel.setData(data);
        binding.luckyWheel.setRound(winningIndex);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                binding.luckyWheel.startLuckyWheelWithTargetIndex(winningIndex);
            }}, 1000);

    }


    private void startSpinner(int winningToken) {
        Random rand = new Random();
        ArrayList<Integer> list = new ArrayList<>();

        int index = rand.nextInt(8);

        for (int i = 0; i < 8; i++){
            if (i == index){
                list.add(winningToken);
                continue;
            }
            list.add(rand.nextInt(winningToken + 500));
        }

        list.add(winningToken);

        initSpinner(list, index+1);
    }

//    void changeToWinningNumber(int winning_num){
//
//        int paddingLength = 5 - String.valueOf(winning_num).length();
//        StringBuilder paddedString = new StringBuilder();
//
//        for (int i = 0; i < paddingLength; i++) {
//            paddedString.append('0');
//        }
//
//        paddedString.append(winning_num);
//
//        changeToNumber(Character.getNumericValue(paddedString.charAt(0)), binding.winnerDigit1, 2000);
//        changeToNumber(Character.getNumericValue(paddedString.charAt(1)), binding.winnerDigit2, 2700);
//        changeToNumber(Character.getNumericValue(paddedString.charAt(2)), binding.winnerDigit3, 3500);
//        changeToNumber(Character.getNumericValue(paddedString.charAt(3)), binding.winnerDigit4, 4500);
//        changeToNumber(Character.getNumericValue(paddedString.charAt(4)), binding.winnerDigit5, 5500);
//    }
//
//
//    void changeToNumber(int num, TextView view, long timeToShow){
//
//        CountDownTimer countDownTimer = new CountDownTimer(timeToShow, 200) {
//            @Override
//            public void onTick(long millisUntilFinished) {
//
//                int digit = ThreadLocalRandom.current().nextInt(0, 9);
//
//                view.setText(String.valueOf(digit));
//            }
//
//            @Override
//            public void onFinish() {
//                view.setText(String.valueOf(num));
//                if (timeToShow == 5500){
//                    new Handler().postDelayed(new Runnable() {
//                        @Override
//                        public void run() {
//                            changeToNextNum();
//                        }
//                        }, 1000);
//                    }
//
//            }
//        };
//
//        countDownTimer.start();
//    }
//
    void changeToNextNum(){
//        timeTillResults += 7500;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                currentIndex += 1;
                initAnimation();
            }}, 1000);
//        if (currentIndex >= winnerList.size()){
//            adapter.updateAllList(winnerList);
//            binding.recyclerviewResult.scrollToPosition(winnerList.size() - 1);
//        }else{
//            adapter.updateAllList(winnerList);
//            binding.recyclerviewResult.scrollToPosition(winnerList.subList(0, currentIndex).size() - 1);
//
//        if(currentIndex < winnerList.size())
//            changeToWinningNumber(winnerList.get(currentIndex).getLottery_token_no());
//        }
    }


    @SuppressLint("SetTextI18n")
    private void showWinnerPopup(int ticketNo){
        binding.winnerPopup.setVisibility(View.VISIBLE);
        binding.winnerPopupLayout.setVisibility(View.VISIBLE);
        binding.winnerPopupTicketNo.setVisibility(View.VISIBLE);

        binding.winnerPopupTicketNo.setText("Ticket: " + ticketNo);
        binding.winnerPopupWinMoney.setText(Objects.requireNonNull(prizeMoneyMap.get(winnerList.get(currentIndex).getPosition())).toString() + "/-");
        binding.winnerPopup.playAnimation();
        binding.coinPopupAnimation.playAnimation();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                changeToNextNum();
            }
        }, 1000);

    }


//
    void initAnimation(){
        if (timeTillResults != -1){
//            currentIndex = (int) timeTillResults / 7500;
            if (currentIndex >= winnerList.size()){
                adapter.updateAllList(winnerList);
//                binding.recyclerviewResult.scrollToPosition(winnerList.size() - 1);
//                  binding.digitAnimationLayout.setVisibility(View.GONE);
//                binding.topLottieSparkle.setVisibility(View.GONE);
//                new Handler().postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
                        binding.topLottieSparkle.setVisibility(View.GONE);
                        binding.winnerSpinner.setVisibility(View.GONE);
//                    }}, 1000);

            }else{
                adapter.updateAllList(winnerList.subList(0, currentIndex));

//                binding.recyclerviewResult.scrollToPosition(winnerList.subList(0, currentIndex).size() - 1);

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        binding.winnerPopup.setVisibility(View.GONE);
                        binding.winnerPopupLayout.setVisibility(View.GONE);
                        binding.winnerPopupTicketNo.setVisibility(View.GONE);
//                        changeToWinningNumber(winnerList.get(currentIndex).getLottery_token_no());
                        startSpinner(winnerList.get(currentIndex).getLottery_token_no());
                    }}, 1000);
            }
        }else {
            if (currentIndex < winnerList.size()){
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        binding.winnerPopup.setVisibility(View.GONE);
                        binding.winnerPopupLayout.setVisibility(View.GONE);
                        binding.winnerPopupTicketNo.setVisibility(View.GONE);
//                        changeToWinningNumber(winnerList.get(currentIndex).getLottery_token_no());
                        startSpinner(winnerList.get(currentIndex).getLottery_token_no());
                    }}, 1000);
            }
        }
    }
}