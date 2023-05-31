package com.example.sanify;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.sanify.adapter.lottery.YourTicketsAdapter;
import com.example.sanify.databinding.ActivityLotteryBuyBinding;
import com.example.sanify.databinding.FragmentYourTicketsBinding;
import com.example.sanify.ui.lottery.LotteryBuyFragment;

import java.util.ArrayList;
import java.util.List;

public class YourTicketsFragment extends AppCompatActivity {
    FragmentYourTicketsBinding binding;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = FragmentYourTicketsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        List<YourTicketInformation> item = new ArrayList<YourTicketInformation>();
        item.add(new YourTicketInformation("21.05.2023","10.25a.m","212121"));
        item.add(new YourTicketInformation("21.05.2023","10.25a.m","212121"));
        item.add(new YourTicketInformation("21.05.2023","10.25a.m","212121"));
        item.add(new YourTicketInformation("21.05.2023","10.25a.m","212121"));
        item.add(new YourTicketInformation("21.05.2023","10.25a.m","212121"));
        item.add(new YourTicketInformation("21.05.2023","10.25a.m","212121"));
        item.add(new YourTicketInformation("21.05.2023","10.25a.m","212121"));

        //set Adapter
        binding.recyclerviewYourTicket.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        binding.recyclerviewYourTicket.setAdapter(new YourTicketsAdapter(getApplicationContext(), item));

        //back btn
        binding.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(YourTicketsFragment.this, LotteryBuyFragment.class);
                startActivity(intent);
            }
        });
    }

}