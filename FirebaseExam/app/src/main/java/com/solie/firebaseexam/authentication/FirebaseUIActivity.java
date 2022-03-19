package com.solie.firebaseexam.authentication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.TaskStackBuilder;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.solie.firebaseexam.R;

import java.util.ArrayList;
import java.util.List;

public class FirebaseUIActivity extends AppCompatActivity implements View.OnClickListener{

    private static final int RC_SIGN_IN = 1000;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firebase_ui);

        Button fireBaseUiAuthBtn = findViewById(R.id.firebaseUiAuthBtn);
        fireBaseUiAuthBtn.setOnClickListener(this);


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == RC_SIGN_IN) {
            IdpResponse response = IdpResponse.fromResultIntent(data);

            if(resultCode == RESULT_OK) {
                Intent intent = new Intent(this, SignedInActivity.class);
                intent.putExtras(data);
                startActivity(intent);
            } else {
                Toast.makeText(getApplicationContext(),"Sign Failed",Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.firebaseUiAuthBtn:
                signIn();
                break;

            default:
                break;
        }
    }

    private void signIn() {
        startActivityForResult(
                AuthUI.getInstance().createSignInIntentBuilder()
                .setTheme(getSelectedTheme())
                .setLogo(getSelectedLogo())
                .setAvailableProviders(getSelectedProviders())
                .setTosAndPrivacyPolicyUrls("https://naver.com", "https://google.com")
                .setIsSmartLockEnabled(true)
                .build(),
        RC_SIGN_IN);
    }

    private int getSelectedTheme() {
        return AuthUI.getDefaultTheme();
    }

    private int getSelectedLogo() {
        return R.mipmap.ic_launcher_round;
    }

    private List<AuthUI.IdpConfig> getSelectedProviders() {
        List<AuthUI.IdpConfig> selectedProviders = new ArrayList<>();
        CheckBox googleChk = findViewById(R.id.google_provider);
        CheckBox facebookChk = findViewById(R.id.facebook_provider);
        CheckBox emailChk = findViewById(R.id.email_provider);

        if(googleChk.isChecked()) {
            selectedProviders.add(new AuthUI.IdpConfig.GoogleBuilder().build());
        }

        if(facebookChk.isChecked()) {
            selectedProviders.add(new AuthUI.IdpConfig.FacebookBuilder().build());
        }

        if(emailChk.isChecked()) {
            selectedProviders.add(new AuthUI.IdpConfig.EmailBuilder().build());
        }

        return selectedProviders;
    }

}
