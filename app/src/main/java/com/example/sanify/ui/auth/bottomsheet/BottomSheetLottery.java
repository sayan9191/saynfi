package com.example.sanify.ui.auth.bottomsheet;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.sanify.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class BottomSheetLottery extends BottomSheetDialogFragment {
    public BottomSheetLottery() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.privacyandpolicy, container, false);
        return view;
    }
}
