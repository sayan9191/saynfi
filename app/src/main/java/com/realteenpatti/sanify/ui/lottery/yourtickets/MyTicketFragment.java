package com.realteenpatti.sanify.ui.lottery.yourtickets;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.realteenpatti.sanify.databinding.FragmentYourTicketsBinding;
import com.realteenpatti.sanify.adapter.lottery.MyTicketAdapter;
import com.realteenpatti.sanify.retrofit.models.lottery.MyTicketResponseModel;
import com.realteenpatti.sanify.ui.dialogbox.LoadingScreen;

import java.util.Objects;

public class MyTicketFragment extends Fragment {
    FragmentYourTicketsBinding binding;
    MyTicketViewModel viewModel;
    MyTicketAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentYourTicketsBinding.inflate(getLayoutInflater(), container, false);
        viewModel = new ViewModelProvider(this).get(MyTicketViewModel.class);
        adapter = new MyTicketAdapter();

        //set Adapter
        binding.recyclerviewYourTicket.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.recyclerviewYourTicket.setAdapter(adapter);

        // Get all my Tickets
        viewModel.getAllMyTicket("");

        viewModel.getMyTicketList().observe(getViewLifecycleOwner(), new Observer<MyTicketResponseModel>() {
            @Override
            public void onChanged(MyTicketResponseModel myTicketResponseModelItems) {
                adapter.updateList(myTicketResponseModelItems);
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