package com.realteenpatti.sanify.ui.addmoney;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
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
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.realteenpatti.sanify.R;
import com.realteenpatti.sanify.databinding.FragmentAddMoneyBinding;
import com.realteenpatti.sanify.retrofit.models.addmoney.PaymentGetResponseModel;
import com.realteenpatti.sanify.retrofit.models.addmoney.PaymentGetResponseModelItem;
import com.realteenpatti.sanify.ui.dialogbox.LoadingScreen;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.realteenpatti.sanify.utils.ImageResizer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.function.Consumer;

public class AddMoneyFragment extends Fragment {
    FragmentAddMoneyBinding binding;
    public static final int GALLERY_REQ_CODE = 1001;
    Uri imageUri;
    private AddMoneyViewModel viewModel;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentAddMoneyBinding.inflate(inflater, container, false);
        viewModel = new ViewModelProvider(this).get(AddMoneyViewModel.class);


        viewModel.getTransactionMedium();

        viewModel.getAllTransactionMediums().observe(getViewLifecycleOwner(), new Observer<PaymentGetResponseModel>() {
            @Override
            public void onChanged(PaymentGetResponseModel transactionMediumData) {
                if (transactionMediumData != null && transactionMediumData.size() > 0){
                    ArrayList<String> paymentMethods = new ArrayList<>();

                    transactionMediumData.forEach(new Consumer<PaymentGetResponseModelItem>() {
                        @Override
                        public void accept(PaymentGetResponseModelItem paymentGetResponseModelItem) {
                            paymentMethods.add(paymentGetResponseModelItem.getMedium_title());
                        }
                    });

                    ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(requireContext(), R.layout.drop_downitem_layout, paymentMethods);
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
        });


        binding.btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int unselectColor = ContextCompat.getColor(requireContext(), R.color.unselect);
                int selectColor = ContextCompat.getColor(requireContext(), R.color.select);
                binding.btn2.setBackgroundTintList(ColorStateList.valueOf(unselectColor));
                binding.btn3.setBackgroundTintList(ColorStateList.valueOf(unselectColor));
                binding.btn4.setBackgroundTintList(ColorStateList.valueOf(unselectColor));
                binding.addAmountTxt.setText("100");
                binding.btn1.setBackgroundTintList(ColorStateList.valueOf(selectColor));
            }
        });
        binding.btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int unselectColor = ContextCompat.getColor(requireContext(), R.color.unselect);
                int selectColor = ContextCompat.getColor(requireContext(), R.color.select);
                binding.btn1.setBackgroundTintList(ColorStateList.valueOf(unselectColor));
                binding.btn3.setBackgroundTintList(ColorStateList.valueOf(unselectColor));
                binding.btn4.setBackgroundTintList(ColorStateList.valueOf(unselectColor));
                binding.addAmountTxt.setText("200");
                binding.btn2.setBackgroundTintList(ColorStateList.valueOf(selectColor));
            }
        });
        binding.btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int unselectColor = ContextCompat.getColor(requireContext(), R.color.unselect);
                int selectColor = ContextCompat.getColor(requireContext(), R.color.select);
                binding.btn1.setBackgroundTintList(ColorStateList.valueOf(unselectColor));
                binding.btn2.setBackgroundTintList(ColorStateList.valueOf(unselectColor));
                binding.btn4.setBackgroundTintList(ColorStateList.valueOf(unselectColor));
                binding.addAmountTxt.setText("500");
                binding.btn3.setBackgroundTintList(ColorStateList.valueOf(selectColor));
            }
        });
        binding.btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int unselectColor = ContextCompat.getColor(requireContext(), R.color.unselect);
                int selectColor = ContextCompat.getColor(requireContext(), R.color.select);
                binding.btn1.setBackgroundTintList(ColorStateList.valueOf(unselectColor));
                binding.btn2.setBackgroundTintList(ColorStateList.valueOf(unselectColor));
                binding.btn3.setBackgroundTintList(ColorStateList.valueOf(unselectColor));
                binding.addAmountTxt.setText("1000");
                binding.btn4.setBackgroundTintList(ColorStateList.valueOf(selectColor));
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

                String transactionId = binding.addAmountTransactionIdTxt.getText().toString().trim();
                String amount = binding.addAmountTxt.getText().toString().trim();
                String paymentMethod = binding.paymentMethod.getText().toString();

                if (!transactionId.isEmpty() &&
                        !amount.isEmpty() &&
                        !paymentMethod.isEmpty() && imageUri != null
                ){
                    try {
                        Bitmap bitmap = ImageResizer.getThumbnail(imageUri, 650000, requireContext());
                        viewModel.transaction(bitmap, transactionId, paymentMethod, Integer.parseInt(amount));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
                else {
                    Toast.makeText(requireContext(),"Please fill all the fields", Toast.LENGTH_SHORT).show();
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
                if (!Objects.equals(s, "")){
                    Toast.makeText(requireActivity(), s, Toast.LENGTH_SHORT).show();
                }
            }
        });

        viewModel.isLoading().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isLoading) {
                if (isLoading){
                    LoadingScreen.Companion.showLoadingDialog(requireContext());
                }else{
                    try {
                        LoadingScreen.Companion.hideLoadingDialog();
                    } catch (Exception e){
                        e.getStackTrace();
                    }
                }
            }
        });

        viewModel.getUploadStatus().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isSuccess) {
                if (isSuccess){
                    binding.addAmountTxt.setText("");
                    binding.addAmountTransactionIdTxt.setText("");
                    binding.paymentMethod.setText("");
                    imageUri = null;
                    binding.transactionImage.setImageResource(R.drawable.image_icon);
                    Toast.makeText(requireContext(),"Transaction request added, please wait until verified",Toast.LENGTH_SHORT).show();
                }
            }
        });


        return binding.getRoot();
    }

//    private void uploadImage() {
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss", Locale.CANADA);
//        Date now = new Date();
//        String fileName = simpleDateFormat.format(now);
//        storageRef = FirebaseStorage.getInstance().getReference("transaction images/" + fileName);
//        storageRef.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//            @Override
//            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                binding.transactionImage.setImageURI(null);
//                Toast.makeText(requireContext(), "upload", Toast.LENGTH_SHORT).show();
//                Intent intent = new Intent(getContext(), DashBoardFragment.class);
//                startActivity(intent);
//            }
//        }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception e) {
//                Toast.makeText(requireContext(), "Failed to upload", Toast.LENGTH_SHORT).show();
//            }
//        });
//    }




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