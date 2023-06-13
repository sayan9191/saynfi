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
//        binding.recyclerviewParticipant.setLayoutManager(new LinearLayoutManager(getContext()));
//        binding.recyclerviewParticipant.setAdapter(adapter);


        // Get all perticipatns
        viewModel.getAllParticipant(pageNo, "");




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