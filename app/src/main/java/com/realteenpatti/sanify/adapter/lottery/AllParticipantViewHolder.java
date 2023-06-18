package com.realteenpatti.sanify.adapter.lottery;


import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.realteenpatti.sanify.R;


public class AllParticipantViewHolder extends RecyclerView.ViewHolder {
    TextView participantName, participantTicketNo;

    public AllParticipantViewHolder(@NonNull View itemView) {
        super(itemView);
        participantName = itemView.findViewById(R.id.participantName);
        participantTicketNo = itemView.findViewById(R.id.participantTicketNo);
    }
}
