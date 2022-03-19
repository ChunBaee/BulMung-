package com.solie.firebaseexam.cloudstorage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.module.AppGlideModule;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.solie.firebaseexam.R;

import java.io.File;
import java.io.IOException;

public class DownloadActivity extends AppCompatActivity implements View.OnClickListener{

    private File localFile = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download);

        Button localFileDownloadBtn = findViewById(R.id.localImgDownloadBtn);
        localFileDownloadBtn.setOnClickListener(this);

        Button firebaseUIBtn = findViewById(R.id.firebaseUiDownloadBtn);
        firebaseUIBtn.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.localImgDownloadBtn:
                showDownloadLocalFileImageView();
                break;

            case R.id.firebaseUiDownloadBtn:
                showFirebaseUiDownloadImageView();
                break;

            default:
                break;
        }
    }

    private void showDownloadLocalFileImageView() {
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();
        StorageReference pathReference = storageRef.child("storage/Df6SctGV4AAKAIS.jpg");

        try {
            localFile = File.createTempFile("images","jpg");
        } catch (IOException e) {
            e.printStackTrace();
        }

        pathReference.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                long fileSize = taskSnapshot.getTotalByteCount();

                ImageView imageView = findViewById(R.id.fcStorageImg);
                Glide.with(DownloadActivity.this).load(new File(localFile.getAbsolutePath())).into(imageView);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(DownloadActivity.this, "다운로드 실패",Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void showFirebaseUiDownloadImageView() {
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();
        StorageReference pathReference = storageRef.child("storage/Df6SctGV4AAKAIS.jpg");

        ImageView imageView = findViewById(R.id.fcStorageImg);

        Glide.with(this)
                .load(pathReference)
                .into(imageView);
    }
}

