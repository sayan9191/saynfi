package com.realteenpatti.sanify.adapter.lottery;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sanify.R;


public class ResultViewHolder extends RecyclerView.ViewHolder {
    TextView winnerRank,  winnerTicketNo,winnerName;

    public ResultViewHolder(@NonNull View itemView) {
        super(itemView);
        winnerRank = itemView.findViewById(R.id.winnerRank);
        winnerName = itemView.findViewById(R.id.winnerName);
        winnerTicketNo = itemView.findViewById(R.id.winnerTicketNo);
    }
}
