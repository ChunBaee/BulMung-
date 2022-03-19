package com.solie.mrio.activity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnPausedListener;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.solie.mrio.MainActivity;
import com.solie.mrio.R;
import com.solie.mrio.RecyclerDecoration;
import com.solie.mrio.adapter.ProfileSettingAdapter;
import com.solie.mrio.data.ProfileSettingData;

import java.io.File;
import java.io.InputStream;

public class ProfileSettingActivity extends AppCompatActivity implements View.OnClickListener{

    RecyclerView photoSelect;
    ProfileSettingAdapter profileSettingAdapter;
    Button saveBtn;

    private static final int REQUEST_CODE = 0;
    private static final int PROFILE_CHANGE = 1500;

    private String mImgPath = null;
    private String mImgTitle = null;
    private String mImgOrient = null;

    public int itemId = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_profile_edit_activity);

        photoSelect = findViewById(R.id.setting_profile);
        photoSelect.addItemDecoration(new RecyclerDecoration(10,10,10,10));
        GridLayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 3);
        photoSelect.setLayoutManager(layoutManager);
        profileSettingAdapter = new ProfileSettingAdapter(getApplicationContext());
        photoSelect.setAdapter(profileSettingAdapter);

        saveBtn = findViewById(R.id.settingSaveBtn);
        saveBtn.setOnClickListener(this);

        for(int i=0; i<6; i++) {
            profileSettingAdapter.addItem(new ProfileSettingData(BitmapFactory.decodeResource(getResources(), R.drawable.chatlist_border)));
        }
        profileSettingAdapter.setOnItemClickListener(new ProfileSettingAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(ProfileSettingAdapter.ViewHolder holder, View view, int position) {
                itemId = position;
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, REQUEST_CODE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_CODE) {
            if(resultCode == RESULT_OK) {

                Uri uri = data.getData();
                getImageNameToUri(uri);

                try {
                    Bitmap image = MediaStore.Images.Media.getBitmap(getContentResolver(),uri);
                    profileSettingAdapter.changeItem(itemId,new ProfileSettingData(image));
                    profileSettingAdapter.notifyDataSetChanged();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                Toast.makeText(getApplicationContext(), "사진 선택을 취소하셨습니다.", Toast.LENGTH_SHORT).show();
            }
        }
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
        UploadTask uploadTask = storageRef.child(MainActivity.user + "/" + file.getLastPathSegment()).putFile(file, metadata);
        uploadTask.addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                double progress = (100.0 * snapshot.getBytesTransferred()) / snapshot.getTotalByteCount();
                Toast.makeText(getApplicationContext(),"Upload is " + progress + "% done", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        Intent intent = new Intent();
        intent.putExtra("MainProfilePic", profileSettingAdapter.setProfileImage(0));
        setResult(PROFILE_CHANGE, intent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.settingSaveBtn:
                uploadFile(mImgPath);
                break;
        }
    }
}
