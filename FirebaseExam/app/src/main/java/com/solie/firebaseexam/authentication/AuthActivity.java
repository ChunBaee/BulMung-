package com.solie.firebaseexam.authentication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.solie.firebaseexam.R;

public class AuthActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        Button firebaseUiBtn = findViewById(R.id.firebaseUi);
        firebaseUiBtn.setOnClickListener(this);

        Button firebaseSignOut = findViewById(R.id.firebaseSignOut);
        firebaseSignOut.setOnClickListener(this);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user != null) {
            firebaseUiBtn.setEnabled(false);
        } else {
            firebaseUiBtn.setEnabled(true);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.firebaseUi:
                Intent intent = new Intent(this, FirebaseUIActivity.class);
                startActivity(intent);
                break;

            case R.id.firebaseSignOut:
                SignOut();
                break;

            default:
                break;
        }
    }

    private void SignOut() {
        AuthUI.getInstance()
                .signOut(this)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()) {
                            finish();
                        } else {

                        }
                    }
                });
    }
}
