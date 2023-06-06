package com.example.sanify.adapter.lottery;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sanify.R;
import com.example.sanify.retrofit.models.lottery.PrizePoolResponseModelItem;

import java.util.ArrayList;
import java.util.List;

public class PrizePoolAdapter extends RecyclerView.Adapter<PrizePoolViewHolder> {
    List<PrizePoolResponseModelItem> items = new ArrayList<>();


    @NonNull
    @Override
    public PrizePoolViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PrizePoolViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.prizepool_item_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull PrizePoolViewHolder holder, int position) {
        holder.prizePoolMoney.setText("৳" + String.valueOf(items.get(position).getPrize_money()));
        holder.prizePoolRank.setText("Rank" + String.valueOf(items.get(position).getRank_no()));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void updateList(List<PrizePoolResponseModelItem> list) {
        items.clear();
        items.addAll(list);
        this.notifyDataSetChanged();
    }
}
