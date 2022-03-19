package com.solie.mrio;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.telephony.ClosedSubscriberGroupInfo;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.solie.mrio.fragment.AcceptFragment;
import com.solie.mrio.fragment.ChatListFragment;
import com.solie.mrio.fragment.FindFragment;
import com.solie.mrio.fragment.SettingFragment;
import com.solie.mrio.network.NetworkCheck;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements OnClickChange {

    private static final int RC_SIGN_IN = 1000;

    public static Toolbar toolbar;
    public static ActionBar actionBar;
    public static ImageView toolbarImage;
    public static TextView toolbarText;

    public FrameLayout frameContainer;

    public static FirebaseUser user;

    FindFragment findFragment;
    ChatListFragment chatListFragment;
    AcceptFragment acceptFragment;
    SettingFragment settingFragment;

    public static boolean acceptOrNot;

    public static BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        frameContainer = findViewById(R.id.frameContainer);
        bottomNavigationView = findViewById(R.id.bottomNav);

        toolbarImage = findViewById(R.id.toolbarImage);
        toolbarText = findViewById(R.id.toolbarText);

        findFragment = new FindFragment();
        chatListFragment = new ChatListFragment();
        acceptFragment = new AcceptFragment();
        settingFragment = new SettingFragment();


        setToolbar();
        networkCheck();

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                item.setChecked(true);
                int id = item.getItemId();
                if (id == R.id.menu_find_list) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.frameContainer, findFragment).commit();
                    acceptOrNot = false;
                } else if (id == R.id.menu_accept_list) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.frameContainer, acceptFragment).commit();
                } else if (id == R.id.menu_chat_list) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.frameContainer, chatListFragment).commit();
                } else {
                    getSupportFragmentManager().beginTransaction().replace(R.id.frameContainer, settingFragment).commit();
                }
                return true;
            }
        });
    }

    public void setToolbar() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(false);
    }

    private void networkCheck() {
        int status = NetworkCheck.getConnectivityStatus(getApplicationContext());
        if (status == NetworkCheck.TYPE_NOT_CONNECTED) {
            //오프라인상태
            Toast.makeText(getApplicationContext(), "인터넷 연결을 확인해주세요.", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            userLoginCheck();
        }
    }

    public void userLoginCheck() {
        user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null) {
            loginProcess();
        } else {
            setFragment();
        }
    }

    private void loginProcess() {
        startActivityForResult(
                AuthUI.getInstance().createSignInIntentBuilder()
                        .setTheme(AuthUI.getDefaultTheme())
                        .setLogo(R.mipmap.ic_launcher_round)
                        .setAvailableProviders(getProviders())
                        .setTosAndPrivacyPolicyUrls("https://naver.com", "https://google.com")
                        .setIsSmartLockEnabled(false)
                        .build(), RC_SIGN_IN);
    }

    private List<AuthUI.IdpConfig> getProviders() {
        List<AuthUI.IdpConfig> providers = new ArrayList<>();
        providers.add(new AuthUI.IdpConfig.GoogleBuilder().build());
        providers.add(new AuthUI.IdpConfig.EmailBuilder().build());
        return providers;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            {
                if (resultCode != 1500) {
                    IdpResponse response = IdpResponse.fromResultIntent(data);
                }
            }
        }
    }

    public void setFragment() {
        getSupportFragmentManager().beginTransaction().add(R.id.frameContainer, findFragment).commit();
    }

    public void onClickChange(int index) {
        if (index == 0) {
            getSupportFragmentManager().beginTransaction().replace(R.id.frameContainer, findFragment).addToBackStack(null).commit();
        } else {
            getSupportFragmentManager().beginTransaction().replace(R.id.frameContainer, acceptFragment).commit();
        }
    }
}
