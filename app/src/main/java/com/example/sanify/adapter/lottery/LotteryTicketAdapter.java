package com.example.sanify.adapter.lottery;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sanify.R;
import com.example.sanify.model.lottery.LotteryTicketInformation;

import java.util.ArrayList;
import java.util.List;

public class LotteryTicketAdapter extends RecyclerView.Adapter<LotteryTicketViewHolder> {
    Context context;
    ArrayList<LotteryTicketInformation> ticketNos = new ArrayList<>();

    public LotteryTicketAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public LotteryTicketViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new LotteryTicketViewHolder(LayoutInflater.from(context).inflate(R.layout.tickets_item_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull LotteryTicketViewHolder holder, int position) {
        holder.myTicketNumber.setText(ticketNos.get(position).getLotteryTicketNo());
    }

    @Override
    public int getItemCount() {
        return ticketNos.size();
    }


    public void updateList(List<LotteryTicketInformation> list){
        ticketNos.clear();
        ticketNos.addAll(list);
        this.notifyDataSetChanged();
    }
}
