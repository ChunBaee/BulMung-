package com.solie.firebaseexam.cloudstorage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.solie.firebaseexam.R;

public class CloudStorageActivity extends AppCompatActivity implements View.OnClickListener {

    private final int REQUEST_CODE = 100;

    private Button uploadBtn;
    private Button downloadBtn;
    private Button metaInfoBtn;
    private Button deleteBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cloud_storage);

        uploadBtn = findViewById(R.id.upLoadBtn);
        uploadBtn.setOnClickListener(this);

        downloadBtn = findViewById(R.id.downLoadBtn);
        downloadBtn.setOnClickListener(this);

        metaInfoBtn = findViewById(R.id.metaInfoBtn);
        metaInfoBtn.setOnClickListener(this);

        deleteBtn = findViewById(R.id.deleteBtn);
        deleteBtn.setOnClickListener(this);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if(checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_CODE);
                Toast.makeText(this, "안드로이드 6.0 마시멜로부터 일부 권한에 대해 사용자에게 동의 필요!",Toast.LENGTH_SHORT).show();
                uploadBtn.setEnabled(false);
                downloadBtn.setEnabled(false);
                metaInfoBtn.setEnabled(false);
                deleteBtn.setEnabled(false);
            }
        }
    }

    @Override
    public void onClick(View v) {
        Intent intent = null;

        switch (v.getId()) {
            case R.id.upLoadBtn:
                intent = new Intent(this, UploadActivity.class);
                break;

            case R.id.downLoadBtn:
                intent = new Intent(this, DownloadActivity.class);
                break;

            case R.id.metaInfoBtn:
                intent = new Intent(this, MetaInfoActivity.class);
                break;

            case R.id.deleteBtn:
                deleteFile();
                break;


            default:
                break;
        }

        if (intent != null) {
            startActivity(intent);
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case REQUEST_CODE:
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    uploadBtn.setEnabled(true);
                    downloadBtn.setEnabled(true);
                    metaInfoBtn.setEnabled(true);
                    deleteBtn.setEnabled(true);
                }
                break;

            default:
                break;
        }
    }

    private void deleteFile() {
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();

        StorageReference desertRef = storageRef.child("storage/Df6SctGV4AAKAIS.jpg");
        desertRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {

            }
        });
    }

}