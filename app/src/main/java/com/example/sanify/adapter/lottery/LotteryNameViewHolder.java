package com.example.sanify.adapter.lottery;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sanify.R;

public class LotteryNameViewHolder extends RecyclerView.ViewHolder {
    TextView personName,lotteryNumber;
    public LotteryNameViewHolder(@NonNull View itemView) {
        super(itemView);
        personName = itemView.findViewById(R.id.personName);
        lotteryNumber = itemView.findViewById(R.id.lotteryNumber);
    }
}
