package com.example.sanify.adapter.transactionadapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sanify.R;
import com.example.sanify.retrofit.models.transaction.AllTransactionsResponseModelItem;
import com.example.sanify.retrofit.models.transaction.AllWithDrawResponseModelItem;
import com.example.sanify.viewholder.WithDrawViewHolder;

import java.util.ArrayList;
import java.util.List;

public class WithDrawHistoryAdapter extends RecyclerView.Adapter<WithDrawViewHolder> {
    List<AllWithDrawResponseModelItem> items = new ArrayList<>();

    @NonNull
    @Override
    public WithDrawViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new WithDrawViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.transaction_item_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull WithDrawViewHolder holder, int position) {
        holder.transactionMedium.setText(items.get(position).getTransaction_medium());
        holder.amount.setText("Amount:  ৳" + items.get(position).getAmount());
        holder.transactionId.setVisibility(View.GONE);
        if (items.get(position).is_rejected_by_admin()) {
            holder.status.setText("Status: Rejected");
        }
        if (items.get(position).is_verified()) {
            holder.status.setText("Status: Approved");
        } else {
            holder.status.setText("Status: Pending");
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
    public void updateAllList(List<AllWithDrawResponseModelItem> list) {
        items.clear();
        items.addAll(list);
        this.notifyDataSetChanged();
    }
}
