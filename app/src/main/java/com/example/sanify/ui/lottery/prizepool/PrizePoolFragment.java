package com.example.sanify.ui.lottery.prizepool;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.sanify.adapter.lottery.PrizePoolAdapter;
import com.example.sanify.ui.lottery.LotteryBuyFragment;
import com.example.sanify.databinding.FragmentPrizePoolBinding;
import com.example.sanify.model.lottery.PrizePoolInformation;

import java.util.ArrayList;
import java.util.List;

public class PrizePoolFragment extends Fragment {

    FragmentPrizePoolBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentPrizePoolBinding.inflate(getLayoutInflater(), container, false);
        List<PrizePoolInformation> item = new ArrayList<>();
        item.add(new PrizePoolInformation("Rank 1", "210255", "1000000"));
        item.add(new PrizePoolInformation("Rank 1", "210255", "1000000"));
        item.add(new PrizePoolInformation("Rank 1", "210255", "1000000"));
        item.add(new PrizePoolInformation("Rank 1", "210255", "1000000"));
        item.add(new PrizePoolInformation("Rank 1", "210255", "1000000"));
        item.add(new PrizePoolInformation("Rank 1", "210255", "1000000"));

        //set adapter
        binding.recyclerviewPrizePool.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.recyclerviewPrizePool.setAdapter(new PrizePoolAdapter(requireContext(), item));

        //back Button
        binding.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(requireContext(), LotteryBuyFragment.class);
                startActivity(intent);
            }
        });

        return binding.getRoot();
    }
}