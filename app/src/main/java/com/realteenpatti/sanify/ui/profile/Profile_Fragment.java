package com.realteenpatti.sanify.ui.profile;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.realteenpatti.sanify.R;
import com.realteenpatti.sanify.HelpCenterActivity;
import com.realteenpatti.sanify.retrofit.models.UserInfoResponseModel;
import com.realteenpatti.sanify.ui.addmoney.AddMoneyFragment;
import com.realteenpatti.sanify.ui.auth.login.LogInActivity;
import com.realteenpatti.sanify.ui.dialogbox.LoadingScreen;
import com.realteenpatti.sanify.ui.transactionhistory.TransactionFragment;
import com.realteenpatti.sanify.ui.withdraw.WithDrawFragmentFragment;
import com.realteenpatti.sanify.ui.withdrawHistory.WithDrawHistoryFragment;
import com.realteenpatti.sanify.utils.StorageUtil;

import java.util.Objects;

public class Profile_Fragment extends Fragment {

    FrameLayout addMoneyBtn, addMoneyHistoryBtn, helpCenterBtn, withDrawHistoryBtn, logOutBtn, withDrawMoneyBtn;
    ImageView backBtn;
    View view;
    StorageUtil localStorage = StorageUtil.Companion.getInstance();
    ProfileViewModel viewModel;
    TextView profileName, profilePhoneNumber;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_profile__activity, container, false);
        addMoneyBtn = view.findViewById(R.id.addMoneyBtn);
        addMoneyHistoryBtn = view.findViewById(R.id.addMoneyHistoryBtn);
        helpCenterBtn = view.findViewById(R.id.helpCenterBtn);
        withDrawHistoryBtn = view.findViewById(R.id.withDrawHistoryBtn);
        logOutBtn = view.findViewById(R.id.logOutBtn);
        withDrawMoneyBtn = view.findViewById(R.id.withDrawMoneyBtn);
        backBtn = view.findViewById(R.id.backBtn);
        profileName = view.findViewById(R.id.profileName);
        profilePhoneNumber = view.findViewById(R.id.profilePhoneNumber);

        localStorage.setSharedPref(requireContext().getSharedPreferences("sharedPref", Context.MODE_PRIVATE));

        viewModel = new ViewModelProvider(this).get(ProfileViewModel.class);

        viewModel.getCurrentUserInfo();

        addMoneyHistoryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getParentFragmentManager().beginTransaction()
                        .setReorderingAllowed(true)
                        .addToBackStack("Profile")
                        .replace(R.id.fragmentContainerView, new TransactionFragment())
                        .commit();
            }
        });
        addMoneyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getParentFragmentManager().beginTransaction()
                        .setReorderingAllowed(true)
                        .addToBackStack("Profile")
                        .replace(R.id.fragmentContainerView, new AddMoneyFragment())
                        .commit();
            }
        });

        withDrawHistoryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getParentFragmentManager().beginTransaction()
                        .setReorderingAllowed(true)
                        .addToBackStack("Profile")
                        .replace(R.id.fragmentContainerView, new WithDrawHistoryFragment())
                        .commit();
            }
        });
        withDrawMoneyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getParentFragmentManager().beginTransaction()
                        .setReorderingAllowed(true)
                        .addToBackStack("Profile")
                        .replace(R.id.fragmentContainerView, new WithDrawFragmentFragment())
                        .commit();
            }
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

        viewModel.getUserInfo().observe(getViewLifecycleOwner(), new Observer<UserInfoResponseModel>() {
            @Override
            public void onChanged(UserInfoResponseModel userInfo) {
                if (userInfo != null){
                    profileName.setText(userInfo.getName());
                    profilePhoneNumber.setText("Phone no. " + userInfo.getPhone_no());
                }
            }
        });

        return view;
    }
}
