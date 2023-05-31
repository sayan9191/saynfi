package com.example.sanify.adapter.lottery;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.sanify.R;
import com.example.sanify.YourTicketsFragment;
import com.example.sanify.databinding.FragmentResultBinding;
import com.example.sanify.databinding.FragmentYourTicketsBinding;
import com.example.sanify.model.lottery.ResultInformation;
import com.example.sanify.ui.lottery.LotteryBuyFragment;

import java.util.ArrayList;
import java.util.List;

public class ResultFragment extends AppCompatActivity {
    FragmentResultBinding binding;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = FragmentResultBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        List<ResultInformation> item = new ArrayList<ResultInformation>();
        item.add(new ResultInformation("Rank1","1,00,000","2104455"));
        item.add(new ResultInformation("Rank1","1,00,000","2104455"));
        item.add(new ResultInformation("Rank1","1,00,000","2104455"));
        item.add(new ResultInformation("Rank1","1,00,000","2104455"));
        item.add(new ResultInformation("Rank1","1,00,000","2104455"));
        item.add(new ResultInformation("Rank1","1,00,000","2104455"));
        item.add(new ResultInformation("Rank1","1,00,000","2104455"));

        //set Adapter
        binding.recyclerviewResult.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        binding.recyclerviewResult.setAdapter(new ResultAdapter(getApplicationContext(), item));

        //back btn
        binding.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ResultFragment.this, LotteryBuyFragment.class);
                startActivity(intent);
            }
        });
    }

}