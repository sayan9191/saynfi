package com.realteenpatti.sanify.ui.lottery.prizepool;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.sanify.databinding.FragmentPrizePoolBinding;
import com.realteenpatti.sanify.adapter.lottery.PrizePoolAdapter;

import com.realteenpatti.sanify.retrofit.models.lottery.PrizePoolResponseModel;
import com.realteenpatti.sanify.ui.dialogbox.LoadingScreen;

import java.util.Objects;

public class PrizePoolFragment extends Fragment {

    FragmentPrizePoolBinding binding;
    PrizePoolViewModel viewModel;
    PrizePoolAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentPrizePoolBinding.inflate(getLayoutInflater(), container, false);
        viewModel = new ViewModelProvider(this).get(PrizePoolViewModel.class);
        adapter = new PrizePoolAdapter();

        //set adapter
        binding.recyclerviewPrizePool.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.recyclerviewPrizePool.setAdapter(adapter);

        // Get all perticipatns
        viewModel.getAllPrizePoolDetails();

        viewModel.getAllPrizePool().observe(getViewLifecycleOwner(), new Observer<PrizePoolResponseModel>() {
            @Override
            public void onChanged(PrizePoolResponseModel prizePoolResponseModelItems) {
                adapter.updateList(prizePoolResponseModelItems);
            }
        });

        viewModel.getErrorMessage().observe(requireActivity(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                if (!Objects.equals(s, "")) {
                    Toast.makeText(requireContext(), s, Toast.LENGTH_SHORT).show();
                }
            }
        });
        viewModel.isLoading().observe(requireActivity(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isLoading) {
                if (isLoading) {
                    LoadingScreen.Companion.showLoadingDialog(requireContext());
                } else {
                    try {
                        LoadingScreen.Companion.hideLoadingDialog();
                    } catch (Exception e) {
                        e.getStackTrace();
                    }
                }
            }
        });

        //back Button
        binding.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getParentFragmentManager().popBackStackImmediate();
            }
        });

        return binding.getRoot();
    }
}