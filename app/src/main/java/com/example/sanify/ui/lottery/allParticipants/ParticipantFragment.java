package com.example.sanify.ui.lottery.allParticipants;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.sanify.adapter.lottery.AllParticipantAdapter;
import com.example.sanify.databinding.FragmentParticipantBinding;
import com.example.sanify.retrofit.models.lottery.AllParticipantResponseModel;
import com.example.sanify.ui.dialogbox.LoadingScreen;

import java.util.Objects;

public class ParticipantFragment extends Fragment {
    FragmentParticipantBinding binding;
    AllParticipantViewModel viewModel;
    AllParticipantAdapter adapter;

    int pageNo = 1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentParticipantBinding.inflate(inflater, container, false);
        viewModel = new ViewModelProvider(this).get(AllParticipantViewModel.class);
        adapter = new AllParticipantAdapter();

        //SET ADAPTER
        binding.recyclerviewParticipant.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recyclerviewParticipant.setAdapter(adapter);


        // Get all perticipatns
        viewModel.getAllParticipant(pageNo, "");


        binding.nextPageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pageNo += 1;
                viewModel.getAllParticipant(pageNo, "");
            }
        });

        binding.prevPageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (pageNo > 1) {
                    pageNo -= 1;
                    viewModel.getAllParticipant(pageNo, "");
                }
            }
        });

        viewModel.getAllParticipant().observe(requireActivity(), new Observer<AllParticipantResponseModel>() {
            @Override
            public void onChanged(AllParticipantResponseModel allParticipantResponseModelItems) {
                adapter.updateAllList(allParticipantResponseModelItems);
                binding.pageNumberTextView.setText(String.valueOf(pageNo));
                if (pageNo > 1) {
                    binding.prevPageBtn.setVisibility(View.VISIBLE);
                } else {
                    binding.prevPageBtn.setVisibility(View.INVISIBLE);
                }
            }
        });
        viewModel.getErrorMessage().observe(requireActivity(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                if (!Objects.equals(s, "")) {
                    Toast.makeText(requireContext(), s, Toast.LENGTH_SHORT).show();
                    if (s.equals("Transactions not found")) {
                        binding.nextPageBtn.setVisibility(View.INVISIBLE);
                        pageNo -= 1;
                    }
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