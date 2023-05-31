package com.example.sanify.adapter.lottery;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.sanify.LotteryBuyFragment;
import com.example.sanify.databinding.FragmentLotteryBuyBinding;
import com.example.sanify.databinding.FragmentResultBinding;
import com.example.sanify.model.lottery.ResultInformation;

import java.util.ArrayList;
import java.util.List;

public class ResultFragment extends Fragment {
    FragmentResultBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentResultBinding.inflate(getLayoutInflater(), container, false);
        List<ResultInformation> item = new ArrayList<ResultInformation>();
        item.add(new ResultInformation("Rank1","1,00,000","2104455"));
        item.add(new ResultInformation("Rank1","1,00,000","2104455"));
        item.add(new ResultInformation("Rank1","1,00,000","2104455"));
        item.add(new ResultInformation("Rank1","1,00,000","2104455"));
        item.add(new ResultInformation("Rank1","1,00,000","2104455"));
        item.add(new ResultInformation("Rank1","1,00,000","2104455"));
        item.add(new ResultInformation("Rank1","1,00,000","2104455"));

        //set Adapter
        binding.recyclerviewResult.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.recyclerviewResult.setAdapter(new ResultAdapter(requireContext(), item));

        //back btn
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