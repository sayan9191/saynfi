package com.realteenpatti.sanify.viewholder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sanify.R;


public class WithDrawViewHolder extends RecyclerView.ViewHolder {

    public TextView transactionId, transactionMedium, amount, status;

    public WithDrawViewHolder(@NonNull View itemView) {
        super(itemView);
        transactionId = itemView.findViewById(R.id.transactionId);
        transactionMedium = itemView.findViewById(R.id.transactionMedium);
        amount = itemView.findViewById(R.id.amount);
        status = itemView.findViewById(R.id.status);
    }
}
