package com.realteenpatti.sanify.ui.withdraw;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.realteenpatti.sanify.R;
import com.realteenpatti.sanify.databinding.FragmentWithDrawFragmentBinding;
import com.realteenpatti.sanify.ui.dialogbox.LoadingScreen;

import java.util.Objects;

public class WithDrawFragment extends Fragment {

    FragmentWithDrawFragmentBinding binding;
    private WithDrawViewModel viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentWithDrawFragmentBinding.inflate(inflater, container, false);
        viewModel = new ViewModelProvider(this).get(WithDrawViewModel.class);
        String[] paymentMethods = new String[]{"GPay", "Bkash ", "Nagad", "PayPal", "Cash App"};
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(requireContext(), R.layout.drop_downitem_layout, paymentMethods);
        binding.paymentMethod.setAdapter(arrayAdapter);

        binding.paymentMethod.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //here save the payment method
                Toast.makeText(requireContext(), binding.paymentMethod.getText().toString(), Toast.LENGTH_SHORT).show();
            }
        });
        //submit BUTTON
        binding.submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String paymentMethod = binding.paymentMethod.getText().toString().trim();
                String phoneNo = binding.addAmountTransactionIdTxt.getText().toString().trim();
                String amount = binding.addAmountTxt.getText().toString().trim();
                if (!paymentMethod.isEmpty() && !phoneNo.isEmpty() && !amount.isEmpty()) {
                    viewModel.withDraw(paymentMethod, phoneNo, Integer.parseInt(amount));
                } else {
                    Toast.makeText(requireContext(), "Please fill all the fields", Toast.LENGTH_SHORT).show();
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

        viewModel.getErrorMessage().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                if (!Objects.equals(s, "")) {
                    Toast.makeText(requireActivity(), s, Toast.LENGTH_SHORT).show();
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

        viewModel.getUploadStatus().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isSuccess) {
                if (isSuccess) {
                    binding.addAmountTxt.setText("");
                    binding.addAmountTransactionIdTxt.setText("");
                    binding.paymentMethod.setText("");
                    Toast.makeText(requireContext(), "Transaction request added, please wait until verified", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return binding.getRoot();
    }
}