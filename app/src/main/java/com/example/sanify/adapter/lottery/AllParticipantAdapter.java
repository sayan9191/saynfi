package com.example.sanify.adapter.lottery;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sanify.R;
import com.example.sanify.retrofit.models.lottery.AllParticipantResponseModelItem;

import java.util.ArrayList;
import java.util.List;

public class AllParticipantAdapter extends RecyclerView.Adapter<AllParticipantViewHolder> {
    List<AllParticipantResponseModelItem> items = new ArrayList<>();

    @NonNull
    @Override
    public AllParticipantViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AllParticipantViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.allparticipant_item_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull AllParticipantViewHolder holder, int position) {
        holder.participantName.setText(items.get(position).getUser().getName());
        holder.participantTicketNo.setText(String.valueOf(items.get(position).getLottery_token()));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
    public void updateAllList(List<AllParticipantResponseModelItem> list) {
        items.clear();
        items.addAll(list);
        this.notifyDataSetChanged();
    }
}
