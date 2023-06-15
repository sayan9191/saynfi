package com.realteenpatti.sanify.ui.bottomsheet;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.sanify.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class BottomSheetTC extends BottomSheetDialogFragment {
    public BottomSheetTC() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.termsandcondition,container,false);
        return view;
    }
}
