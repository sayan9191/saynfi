package com.realteenpatti.sanify.ui.lottery.result;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.sanify.databinding.FragmentResultBinding;
import com.realteenpatti.sanify.adapter.lottery.ResultAdapter;
import com.realteenpatti.sanify.retrofit.models.lottery.AllWinnerResponseModel;
import com.realteenpatti.sanify.ui.dialogbox.LoadingScreen;


import java.util.Objects;

public class ResultFragment extends Fragment {
    FragmentResultBinding binding;
    ResultViewModel viewModel;
    ResultAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentResultBinding.inflate(getLayoutInflater(), container, false);
        viewModel = new ViewModelProvider(this).get(ResultViewModel.class);
        adapter = new ResultAdapter();

        //set Adapter
        binding.recyclerviewResult.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.recyclerviewResult.setAdapter(adapter);

        // Get all winnerlist
        viewModel.getAllWinnerDetails();

        viewModel.getAllWinnerList().observe(getViewLifecycleOwner(), new Observer<AllWinnerResponseModel>() {
            @Override
            public void onChanged(AllWinnerResponseModel allWinnerResponseModelItems) {
                adapter.updateAllList(allWinnerResponseModelItems);
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

        //back btn
        binding.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getParentFragmentManager().popBackStackImmediate();
            }
        });
        return binding.getRoot();
    }
}