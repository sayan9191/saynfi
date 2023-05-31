package com.example.sanify.adapter.lottery;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sanify.R;

public class PrizePoolViewHolder extends RecyclerView.ViewHolder {
    TextView rankTxt, prizeMoneyTxt, ticketNoTxt;

    public PrizePoolViewHolder(@NonNull View itemView) {
        super(itemView);
        rankTxt = itemView.findViewById(R.id.rankTxt);
        prizeMoneyTxt = itemView.findViewById(R.id.prizeMoneyTxt);
        ticketNoTxt = itemView.findViewById(R.id.ticketNoTxt);
    }
}
