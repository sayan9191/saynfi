package com.example.sanify.adapter.lottery;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sanify.R;
import com.example.sanify.model.lottery.ResultInformation;

import java.util.List;

public class ResultAdapter extends RecyclerView.Adapter<ResultViewHolder> {
    Context context;
    List<ResultInformation> items;

    public ResultAdapter(Context context, List<ResultInformation> items) {
        this.context = context;
        this.items = items;
    }


    @NonNull
    @Override
    public ResultViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ResultViewHolder(LayoutInflater.from(context).inflate(R.layout.lottery_profile, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ResultViewHolder holder, int position) {

        holder.rankName.setText(items.get(position).getRank());
        holder.prizeMoney.setText(items.get(position).getPrizeMoney().toString());
        holder.ticketNo.setText(items.get(position).getTicketNo().toString());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
