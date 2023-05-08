package com.example.sanify;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;


public class HelpCenterActivity extends Fragment {

    LinearLayout whatsAppBtn;
        View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_help_center_activity, container, false);
        whatsAppBtn = view.findViewById(R.id.whatsAppBtn);
        whatsAppBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("http://api.whatsapp.com/send?phone="+"+91"+"8609728367" + "&text="+"Hi,"));
                startActivity(intent);
            }
        });
        return view;
    }
}