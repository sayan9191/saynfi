package com.realteenpatti.sanify.ui.withdrawHistory;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.realteenpatti.sanify.databinding.FragmentWithDrawHistoryBinding;
import com.realteenpatti.sanify.adapter.transactionadapter.WithDrawHistoryAdapter;
import com.realteenpatti.sanify.retrofit.models.transaction.AllWithDrawResponseModel;
import com.realteenpatti.sanify.ui.dialogbox.LoadingScreen;

import java.util.Objects;

public class WithDrawHistoryFragment extends Fragment {

    FragmentWithDrawHistoryBinding binding;
    WithDrawHistoryViewModel viewModel;
    WithDrawHistoryAdapter adapter;
    private int pageNo = 1;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentWithDrawHistoryBinding.inflate(inflater, container, false);
        viewModel = new ViewModelProvider(this).get(WithDrawHistoryViewModel.class);

        adapter = new WithDrawHistoryAdapter();
        binding.withDrawHistoryRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.withDrawHistoryRecyclerView.setAdapter(adapter);


        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getParentFragmentManager().popBackStackImmediate();
            }
        });

        viewModel.getAllWithDrawHistory(pageNo, "");

        binding.nextPageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pageNo += 1;
                viewModel.getAllWithDrawHistory(pageNo, "");
            }
        });

        binding.prevPageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (pageNo > 1) {
                    pageNo -= 1;
                    viewModel.getAllWithDrawHistory(pageNo, "");
                }
            }
        });

        viewModel.getAllWithDrawHistory().observe(getViewLifecycleOwner(), new Observer<AllWithDrawResponseModel>() {
            @Override
            public void onChanged(AllWithDrawResponseModel allWithDrawResponseModelItems) {
                binding.lottieAnimation.setVisibility(View.GONE);
                adapter.updateAllList(allWithDrawResponseModelItems);
                binding.pageNumberTextView.setText(String.valueOf(pageNo));
                if (pageNo > 1) {
                    binding.prevPageBtn.setVisibility(View.VISIBLE);
                } else {
                    binding.prevPageBtn.setVisibility(View.INVISIBLE);
                }
            }
        });
        viewModel.getErrorMessage().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                if (!Objects.equals(s, "")) {
                    Toast.makeText(requireActivity(), s, Toast.LENGTH_SHORT).show();
                    if (s.equals("No withdraw requests has been made")) {
                        binding.nextPageBtn.setVisibility(View.INVISIBLE);
                        pageNo -= 1;
                    }
                }
            }
        });

        viewModel.isLoading().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
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
    }

}