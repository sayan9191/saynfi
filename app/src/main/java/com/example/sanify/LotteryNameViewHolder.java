package com.example.sanify;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class LotteryNameViewHolder extends RecyclerView.ViewHolder {
    ImageView profileImage;
    TextView personName,lotteryNumber;
    public LotteryNameViewHolder(@NonNull View itemView) {
        super(itemView);
        profileImage = itemView.findViewById(R.id.profileImage);
        personName = itemView.findViewById(R.id.personName);
        lotteryNumber = itemView.findViewById(R.id.lotteryNumber);
    }
}
