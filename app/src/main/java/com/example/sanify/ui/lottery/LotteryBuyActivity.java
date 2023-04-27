package com.example.sanify.ui.lottery;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.sanify.R;
import com.example.sanify.adapter.lottery.LotteryNameAdapter;
import com.example.sanify.databinding.ActivityLotteryBuyBinding;
import com.example.sanify.model.lottery.LotteryInformation;

import java.util.ArrayList;
import java.util.List;

public class LotteryBuyActivity extends AppCompatActivity {
    ActivityLotteryBuyBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLotteryBuyBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        List<LotteryInformation> items = new ArrayList<LotteryInformation>();
        items.add(new LotteryInformation("SAYAN MONDAL", 20154144,R.drawable.person_icon));
        items.add(new LotteryInformation("SANTUNU MUKHERJEE", 7855558, R.drawable.person_icon));
        items.add(new LotteryInformation("Srijan Mukherjee", 98452221, R.drawable.person_icon));
        items.add(new LotteryInformation("Sreyashi Saha", 425563110, R.drawable.person_icon));
        items.add(new LotteryInformation("Bikash Ghosh", 478221235, R.drawable.person_icon));
        items.add(new LotteryInformation("Bidhut Sen", 10258686, R.drawable.person_icon));
        items.add(new LotteryInformation("Ankan Ghosh", 86574212, R.drawable.person_icon));
        items.add(new LotteryInformation("Lakshman Mondal", 78787, R.drawable.person_icon));
        items.add(new LotteryInformation("Vikash  Das", 5386865, R.drawable.person_icon));
        items.add(new LotteryInformation("Shreya Mandal", 2102222, R.drawable.person_icon));
        binding.recyclerviewLotteryBuy.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerviewLotteryBuy.setAdapter(new LotteryNameAdapter(getApplicationContext(), items));
    }
}