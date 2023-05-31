package com.example.sanify.adapter.lottery;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.sanify.R;
import com.example.sanify.databinding.FragmentPrizePoolBinding;

public class PrizePoolFragment extends Fragment {

    FragmentPrizePoolBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding=  FragmentPrizePoolBinding.inflate(getLayoutInflater(), container, false);
        return binding.getRoot();
    }
}