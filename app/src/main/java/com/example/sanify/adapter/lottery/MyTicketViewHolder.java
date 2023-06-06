package com.example.sanify.adapter.lottery;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sanify.R;

public class MyTicketViewHolder extends RecyclerView.ViewHolder {
    TextView myTicketNo;

    public MyTicketViewHolder(@NonNull View itemView) {
        super(itemView);

        myTicketNo = itemView.findViewById(R.id.myTicketNo);

    }
}
