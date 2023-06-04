package com.example.sanify;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.sanify.databinding.FragmentAddMoneyBinding;
import com.example.sanify.ui.DashBoardFragment;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class AddMoneyFragment extends Fragment {
    FragmentAddMoneyBinding binding;
    public static final int GALLERY_REQ_CODE = 1001;
    Uri imageUri;
    private FirebaseStorage storage;
    private StorageReference storageRef;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentAddMoneyBinding.inflate(inflater, container, false);
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

        binding.fileChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImage();
            }
        });

        binding.submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadImage();
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

    private void uploadImage() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss", Locale.CANADA);
        Date now = new Date();
        String fileName = simpleDateFormat.format(now);
        storageRef = FirebaseStorage.getInstance().getReference("transaction images/" + fileName);
        storageRef.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                binding.transactionImage.setImageURI(null);
                Toast.makeText(requireContext(), "upload", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getContext(), DashBoardFragment.class);
                startActivity(intent);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(requireContext(), "Failed to upload", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void selectImage() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, GALLERY_REQ_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GALLERY_REQ_CODE && data != null && data.getData() != null) {
            imageUri = data.getData();
            binding.transactionImage.setImageURI(imageUri);
        }
    }
}