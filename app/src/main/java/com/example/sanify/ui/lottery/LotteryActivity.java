package com.example.sanify.ui.lottery;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.sanify.model.lottery.LotteryInformation;
import com.example.sanify.adapter.lottery.LotteryNameAdapter;
import com.example.sanify.R;

import java.util.ArrayList;
import java.util.List;

public class LotteryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lottery);

        RecyclerView recyclerviewLottery = findViewById(R.id.recyclerviewLottery);

        List<LotteryInformation> items = new ArrayList<LotteryInformation>();
        items.add(new LotteryInformation("SAYAN MONDAL",201000, R.drawable.person_icon));
        items.add(new LotteryInformation("SAYAN MONDAL",201000, R.drawable.person_icon));
        items.add(new LotteryInformation("SAYAN MONDAL",201000, R.drawable.person_icon));
        items.add(new LotteryInformation("SAYAN MONDAL",201000, R.drawable.person_icon));
        items.add(new LotteryInformation("SAYAN MONDAL",201000, R.drawable.person_icon));
        items.add(new LotteryInformation("SAYAN MONDAL",201000, R.drawable.person_icon));
        items.add(new LotteryInformation("SAYAN MONDAL",201000, R.drawable.person_icon));
        items.add(new LotteryInformation("SAYAN MONDAL",201000, R.drawable.person_icon));
        items.add(new LotteryInformation("SAYAN MONDAL",201000, R.drawable.person_icon));
        items.add(new LotteryInformation("SAYAN MONDAL",201000, R.drawable.person_icon));
        recyclerviewLottery.setLayoutManager(new LinearLayoutManager(this));
        recyclerviewLottery.setAdapter(new LotteryNameAdapter(getApplicationContext(),items));
    }
}