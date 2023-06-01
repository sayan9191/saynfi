package com.example.sanify.adapter.lottery;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sanify.R;
import com.example.sanify.YourTicketInformation;
import com.example.sanify.model.lottery.LotteryTicketInformation;

import java.util.ArrayList;
import java.util.List;

public class YourTicketsAdapter extends RecyclerView.Adapter<YourTicketViewHolder> {
    Context context;
    List<YourTicketInformation> items;
    ArrayList<LotteryTicketInformation> ticketNos = new ArrayList<>();

    public YourTicketsAdapter(Context context, List<YourTicketInformation> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public YourTicketViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new YourTicketViewHolder(LayoutInflater.from(context).inflate(R.layout.ticket_item_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull YourTicketViewHolder holder, int position) {
        holder.dateTxt.setText(items.get(position).getDate());
        holder.timeTxt.setText(items.get(position).getTime());
        holder.ticketNoTxt.setText(items.get(position).getTicketNo());
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
