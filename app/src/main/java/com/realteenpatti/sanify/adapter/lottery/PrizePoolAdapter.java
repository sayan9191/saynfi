package com.realteenpatti.sanify.adapter.lottery;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.realteenpatti.sanify.R;
import com.realteenpatti.sanify.retrofit.models.lottery.PrizePoolResponseModelItem;

import java.util.ArrayList;
import java.util.List;

public class PrizePoolAdapter extends RecyclerView.Adapter<PrizePoolViewHolder> {
    List<PrizePoolResponseModelItem> items = new ArrayList<>();


    @NonNull
    @Override
    public PrizePoolViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PrizePoolViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.prizepool_item_layout, parent, false));
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull PrizePoolViewHolder holder, int position) {
        holder.prizePoolMoney.setText(String.valueOf(items.get(position).getPrize_money()));
        if (items.get(position).getRank_no() == 1) {
            holder.prizePoolRank.setText(String.valueOf(items.get(position).getRank_no()) + "st Prize");
        } else if (items.get(position).getRank_no() == 2) {
            holder.prizePoolRank.setText(String.valueOf(items.get(position).getRank_no()) + "nd Prize");
        } else if (items.get(position).getRank_no() == 3) {
            holder.prizePoolRank.setText(String.valueOf(items.get(position).getRank_no()) + "rd Prize");
        } else {
            holder.prizePoolRank.setText(String.valueOf(items.get(position).getRank_no()) + "th Prize");
        }
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
