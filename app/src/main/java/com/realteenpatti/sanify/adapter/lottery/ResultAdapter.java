package com.realteenpatti.sanify.adapter.lottery;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.realteenpatti.sanify.R;
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

        AllWinnerResponseModelItem item = items.get(position);

        if(item != null && item.getUser() != null){
            holder.winnerName.setText("৳" + item.getUser().getName());
        }

        holder.winnerRank.setText("Rank " + item.getPosition());

        holder.winnerTicketNo.setText(String.valueOf(items.get(position).getLottery_token_no()));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void updateAllList(List<AllWinnerResponseModelItem> list) {
        items.clear();
        items.addAll(list);
//        this.notifyDataSetChanged();
        this.notifyItemInserted(list.size() - 1);
    }
}
