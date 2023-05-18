package com.example.sanify.repo

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.UploadTask
import java.text.SimpleDateFormat
import java.util.*

class PaymentRepository {

    val uploadStatus: MutableLiveData<Boolean> = MutableLiveData()

    fun uploadImage(context: Context, imageUri: Uri) {
        val simpleDateFormat = SimpleDateFormat("yyyy_MM_dd_HH_mm_ss", Locale.CANADA)
        val now = Date()
        val fileName = simpleDateFormat.format(now)
        val storageRef = FirebaseStorage.getInstance().getReference("transaction_images/$fileName")
        storageRef.putFile(imageUri)
            .addOnSuccessListener(OnSuccessListener<UploadTask.TaskSnapshot?> {
                val url: String = storageRef.getDownloadUrl().getResult().toString()
                Toast.makeText(context, "upload", Toast.LENGTH_SHORT).show()
                uploadStatus.postValue(true)
            }).addOnFailureListener(OnFailureListener {
                Toast.makeText(
                    context,
                    "Failed to upload",
                    Toast.LENGTH_SHORT
                ).show()
                uploadStatus.postValue(false)
            })
    }
}