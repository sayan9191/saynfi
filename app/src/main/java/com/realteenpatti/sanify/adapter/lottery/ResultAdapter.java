package com.realteenpatti.sanify.adapter.lottery;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sanify.R;
import com.realteenpatti.sanify.retrofit.models.lottery.AllWinnerResponseModelItem;

import java.util.ArrayList;
import java.util.List;

public class ResultAdapter extends RecyclerView.Adapter<ResultViewHolder> {
    List<AllWinnerResponseModelItem> items = new ArrayList<>();

    @NonNull
    @Override
    public ResultViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ResultViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.winner_item_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ResultViewHolder holder, int position) {

        holder.winnerRank.setText("Rank" + items.get(position).getPosition());
        holder.winnerName.setText("৳" + items.get(position).getUser().getName());
        holder.winnerTicketNo.setText(items.get(position).getLottery_token_no());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
    public void updateAllList(List<AllWinnerResponseModelItem> list) {
        items.clear();
        items.addAll(list);
        this.notifyDataSetChanged();
    }
}
