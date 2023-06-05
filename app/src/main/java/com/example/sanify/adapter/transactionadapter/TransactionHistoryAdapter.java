package com.example.sanify.adapter.transactionadapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sanify.R;
import com.example.sanify.retrofit.models.transaction.AllTransactionsResponseModelItem;
import com.example.sanify.viewholder.TransactionViewHolder;

import java.util.ArrayList;
import java.util.List;

public class TransactionHistoryAdapter extends RecyclerView.Adapter<TransactionViewHolder> {
    List<AllTransactionsResponseModelItem> items = new ArrayList<>();


    @NonNull
    @Override
    public TransactionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TransactionViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.transaction_item_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull TransactionViewHolder holder, int position) {
        holder.transactionId.setText("Transaction Id: " + items.get(position).getTransction_id());
        holder.transactionMedium.setText(items.get(position).getTransaction_medium());
        holder.amount.setText("Amount:  ৳" + items.get(position).getAmount());
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


    public void updateAllList(List<AllTransactionsResponseModelItem> list) {
        items.clear();
        items.addAll(list);
        this.notifyDataSetChanged();
    }
}
