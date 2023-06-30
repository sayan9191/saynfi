package com.realteenpatti.sanify.ui.lottery.result;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.realteenpatti.sanify.databinding.FragmentResultBinding;
import com.realteenpatti.sanify.adapter.lottery.ResultAdapter;
import com.realteenpatti.sanify.retrofit.models.lottery.AllWinnerResponseModel;
import com.realteenpatti.sanify.retrofit.models.lottery.AllWinnerResponseModelItem;
import com.realteenpatti.sanify.ui.dialogbox.LoadingScreen;


import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

import jp.wasabeef.recyclerview.animators.SlideInRightAnimator;

public class ResultFragment extends Fragment {
    FragmentResultBinding binding;
    ResultViewModel viewModel;
    ResultAdapter adapter;

    List<AllWinnerResponseModelItem> winnerList = new ArrayList<>();
    long timeTillResults = -1;
    int currentIndex = 0;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

//        try {
//            if (getArguments() != null) {
//                timeTillResults = getArguments().getLong("timeTillResults");
//            }
//        }catch (Exception e){
//            e.getStackTrace();
//        }

        binding = FragmentResultBinding.inflate(getLayoutInflater(), container, false);
        viewModel = new ViewModelProvider(this).get(ResultViewModel.class);
        adapter = new ResultAdapter();

        //set Adapter
        binding.recyclerviewResult.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.recyclerviewResult.setItemAnimator(new SlideInRightAnimator());
        binding.recyclerviewResult.setAdapter(adapter);


        // get how much time spent
        timeTillResults = 5000;


        // Get all winnerList
        viewModel.getAllWinnerDetails();

        viewModel.getAllWinnerList().observe(getViewLifecycleOwner(), new Observer<AllWinnerResponseModel>() {
            @Override
            public void onChanged(AllWinnerResponseModel allWinnerResponseModelItems) {
                //                adapter.updateAllList(allWinnerResponseModelItems);
                winnerList.clear();
                winnerList.addAll(allWinnerResponseModelItems);
                if (winnerList.size() > 0){
                    initAnimation();
//                    Log.d("_________________", winnerList.get(0).getUser().toString());
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



        return binding.getRoot();
    }


    void changeToWinningNumber(int winning_num){

        int paddingLength = 5 - String.valueOf(winning_num).length();
        StringBuilder paddedString = new StringBuilder();

        for (int i = 0; i < paddingLength; i++) {
            paddedString.append('0');
        }

        paddedString.append(winning_num);

        changeToNumber(Character.getNumericValue(paddedString.charAt(0)), binding.winnerDigit1, 2000);
        changeToNumber(Character.getNumericValue(paddedString.charAt(1)), binding.winnerDigit2, 2700);
        changeToNumber(Character.getNumericValue(paddedString.charAt(2)), binding.winnerDigit3, 3500);
        changeToNumber(Character.getNumericValue(paddedString.charAt(3)), binding.winnerDigit4, 4500);
        changeToNumber(Character.getNumericValue(paddedString.charAt(4)), binding.winnerDigit5, 5500);
    }


    void changeToNumber(int num, TextView view, long timeToShow){

        CountDownTimer countDownTimer = new CountDownTimer(timeToShow, 200) {
            @Override
            public void onTick(long millisUntilFinished) {

                int digit = ThreadLocalRandom.current().nextInt(0, 9);

                view.setText(String.valueOf(digit));
            }

            @Override
            public void onFinish() {
                view.setText(String.valueOf(num));
                if (timeToShow == 5500){
                    changeToNextNum();
                }
            }
        };

        countDownTimer.start();
    }

    void changeToNextNum(){
        currentIndex += 1;
        if (currentIndex >= winnerList.size()){
            adapter.updateAllList(winnerList);
            binding.recyclerviewResult.scrollToPosition(winnerList.size() - 1);
        }else{
            adapter.updateAllList(winnerList);
            binding.recyclerviewResult.scrollToPosition(winnerList.subList(0, currentIndex).size() - 1);

        if(currentIndex < winnerList.size())
            changeToWinningNumber(winnerList.get(currentIndex).getLottery_token_no());
        }
    }

    void initAnimation(){
        if (timeTillResults != -1){
            currentIndex = (int) timeTillResults / 5500;
            if (currentIndex >= winnerList.size()){
                adapter.updateAllList(winnerList);
                binding.recyclerviewResult.scrollToPosition(winnerList.size() - 1);
            }else{
                adapter.updateAllList(winnerList.subList(0, currentIndex));

                    binding.recyclerviewResult.scrollToPosition(winnerList.subList(0, currentIndex).size() - 1);
                    changeToWinningNumber(winnerList.get(currentIndex).getLottery_token_no());

            }
        }else {
            changeToWinningNumber(winnerList.get(0).getLottery_token_no());
        }
    }
}