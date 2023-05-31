package com.example.sanify;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.sanify.adapter.lottery.YourTicketsAdapter;
import com.example.sanify.databinding.FragmentYourTicketsBinding;

import java.util.ArrayList;
import java.util.List;

public class YourTicketsFragment extends Fragment {
    FragmentYourTicketsBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentYourTicketsBinding.inflate(getLayoutInflater(), container, false);
        List<YourTicketInformation> item = new ArrayList<YourTicketInformation>();
        item.add(new YourTicketInformation("21.05.2023", "10.25a.m", "212121"));
        item.add(new YourTicketInformation("21.05.2023", "10.25a.m", "212121"));
        item.add(new YourTicketInformation("21.05.2023", "10.25a.m", "212121"));
        item.add(new YourTicketInformation("21.05.2023", "10.25a.m", "212121"));
        item.add(new YourTicketInformation("21.05.2023", "10.25a.m", "212121"));
        item.add(new YourTicketInformation("21.05.2023", "10.25a.m", "212121"));
        item.add(new YourTicketInformation("21.05.2023", "10.25a.m", "212121"));

        //set Adapter
        binding.recyclerviewYourTicket.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.recyclerviewYourTicket.setAdapter(new YourTicketsAdapter(requireContext(), item));

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