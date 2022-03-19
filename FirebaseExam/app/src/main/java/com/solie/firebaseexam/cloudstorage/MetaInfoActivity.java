package com.solie.firebaseexam.cloudstorage;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;
import com.solie.firebaseexam.R;

public class MetaInfoActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meta_info);

        Button metaBtn = findViewById(R.id.metaBtn);
        metaBtn.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.metaBtn:
                getMetaData();
                break;
        }
    }

    private void getMetaData() {
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();
        StorageReference forestRef = storageRef.child("storage/Df6SctGV4AAKAIS.jpg");

        forestRef.getMetadata().addOnSuccessListener(new OnSuccessListener<StorageMetadata>() {
            @Override
            public void onSuccess(StorageMetadata storageMetadata) {
                String metaData = storageMetadata.getName() + "\n" +
                        storageMetadata.getPath() + "\n" +
                        storageMetadata.getBucket();

                TextView metaTxt = findViewById(R.id.metaInfoTxt);
                metaTxt.setText(metaData);
            }
        });
    }

}