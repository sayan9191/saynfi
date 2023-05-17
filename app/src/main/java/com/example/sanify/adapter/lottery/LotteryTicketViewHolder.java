package com.example.sanify.adapter.lottery;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sanify.R;

public class LotteryTicketViewHolder extends RecyclerView.ViewHolder {
    TextView myTicketNumber;

    public LotteryTicketViewHolder(@NonNull View itemView) {
        super(itemView);
        myTicketNumber = itemView.findViewById(R.id.myTicketNumber);

    }
}
