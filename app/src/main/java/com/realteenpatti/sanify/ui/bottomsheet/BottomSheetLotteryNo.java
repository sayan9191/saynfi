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

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.realteenpatti.sanify.R;

public class BottomSheetLotteryNo extends BottomSheetDialogFragment {
    public BottomSheetLotteryNo() {
    }

    @SuppressLint("MissingInflatedId")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.lottery_buy_no, container, false);
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
                int noOfTicket = Integer.parseInt(noOfLottery.getText().toString().trim());
                noOfTicket = noOfTicket + 10;
                noOfLottery.setText(Integer.toString(noOfTicket));
                totalBuyAmount.setText("Total Amount: "+noOfTicket*20);
            }
        });

        buyMLotteryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //call the api here

            }
        });
        return view;
    }
}
