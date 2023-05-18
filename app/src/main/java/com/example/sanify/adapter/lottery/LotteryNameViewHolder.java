package com.example.sanify.adapter.lottery;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sanify.R;

public class LotteryNameViewHolder extends RecyclerView.ViewHolder {
    TextView rankName,prizeMoney,ticketNo;
    public LotteryNameViewHolder(@NonNull View itemView) {
        super(itemView);
        rankName = itemView.findViewById(R.id.rankName);
        prizeMoney = itemView.findViewById(R.id.prizeMoney);
        ticketNo = itemView.findViewById(R.id.ticketNo);
    }
}
