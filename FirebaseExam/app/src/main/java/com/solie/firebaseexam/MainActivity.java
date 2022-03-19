package com.solie.firebaseexam;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;

import com.solie.firebaseexam.authentication.AuthActivity;
import com.solie.firebaseexam.cloudstorage.CloudStorageActivity;
import com.solie.firebaseexam.cloudstorage.FireStoreActivity;
import com.solie.firebaseexam.realtimedb.MemoActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);

        Button firebaseAuthBtn = findViewById(R.id.firebaseAuthBtn);
        firebaseAuthBtn.setOnClickListener(this);

        Button firebaseRealDBBtn = findViewById(R.id.fireBaseRealTimeBtn);
        firebaseRealDBBtn.setOnClickListener(this);

        Button firebaseCloudFireStorageBtn = findViewById(R.id.firebaseCloudFirebaseStoreBtn);
        firebaseCloudFireStorageBtn.setOnClickListener(this);

        Button firebaseCloudBtn = findViewById(R.id.fireBaseCloudStorageBtn);
        firebaseCloudBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.firebaseAuthBtn:
                Intent intent = new Intent(this, AuthActivity.class);
                startActivity(intent);
                break;

            case R.id.fireBaseRealTimeBtn:
                intent = new Intent(this, MemoActivity.class);
                startActivity(intent);
                break;

            case R.id.firebaseCloudFirebaseStoreBtn:
                intent = new Intent(this, FireStoreActivity.class);
                startActivity(intent);
                break;

            case R.id.fireBaseCloudStorageBtn:
                intent = new Intent(this, CloudStorageActivity.class);
                startActivity(intent);

            default:
                break;
        }
    }
}