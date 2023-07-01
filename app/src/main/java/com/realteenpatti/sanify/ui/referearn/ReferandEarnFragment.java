package com.realteenpatti.sanify.ui.referearn;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;

import com.realteenpatti.sanify.databinding.FragmentReferandEarnBinding;


public class ReferandEarnFragment extends Fragment {

    FragmentReferandEarnBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentReferandEarnBinding.inflate(inflater, container, false);

        getParentFragmentManager().setFragmentResultListener("referKey", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                String refer = result.getString("refer");
                binding.referCode.setText(refer);
                String copyText = "Use my coupon code " + refer + "and get cash bonus.";
                binding.copyBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ClipboardManager clipboardManager = (ClipboardManager) requireContext().getSystemService(Context.CLIPBOARD_SERVICE);
                        ClipData clipData = ClipData.newPlainText("Text", copyText);
                        clipboardManager.setPrimaryClip(clipData);
                        Toast.makeText(requireContext(), "Text copied", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });

        //back button
        binding.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getParentFragmentManager().popBackStackImmediate();
            }
        });


        return binding.getRoot();
    }
}