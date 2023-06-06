package com.example.sanify.adapter.lottery;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sanify.R;
import com.example.sanify.retrofit.models.lottery.MyTicketResponseModelItem;

import java.util.ArrayList;
import java.util.List;

public class MyTicketAdapter extends RecyclerView.Adapter<MyTicketViewHolder> {
    List<MyTicketResponseModelItem> items = new ArrayList<>();


    @NonNull
    @Override
    public MyTicketViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyTicketViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.myticket_item_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyTicketViewHolder holder, int position) {
        holder.myTicketNo.setText(String.valueOf(items.get(position).getLottery_token()));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void updateList(List<MyTicketResponseModelItem> list) {
        items.clear();
        items.addAll(list);
        this.notifyDataSetChanged();
    }
}
