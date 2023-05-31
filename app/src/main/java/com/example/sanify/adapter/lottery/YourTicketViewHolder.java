package com.example.sanify.adapter.lottery;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sanify.R;

public class YourTicketViewHolder extends RecyclerView.ViewHolder {
    TextView dateTxt,timeTxt,ticketNoTxt;

    public YourTicketViewHolder(@NonNull View itemView) {
        super(itemView);
        dateTxt = itemView.findViewById(R.id.dateTxt);
        timeTxt = itemView.findViewById(R.id.timeTxt);
        ticketNoTxt = itemView.findViewById(R.id.ticketNoTxt);

    }
}
