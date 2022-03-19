package com.solie.firebaseexam.cloudstorage;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnPausedListener;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.solie.firebaseexam.R;

import java.io.File;

public class UploadActivity extends AppCompatActivity implements View.OnClickListener {

    private final int REQ_CODE_SELECT_IMAGE = 1000;

    private String mImgPath = null;
    private String mImgTitle = null;
    private String mImgOrient = null;

    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);

        Button upLoadBtn = findViewById(R.id.imgUploadBtn);
        upLoadBtn.setOnClickListener(this);

        progressBar = findViewById(R.id.progressBar);

        getGallery();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQ_CODE_SELECT_IMAGE) {
            if(resultCode == Activity.RESULT_OK) {
                Uri uri = data.getData();
                getImageNameToUri(uri);

                try {
                    Bitmap bmp = MediaStore.Images.Media.getBitmap(getContentResolver(),uri);
                    ImageView img = findViewById(R.id.showImg);
                    img.setImageBitmap(bmp);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgUploadBtn:
                uploadFile(mImgPath);
                break;

            default:
                break;
        }

    }

    private void getGallery() {
        Intent intent = null;

        if(Build.VERSION.SDK_INT >= 19) {
            intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        } else {
            intent = new Intent(Intent.ACTION_GET_CONTENT);
        }
        intent.setType("image/*");
        startActivityForResult(intent, REQ_CODE_SELECT_IMAGE);
    }

    private void getImageNameToUri(Uri data) {
        String[] proj = {
                MediaStore.Images.Media.DATA,
                MediaStore.Images.Media.TITLE,
                MediaStore.Images.Media.ORIENTATION
        };

        Cursor cursor = this.getContentResolver().query(data, proj, null, null, null);
        cursor.moveToFirst();

        int column_data = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        int column_title = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.TITLE);
        int column_orientation = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.ORIENTATION);

        mImgPath = cursor.getString(column_data);
        mImgTitle = cursor.getString(column_title);
        mImgOrient = cursor.getString(column_orientation);
    }

    private void uploadFile (String aFilePath) {
        Uri file = Uri.fromFile(new File(aFilePath));
        StorageMetadata metadata = new StorageMetadata.Builder().setContentType("image/jpeg").build();

        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();
        UploadTask uploadTask = storageRef.child("storage/" + file.getLastPathSegment()).putFile(file, metadata);

        uploadTask.addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                double progress = (100.0 * snapshot.getBytesTransferred()) / snapshot.getTotalByteCount();
                progressBar.setProgress((int)progress);
                Toast.makeText(UploadActivity.this, "Upload is " + progress + "% done", Toast.LENGTH_SHORT).show();
            }
        }).addOnPausedListener(new OnPausedListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onPaused(@NonNull UploadTask.TaskSnapshot snapshot) {
                Toast.makeText(UploadActivity.this,"업로드 중단되었습니다.",Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(UploadActivity.this, "업로드 실패했습니다.",Toast.LENGTH_SHORT).show();

            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(UploadActivity.this, "업로드가 완료되었습니다!",Toast.LENGTH_SHORT).show();
            }
        });

    }

}