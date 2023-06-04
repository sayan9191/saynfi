package com.example.sanify;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.sanify.databinding.FragmentWithDrawFragmentBinding;

public class WithDrawFragmentFragment extends Fragment {

    FragmentWithDrawFragmentBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentWithDrawFragmentBinding.inflate(inflater, container, false);
        String[] paymentMethods = new String[]{"BIKSH ", "PAY PAL", "NAGAD"};
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(requireContext(), R.layout.drop_downitem_layout, paymentMethods);
        binding.paymentMethod.setAdapter(arrayAdapter);

        binding.paymentMethod.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //here save the payment method
                Toast.makeText(requireContext(), binding.paymentMethod.getText().toString(), Toast.LENGTH_SHORT).show();
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