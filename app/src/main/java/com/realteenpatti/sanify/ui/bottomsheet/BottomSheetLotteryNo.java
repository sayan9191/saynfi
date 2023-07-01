package com.realteenpatti.sanify.ui.bottomsheet;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.realteenpatti.sanify.R;
import com.realteenpatti.sanify.ui.bottomsheet.listeners.LotteryBuyListener;
import com.realteenpatti.sanify.ui.dialogbox.LoadingScreen;

import java.util.Objects;

public class BottomSheetLotteryNo extends BottomSheetDialogFragment {
    LotteryBuyListener listener;
    public BottomSheetLotteryNo(LotteryBuyListener listener) {
        this.listener = listener;
    }

    BottomSheetLotteryNoViewModel viewModel;
    int noOfTicket = 10;

    @SuppressLint("MissingInflatedId")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.lottery_buy_no, container, false);
        viewModel = new ViewModelProvider(this).get(BottomSheetLotteryNoViewModel.class);

        TextView decrementBtn,noOfLottery,incrementBtn,totalBuyAmount,buyMLotteryBtn;
        decrementBtn = view.findViewById(R.id.decrementBtn);
        noOfLottery = view.findViewById(R.id.noOfLottery);
        incrementBtn = view.findViewById(R.id.incrementBtn);
        totalBuyAmount = view.findViewById(R.id.totalBuyAmount);
        buyMLotteryBtn = view.findViewById(R.id.buyMLotteryBtn);

        decrementBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int noOfTicket = Integer.parseInt(noOfLottery.getText().toString().trim());
                if (noOfTicket > 2) {
                    noOfTicket = noOfTicket - 1;
                    noOfLottery.setText(Integer.toString(noOfTicket));
                    totalBuyAmount.setText("Total Amount: "+noOfTicket*20);
                } else {
                    Toast.makeText(requireContext(), "Minimum Lottery No. is 2", Toast.LENGTH_SHORT).show();
                }
            }
        });

        incrementBtn.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View view) {
                noOfTicket = Integer.parseInt(noOfLottery.getText().toString().trim());
                noOfTicket = noOfTicket + 10;
                noOfLottery.setText(Integer.toString(noOfTicket));
                totalBuyAmount.setText("Total Amount: "+noOfTicket*20 + "/-");
                buyMLotteryBtn.setText("Buy " + noOfTicket + " tickets");
            }
        });

        buyMLotteryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //call the api here
                viewModel.buyLottery(noOfTicket*20);
            }
        });

        viewModel.isBuyLotterySuccess().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isSuccess) {
                if (isSuccess){
                    listener.onMultipleLotteryBuySuccess();
                    dismiss();
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

        return view;
    }
}
