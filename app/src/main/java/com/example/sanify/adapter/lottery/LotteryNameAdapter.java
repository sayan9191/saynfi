package com.example.sanify.adapter.lottery;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sanify.R;
import com.example.sanify.model.lottery.LotteryInformation;

import java.util.List;

public class LotteryNameAdapter extends RecyclerView.Adapter<LotteryNameViewHolder> {
    Context context;
    List<LotteryInformation> items;

    public LotteryNameAdapter(Context context, List<LotteryInformation> items) {
        this.context = context;
        this.items = items;
    }


    @NonNull
    @Override
    public LotteryNameViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new LotteryNameViewHolder(LayoutInflater.from(context).inflate(R.layout.lottery_profile, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull LotteryNameViewHolder holder, int position) {

        holder.personName.setText(items.get(position).getName());
        holder.lotteryNumber.setText(items.get(position).getNumber().toString());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
