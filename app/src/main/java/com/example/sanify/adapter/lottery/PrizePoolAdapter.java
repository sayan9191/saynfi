package com.example.sanify.adapter.lottery;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sanify.R;
import com.example.sanify.model.lottery.PrizePoolInformation;

import java.util.List;

public class PrizePoolAdapter extends RecyclerView.Adapter<PrizePoolViewHolder> {
    Context context;
    List<PrizePoolInformation> items;

    public PrizePoolAdapter(Context context, List<PrizePoolInformation> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public PrizePoolViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PrizePoolViewHolder(LayoutInflater.from(context).inflate(R.layout.prizepool_item_layout, parent, false));

    }

    @Override
    public void onBindViewHolder(@NonNull PrizePoolViewHolder holder, int position) {
        holder.rankTxt.setText(items.get(position).getRank());
        holder.prizeMoneyTxt.setText(items.get(position).getPrizeMoney());
        holder.ticketNoTxt.setText(items.get(position).getTicketNo());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
