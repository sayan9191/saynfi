package com.example.sanify;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.sanify.ui.DashBoardFragment;
import com.example.sanify.ui.auth.LogInActivity;
import com.example.sanify.utils.StorageUtil;
import com.google.firebase.auth.FirebaseAuth;

public class Profile_Fragment extends Fragment {

    TextView addMoneyBtn, transactionHistoryBtn, helpCenterBtn, tcBtn, logOutBtn, balanceAmount;
    ImageView backBtn;
    FirebaseAuth firebaseAuth;
    View view;
    StorageUtil localStorage = new StorageUtil();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_profile__activity, container, false);
        addMoneyBtn = view.findViewById(R.id.addMoneyBtn);
        transactionHistoryBtn = view.findViewById(R.id.transactionHistoryBtn);
        helpCenterBtn = view.findViewById(R.id.helpCenterBtn);
        tcBtn = view.findViewById(R.id.tcBtn);
        logOutBtn = view.findViewById(R.id.logOutBtn);
        backBtn = view.findViewById(R.id.backBtn);

        localStorage.setSharedPref(requireContext().getSharedPreferences("sharedPref", Context.MODE_PRIVATE));


        addMoneyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getParentFragmentManager().beginTransaction()
                        .setReorderingAllowed(true)
                        .addToBackStack("Profile")
                        .replace(R.id.fragmentContainerView, new PaymentFragment())
                        .commit();            }
        });

        helpCenterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getParentFragmentManager().beginTransaction()
                        .setReorderingAllowed(true)
                        .addToBackStack("helpCenter")
                        .replace(R.id.fragmentContainerView, new HelpCenterActivity())
                        .commit();
            }
        });
        logOutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                FirebaseAuth.getInstance().signOut();
                localStorage.setToken("");
                startActivity(new Intent(requireContext(), LogInActivity.class));
                requireActivity().finish();
            }
        });
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity( new Intent(requireContext(), DashBoardFragment.class));
            }
        });

        return view;
    }
}
