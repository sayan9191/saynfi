package com.example.sanify;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.sanify.databinding.FragmentAddMoneyBinding;

public class AddMoneyFragment extends Fragment {
    FragmentAddMoneyBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String[] paymentMethods = new String[]{"BIKSH   PHONE NO.9876543210", "PAY PAL   ACCOUNT NO.9874563210", ""};
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(requireContext(), R.layout.drop_downitem_layout, paymentMethods);
        binding.paymentMethod.setAdapter(arrayAdapter);
        binding.paymentMethod.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //here save the payment method
                Toast.makeText(requireContext(), binding.paymentMethod.getText().toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }


}