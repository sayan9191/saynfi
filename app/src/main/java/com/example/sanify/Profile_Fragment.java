package com.example.sanify;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.sanify.databinding.FragmentProfileActivityBinding;
import com.example.sanify.ui.auth.LogInActivity;
import com.example.sanify.utils.StorageUtil;
import com.google.firebase.auth.FirebaseAuth;

public class Profile_Fragment extends Fragment {

    FragmentProfileActivityBinding binding;
    StorageUtil localStorage = new StorageUtil();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentProfileActivityBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.addMoneyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getParentFragmentManager().beginTransaction()
                        .setReorderingAllowed(true)
                        .addToBackStack("Profile")
                        .replace(R.id.fragmentContainerView, new PaymentFragment())
                        .commit();
            }
        });
        binding.transactionHistoryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getParentFragmentManager().beginTransaction()
                        .setReorderingAllowed(true)
                        .addToBackStack("helpCenter")
                        .replace(R.id.fragmentContainerView, new TransactionFragment())
                        .commit();
            }
        });

        binding.helpCenterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getParentFragmentManager().beginTransaction()
                        .setReorderingAllowed(true)
                        .addToBackStack("helpCenter")
                        .replace(R.id.fragmentContainerView, new HelpCenterActivity())
                        .commit();
            }
        });
        binding.logOutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                FirebaseAuth.getInstance().signOut();
                localStorage.saveTokenLocally("");
                startActivity(new Intent(requireContext(), LogInActivity.class));
                requireActivity().finish();
            }
        });
        binding.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(requireContext(), MainActivity.class));
            }
        });
    }
    }
