package com.example.sanify.adapter.lottery;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sanify.R;

public class PrizePoolViewHolder extends RecyclerView.ViewHolder {
    TextView prizePoolRank, prizePoolMoney;

    public PrizePoolViewHolder(@NonNull View itemView) {
        super(itemView);
        prizePoolRank = itemView.findViewById(R.id.prizePoolRank);
        prizePoolMoney = itemView.findViewById(R.id.prizePoolMoney);
    }
}
