package com.example.sanify;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

public class Profile_Activity extends Fragment {

    LinearLayout addMoneyBtn,transactionHistoryBtn,helpCenterBtn,tcBtn,logOutBtn;
    View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_profile__activity, container, false);
        addMoneyBtn = view.findViewById(R.id.addMoneyBtn);
        transactionHistoryBtn = view.findViewById(R.id.transactionHistoryBtn);
        helpCenterBtn = view.findViewById(R.id.helpCenterBtn);
        tcBtn = view.findViewById(R.id.tcBtn);
        logOutBtn = view.findViewById(R.id.logOutBtn);

        addMoneyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getParentFragmentManager().beginTransaction()
                        .setReorderingAllowed(true)
                        .addToBackStack("Profile")
//                        .replace(R.id.main_, new PaymentActivity())
                        .commit();            }
        });
        return view;
    }
}